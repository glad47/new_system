package com.ruoyi.api.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * إعدادات الاتفاقيات التجارية | Trade Agreement Configuration
 */
public class BizTradeAgreementConfig extends BaseEntity {
    private Long configId;
    private String configKey;
    private String configValue;
    private String configLabel;
    private String configGroup;
    private String status;

    public Long getConfigId() { return configId; }
    public void setConfigId(Long configId) { this.configId = configId; }
    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }
    public String getConfigValue() { return configValue; }
    public void setConfigValue(String configValue) { this.configValue = configValue; }
    public String getConfigLabel() { return configLabel; }
    public void setConfigLabel(String configLabel) { this.configLabel = configLabel; }
    public String getConfigGroup() { return configGroup; }
    public void setConfigGroup(String configGroup) { this.configGroup = configGroup; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
