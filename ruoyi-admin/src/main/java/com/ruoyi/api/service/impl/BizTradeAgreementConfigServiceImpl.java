package com.ruoyi.api.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.api.domain.BizTradeAgreementConfig;
import com.ruoyi.api.mapper.BizTradeAgreementConfigMapper;
import com.ruoyi.api.service.IBizTradeAgreementConfigService;

@Service
public class BizTradeAgreementConfigServiceImpl implements IBizTradeAgreementConfigService {

    @Autowired
    private BizTradeAgreementConfigMapper mapper;

    @Override
    public Map<String, String> getAllConfigMap() {
        List<BizTradeAgreementConfig> list = mapper.selectAll();
        Map<String, String> map = new HashMap<>();
        for (BizTradeAgreementConfig c : list) {
            map.put(c.getConfigKey(), c.getConfigValue() != null ? c.getConfigValue() : "");
        }
        return map;
    }

    @Override
    @Transactional
    public int saveConfig(Map<String, String> configMap) {
        int count = 0;
        for (Map.Entry<String, String> entry : configMap.entrySet()) {
            BizTradeAgreementConfig c = new BizTradeAgreementConfig();
            c.setConfigKey(entry.getKey());
            c.setConfigValue(entry.getValue());
            count += mapper.updateByKey(c);
        }
        return count;
    }
}
