package com.ruoyi.api.service.impl;

import java.util.*;
import com.alibaba.fastjson2.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.api.domain.BizD365Cache;
import com.ruoyi.api.mapper.BizD365CacheMapper;
import com.ruoyi.api.service.IBizD365CacheService;
import com.ruoyi.api.service.ID365ProxyService;

@Service
public class BizD365CacheServiceImpl implements IBizD365CacheService {
    private static final Logger log = LoggerFactory.getLogger(BizD365CacheServiceImpl.class);

    @Autowired private BizD365CacheMapper mapper;
    @Autowired private ID365ProxyService d365Proxy;

    @Override public List<BizD365Cache> selectList(BizD365Cache c) { return mapper.selectList(c); }
    @Override public BizD365Cache selectById(Long id) { return mapper.selectById(id); }
    @Override public int insert(BizD365Cache c) { return mapper.insert(c); }
    @Override public int update(BizD365Cache c) { return mapper.update(c); }
    @Override public int deleteByIds(Long[] ids) { return mapper.deleteByIds(ids); }

    @Override
    public String getCachedData(String endpointName, String extraParams) {
        BizD365Cache cache = mapper.selectByEndpoint(endpointName, extraParams);
        if (cache == null) return null;

        // Check if cache is still valid
        if ("success".equals(cache.getSyncStatus()) && cache.getNextSyncTime() != null
            && new Date().before(cache.getNextSyncTime()) && cache.getCachedData() != null) {
            return cache.getCachedData();
        }

        // Cache expired or never synced — sync now
        BizD365Cache synced = syncEndpoint(cache.getCacheId());
        return synced != null ? synced.getCachedData() : null;
    }

    @Override
    public BizD365Cache syncEndpoint(Long cacheId) {
        BizD365Cache cache = mapper.selectById(cacheId);
        if (cache == null) return null;

        cache.setSyncStatus("syncing");
        mapper.update(cache);

        try {
            Map<String, Object> extra = null;
            if (cache.getExtraParams() != null && !cache.getExtraParams().isEmpty()) {
                extra = JSON.parseObject(cache.getExtraParams(), Map.class);
            }

            String raw = d365Proxy.callLookup(cache.getEndpointName(), extra);

            // Count records
            int count = 0;
            if (raw != null) {
                try {
                    Object parsed = JSON.parse(raw);
                    if (parsed instanceof List) count = ((List<?>) parsed).size();
                    else if (parsed instanceof Map) {
                        // Try to find array inside
                        for (Object v : ((Map<?, ?>) parsed).values()) {
                            if (v instanceof List) { count = ((List<?>) v).size(); break; }
                            if (v instanceof String) {
                                try { Object p2 = JSON.parse((String) v); if (p2 instanceof List) { count = ((List<?>) p2).size(); break; } } catch (Exception ignored) {}
                            }
                        }
                    }
                } catch (Exception ignored) {}
            }

            cache.setCachedData(raw);
            cache.setRecordCount(count);
            cache.setLastSyncTime(new Date());
            cache.setSyncStatus("success");
            cache.setErrorMessage(null);

            // Calculate next sync time
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.HOUR_OF_DAY, cache.getTtlHours() != null ? cache.getTtlHours() : 24);
            cache.setNextSyncTime(cal.getTime());

            mapper.update(cache);
            log.info("Cache synced: {} → {} records", cache.getEndpointName(), count);
            return cache;

        } catch (Exception e) {
            log.error("Cache sync failed: {}", cache.getEndpointName(), e);
            cache.setSyncStatus("failed");
            cache.setErrorMessage(e.getMessage() != null && e.getMessage().length() > 2000
                ? e.getMessage().substring(0, 2000) : e.getMessage());
            mapper.update(cache);
            return cache;
        }
    }

    @Override
    public int syncAll() {
        List<BizD365Cache> all = mapper.selectList(new BizD365Cache());
        int ok = 0;
        for (BizD365Cache c : all) {
            BizD365Cache result = syncEndpoint(c.getCacheId());
            if (result != null && "success".equals(result.getSyncStatus())) ok++;
        }
        return ok;
    }
}
