package com.ruoyi.api.mapper;

import java.util.List;
import com.ruoyi.api.domain.BizD365Config;

public interface BizD365ConfigMapper
{
    List<BizD365Config> selectBizD365ConfigList(BizD365Config config);
    BizD365Config selectBizD365ConfigById(Long configId);
    BizD365Config selectBizD365ConfigByKey(String configKey);
    int insertBizD365Config(BizD365Config config);
    int updateBizD365Config(BizD365Config config);
    int deleteBizD365ConfigByIds(Long[] configIds);
}
