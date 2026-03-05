package com.ruoyi.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.api.domain.BizD365Config;
import com.ruoyi.api.mapper.BizD365ConfigMapper;
import com.ruoyi.api.service.IBizD365ConfigService;

@Service
public class BizD365ConfigServiceImpl implements IBizD365ConfigService
{
    @Autowired
    private BizD365ConfigMapper mapper;

    @Override
    public List<BizD365Config> selectBizD365ConfigList(BizD365Config config) {
        return mapper.selectBizD365ConfigList(config);
    }

    @Override
    public BizD365Config selectBizD365ConfigById(Long configId) {
        return mapper.selectBizD365ConfigById(configId);
    }

    @Override
    public BizD365Config selectBizD365ConfigByKey(String configKey) {
        return mapper.selectBizD365ConfigByKey(configKey);
    }

    @Override
    public int insertBizD365Config(BizD365Config config) {
        return mapper.insertBizD365Config(config);
    }

    @Override
    public int updateBizD365Config(BizD365Config config) {
        return mapper.updateBizD365Config(config);
    }

    @Override
    public int deleteBizD365ConfigByIds(Long[] configIds) {
        return mapper.deleteBizD365ConfigByIds(configIds);
    }
}
