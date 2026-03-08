package com.ruoyi.api.domain;

import java.util.Date;
import com.ruoyi.common.core.domain.BaseEntity;

public class BizD365Cache extends BaseEntity {
    private Long cacheId;
    private String endpointName;
    private String endpointLabel;
    private String extraParams;
    private String cachedData;
    private Integer ttlHours;
    private Date lastSyncTime;
    private Date nextSyncTime;
    private String syncStatus;
    private String errorMessage;
    private Integer recordCount;
    private String status;

    public Long getCacheId() { return cacheId; }
    public void setCacheId(Long cacheId) { this.cacheId = cacheId; }
    public String getEndpointName() { return endpointName; }
    public void setEndpointName(String endpointName) { this.endpointName = endpointName; }
    public String getEndpointLabel() { return endpointLabel; }
    public void setEndpointLabel(String endpointLabel) { this.endpointLabel = endpointLabel; }
    public String getExtraParams() { return extraParams; }
    public void setExtraParams(String extraParams) { this.extraParams = extraParams; }
    public String getCachedData() { return cachedData; }
    public void setCachedData(String cachedData) { this.cachedData = cachedData; }
    public Integer getTtlHours() { return ttlHours; }
    public void setTtlHours(Integer ttlHours) { this.ttlHours = ttlHours; }
    public Date getLastSyncTime() { return lastSyncTime; }
    public void setLastSyncTime(Date lastSyncTime) { this.lastSyncTime = lastSyncTime; }
    public Date getNextSyncTime() { return nextSyncTime; }
    public void setNextSyncTime(Date nextSyncTime) { this.nextSyncTime = nextSyncTime; }
    public String getSyncStatus() { return syncStatus; }
    public void setSyncStatus(String syncStatus) { this.syncStatus = syncStatus; }
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    public Integer getRecordCount() { return recordCount; }
    public void setRecordCount(Integer recordCount) { this.recordCount = recordCount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
