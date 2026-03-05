package com.ruoyi.api.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Product Number Sequence Entity biz_product_number_seq
 * تسلسل أرقام المنتجات
 */
public class BizProductNumberSeq extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long seqId;

    @Excel(name = "Prefix")
    private String prefix;

    @Excel(name = "Current Value")
    private Long currentValue;

    @Excel(name = "Increment By")
    private Integer incrementBy;

    private Long minValue;
    private Long maxValue;

    @Excel(name = "Pad Length")
    private Integer padLength;

    @Excel(name = "Description")
    private String description;

    private String status;

    public Long getSeqId() { return seqId; }
    public void setSeqId(Long seqId) { this.seqId = seqId; }

    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }

    public Long getCurrentValue() { return currentValue; }
    public void setCurrentValue(Long currentValue) { this.currentValue = currentValue; }

    public Integer getIncrementBy() { return incrementBy; }
    public void setIncrementBy(Integer incrementBy) { this.incrementBy = incrementBy; }

    public Long getMinValue() { return minValue; }
    public void setMinValue(Long minValue) { this.minValue = minValue; }

    public Long getMaxValue() { return maxValue; }
    public void setMaxValue(Long maxValue) { this.maxValue = maxValue; }

    public Integer getPadLength() { return padLength; }
    public void setPadLength(Integer padLength) { this.padLength = padLength; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
