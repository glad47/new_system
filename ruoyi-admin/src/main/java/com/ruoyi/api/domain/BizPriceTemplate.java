package com.ruoyi.api.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Price Template Entity biz_price_template
 * Simple entity - just name and size for pricing
 * 
 * @author ruoyi
 */
public class BizPriceTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Price Template ID */
    private Long priceTemplateId;

    /** Price Template Name */
    @Excel(name = "Price Template Name")
    @NotBlank(message = "Price template name cannot be empty")
    @Size(min = 0, max = 100, message = "Price template name cannot exceed 100 characters")
    private String priceTemplateName;

    /** Size (e.g., S, M, L, XL or dimensions) */
    @Excel(name = "Size")
    @Size(min = 0, max = 50, message = "Size cannot exceed 50 characters")
    private String size;

    /** Status (0=Normal, 1=Disabled) */
    @Excel(name = "Status", readConverterExp = "0=Normal,1=Disabled")
    private String status;

    /** Delete Flag (0=Exist, 2=Deleted) */
    private String delFlag;

    public Long getPriceTemplateId()
    {
        return priceTemplateId;
    }

    public void setPriceTemplateId(Long priceTemplateId)
    {
        this.priceTemplateId = priceTemplateId;
    }

    public String getPriceTemplateName()
    {
        return priceTemplateName;
    }

    public void setPriceTemplateName(String priceTemplateName)
    {
        this.priceTemplateName = priceTemplateName;
    }

    public String getSize()
    {
        return size;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("priceTemplateId", getPriceTemplateId())
            .append("priceTemplateName", getPriceTemplateName())
            .append("size", getSize())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
