package com.ruoyi.api.mapper;

import java.util.List;
import com.ruoyi.api.domain.BizD365DefaultValue;

public interface BizD365DefaultValueMapper {
    List<BizD365DefaultValue> selectList(BizD365DefaultValue query);
    BizD365DefaultValue selectById(Long defaultId);
    BizD365DefaultValue selectByEndpoint(String endpointName, String extraParams);
    List<BizD365DefaultValue> selectAllDefaults();
    int insert(BizD365DefaultValue record);
    int update(BizD365DefaultValue record);
    int deleteByIds(Long[] ids);
}
