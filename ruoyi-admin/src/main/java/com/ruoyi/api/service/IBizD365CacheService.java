package com.ruoyi.api.service;

import java.util.List;
import com.ruoyi.api.domain.BizD365Cache;

public interface IBizD365CacheService {
    List<BizD365Cache> selectList(BizD365Cache cache);
    BizD365Cache selectById(Long cacheId);
    /** Get cached data for an endpoint — returns cached JSON or null if expired/missing */
    String getCachedData(String endpointName, String extraParams);
    /** Sync one endpoint from D365 and store in cache */
    BizD365Cache syncEndpoint(Long cacheId);
    /** Sync all endpoints */
    int syncAll();
    int insert(BizD365Cache cache);
    int update(BizD365Cache cache);
    int deleteByIds(Long[] ids);
}
