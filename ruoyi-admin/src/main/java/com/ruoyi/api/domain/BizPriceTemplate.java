package com.ruoyi.api.domain;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * Price Template Entity  biz_price_template
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

    /** Size label (e.g., S, M, L, XL) */
    @Excel(name = "Size")
    @Size(min = 0, max = 50, message = "Size cannot exceed 50 characters")
    private String size;

    /**
     * Label width in millimetres.
     * Examples: 210 = A4-width, 105 = A6-width, 20 = tiny label
     */
    @Excel(name = "Width (mm)")
    @DecimalMin(value = "1.0",  message = "Width must be at least 1 mm")
    @DecimalMax(value = "9999.0", message = "Width cannot exceed 9999 mm")
    private BigDecimal widthMm;

    /**
     * Label height in millimetres.
     * Examples: 297 = A4-height, 148.5 = A6-height, 20 = tiny label
     */
    @Excel(name = "Height (mm)")
    @DecimalMin(value = "1.0",  message = "Height must be at least 1 mm")
    @DecimalMax(value = "9999.0", message = "Height cannot exceed 9999 mm")
    private BigDecimal heightMm;

    /** Status (0=Normal, 1=Disabled) */
    @Excel(name = "Status", readConverterExp = "0=Normal,1=Disabled")
    private String status;

    /** Is Default (0=No, 1=Yes) */
    @Excel(name = "Is Default", readConverterExp = "0=No,1=Yes")
    private String isDefault;

    /** Delete Flag (0=Exist, 2=Deleted) */
    private String delFlag;

    // ----------------------------------------------------------------
    // Getters & Setters
    // ----------------------------------------------------------------

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

    public BigDecimal getWidthMm()
    {
        return widthMm;
    }

    public void setWidthMm(BigDecimal widthMm)
    {
        this.widthMm = widthMm;
    }

    public BigDecimal getHeightMm()
    {
        return heightMm;
    }

    public void setHeightMm(BigDecimal heightMm)
    {
        this.heightMm = heightMm;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
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
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("priceTemplateId",   getPriceTemplateId())
                .append("priceTemplateName", getPriceTemplateName())
                .append("size",              getSize())
                .append("widthMm",           getWidthMm())
                .append("heightMm",          getHeightMm())
                .append("status",            getStatus())
                .append("isDefault",         getIsDefault())
                .append("delFlag",           getDelFlag())
                .append("createBy",          getCreateBy())
                .append("createTime",        getCreateTime())
                .append("updateBy",          getUpdateBy())
                .append("updateTime",        getUpdateTime())
                .append("remark",            getRemark())
                .toString();
    }
}