package com.ruoyi.api.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * D365 Configuration Entity biz_d365_config
 * إعدادات D365
 */
public class BizD365Config extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long configId;

    @Excel(name = "Config Key")
    @NotBlank(message = "Config key cannot be empty")
    @Size(min = 0, max = 100)
    private String configKey;

    @Excel(name = "Config Value")
    @NotBlank(message = "Config value cannot be empty")
    @Size(min = 0, max = 500)
    private String configValue;

    @Excel(name = "Config Label")
    private String configLabel;

    private String configType;
    private String status;
    private String delFlag;

    public Long getConfigId() { return configId; }
    public void setConfigId(Long configId) { this.configId = configId; }

    public String getConfigKey() { return configKey; }
    public void setConfigKey(String configKey) { this.configKey = configKey; }

    public String getConfigValue() { return configValue; }
    public void setConfigValue(String configValue) { this.configValue = configValue; }

    public String getConfigLabel() { return configLabel; }
    public void setConfigLabel(String configLabel) { this.configLabel = configLabel; }

    public String getConfigType() { return configType; }
    public void setConfigType(String configType) { this.configType = configType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDelFlag() { return delFlag; }
    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }
}
