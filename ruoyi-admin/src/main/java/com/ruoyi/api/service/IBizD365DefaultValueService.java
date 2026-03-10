package com.ruoyi.api.service;

import java.util.List;
import java.util.Map;
import com.ruoyi.api.domain.BizD365DefaultValue;

public interface IBizD365DefaultValueService {
    List<BizD365DefaultValue> selectList(BizD365DefaultValue query);
    BizD365DefaultValue selectById(Long defaultId);
    BizD365DefaultValue selectByEndpoint(String endpointName, String extraParams);

    /**
     * Get all configured defaults as a map: endpointName -> defaultValue
     * Used by product creation to apply defaults without relying on isDefault in cached data
     */
    Map<String, String> getAllDefaultsMap();

    int insert(BizD365DefaultValue record);
    int update(BizD365DefaultValue record);
    int deleteByIds(Long[] ids);
}
