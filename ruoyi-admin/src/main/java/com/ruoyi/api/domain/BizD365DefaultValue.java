package com.ruoyi.api.domain;

import com.ruoyi.common.core.domain.BaseEntity;

/**
 * القيم الافتراضية لنقاط النهاية | D365 Endpoint Default Values
 */
public class BizD365DefaultValue extends BaseEntity {
    private Long defaultId;
    private String endpointName;
    private String endpointLabel;
    private String extraParams;
    private String defaultValue;
    private String defaultLabel;
    private String showInUi;
    private String status;
    private String delFlag;

    public Long getDefaultId() { return defaultId; }
    public void setDefaultId(Long defaultId) { this.defaultId = defaultId; }
    public String getEndpointName() { return endpointName; }
    public void setEndpointName(String endpointName) { this.endpointName = endpointName; }
    public String getEndpointLabel() { return endpointLabel; }
    public void setEndpointLabel(String endpointLabel) { this.endpointLabel = endpointLabel; }
    public String getExtraParams() { return extraParams; }
    public void setExtraParams(String extraParams) { this.extraParams = extraParams; }
    public String getDefaultValue() { return defaultValue; }
    public void setDefaultValue(String defaultValue) { this.defaultValue = defaultValue; }
    public String getDefaultLabel() { return defaultLabel; }
    public void setDefaultLabel(String defaultLabel) { this.defaultLabel = defaultLabel; }
    public String getShowInUi() { return showInUi; }
    public void setShowInUi(String showInUi) { this.showInUi = showInUi; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDelFlag() { return delFlag; }
    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }
}
