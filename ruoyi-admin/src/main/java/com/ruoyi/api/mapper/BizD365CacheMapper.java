package com.ruoyi.api.mapper;

import java.util.List;
import com.ruoyi.api.domain.BizD365Cache;

public interface BizD365CacheMapper {
    List<BizD365Cache> selectList(BizD365Cache cache);
    BizD365Cache selectById(Long cacheId);
    BizD365Cache selectByEndpoint(String endpointName, String extraParams);
    int insert(BizD365Cache cache);
    int update(BizD365Cache cache);
    int deleteByIds(Long[] ids);
}
