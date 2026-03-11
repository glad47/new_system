package com.ruoyi.api.service;

import java.util.Map;

public interface IBizTradeAgreementConfigService {
    /**
     * Get all trade agreement config as a map: { configKey: configValue }
     */
    Map<String, String> getAllConfigMap();

    /**
     * Save multiple config values from the frontend form
     */
    int saveConfig(Map<String, String> configMap);
}
