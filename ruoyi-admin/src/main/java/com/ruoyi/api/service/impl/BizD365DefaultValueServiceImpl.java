package com.ruoyi.api.service.impl;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.api.domain.BizD365DefaultValue;
import com.ruoyi.api.mapper.BizD365DefaultValueMapper;
import com.ruoyi.api.service.IBizD365DefaultValueService;

@Service
public class BizD365DefaultValueServiceImpl implements IBizD365DefaultValueService {

    @Autowired
    private BizD365DefaultValueMapper mapper;

    @Override
    public List<BizD365DefaultValue> selectList(BizD365DefaultValue query) {
        return mapper.selectList(query);
    }

    @Override
    public BizD365DefaultValue selectById(Long defaultId) {
        return mapper.selectById(defaultId);
    }

    @Override
    public BizD365DefaultValue selectByEndpoint(String endpointName, String extraParams) {
        return mapper.selectByEndpoint(endpointName, extraParams);
    }

    @Override
    public Map<String, String> getAllDefaultsMap() {
        List<BizD365DefaultValue> list = mapper.selectAllDefaults();
        Map<String, String> map = new LinkedHashMap<>();
        for (BizD365DefaultValue dv : list) {
            // Key = endpointName (+ extraParams if present)
            String key = dv.getEndpointName();
            if (dv.getExtraParams() != null && !dv.getExtraParams().isEmpty()) {
                key = key + "|" + dv.getExtraParams();
            }
            map.put(key, dv.getDefaultValue());
        }
        return map;
    }

    @Override
    public int insert(BizD365DefaultValue record) {
        return mapper.insert(record);
    }

    @Override
    public int update(BizD365DefaultValue record) {
        return mapper.update(record);
    }

    @Override
    public int deleteByIds(Long[] ids) {
        return mapper.deleteByIds(ids);
    }
}
