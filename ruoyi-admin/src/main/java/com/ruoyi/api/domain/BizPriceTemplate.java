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
 * All font-size settings for every field on a price tag are stored here.
 * During printing, font values are retrieved by joining through price_template_id.
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

    /** Label width in millimetres */
    @Excel(name = "Width (mm)")
    @DecimalMin(value = "1.0",  message = "Width must be at least 1 mm")
    @DecimalMax(value = "9999.0", message = "Width cannot exceed 9999 mm")
    private BigDecimal widthMm;

    /** Label height in millimetres */
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

    // ── NEW price font sizes (السعر الجديد / price after) ───────────────────
    /** Font size (pt) for digits LEFT  of decimal in the NEW price  e.g. "5"   in "5.80" */
    @Excel(name = "New Price Integer Font Size (pt)")
    private Integer priceAfterIntegerFontSize;

    /** Font size (pt) for digits RIGHT of decimal in the NEW price  e.g. ".80" in "5.80" */
    @Excel(name = "New Price Decimal Font Size (pt)")
    private Integer priceAfterDecimalFontSize;

    // ── OLD price font sizes (السعر القديم / price before) ──────────────────
    /** Font size (pt) for digits LEFT  of decimal in the OLD price  e.g. "8"   in "8.80" */
    @Excel(name = "Old Price Integer Font Size (pt)")
    private Integer priceBeforeIntegerFontSize;

    /** Font size (pt) for digits RIGHT of decimal in the OLD price  e.g. ".80" in "8.80" */
    @Excel(name = "Old Price Decimal Font Size (pt)")
    private Integer priceBeforeDecimalFontSize;

    // ── Other field font sizes ────────────────────────────────────────────────
    /** Font size (pt) for the product name */
    @Excel(name = "Product Name Font Size (pt)")
    private Integer fontProductName;

    /** Font size (pt) for the barcode */
    @Excel(name = "Barcode Font Size (pt)")
    private Integer fontBarcode;

    /** Font size (pt) for the quantity field */
    @Excel(name = "Quantity Font Size (pt)")
    private Integer fontQuantity;

    /** Font size (pt) for the per-customer limit field */
    @Excel(name = "Per-Customer Font Size (pt)")
    private Integer fontPerCustomer;

    // ── Product name override / truncation ───────────────────────────────────
    /**
     * Optional manual override for the product name from the API.
     * Null / blank = use the API name as-is.
     */
    @Excel(name = "Product Name Override")
    @Size(min = 0, max = 100, message = "Product name override cannot exceed 100 characters")
    private String productNameOverride;

    /**
     * Max characters to display before auto-truncating with "…".
     * Default: 35.
     */
    @Excel(name = "Name Max Characters")
    private Integer nameMaxChars;

    // ─────────────────────────────────────────────────────────────────────────
    // Getters & Setters
    // ─────────────────────────────────────────────────────────────────────────

    public Long getPriceTemplateId() { return priceTemplateId; }
    public void setPriceTemplateId(Long priceTemplateId) { this.priceTemplateId = priceTemplateId; }

    public String getPriceTemplateName() { return priceTemplateName; }
    public void setPriceTemplateName(String priceTemplateName) { this.priceTemplateName = priceTemplateName; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public BigDecimal getWidthMm() { return widthMm; }
    public void setWidthMm(BigDecimal widthMm) { this.widthMm = widthMm; }

    public BigDecimal getHeightMm() { return heightMm; }
    public void setHeightMm(BigDecimal heightMm) { this.heightMm = heightMm; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getIsDefault() { return isDefault; }
    public void setIsDefault(String isDefault) { this.isDefault = isDefault; }

    public String getDelFlag() { return delFlag; }
    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }

    public Integer getPriceAfterIntegerFontSize() { return priceAfterIntegerFontSize; }
    public void setPriceAfterIntegerFontSize(Integer v) { this.priceAfterIntegerFontSize = v; }

    public Integer getPriceAfterDecimalFontSize() { return priceAfterDecimalFontSize; }
    public void setPriceAfterDecimalFontSize(Integer v) { this.priceAfterDecimalFontSize = v; }

    public Integer getPriceBeforeIntegerFontSize() { return priceBeforeIntegerFontSize; }
    public void setPriceBeforeIntegerFontSize(Integer v) { this.priceBeforeIntegerFontSize = v; }

    public Integer getPriceBeforeDecimalFontSize() { return priceBeforeDecimalFontSize; }
    public void setPriceBeforeDecimalFontSize(Integer v) { this.priceBeforeDecimalFontSize = v; }

    public Integer getFontProductName() { return fontProductName; }
    public void setFontProductName(Integer fontProductName) { this.fontProductName = fontProductName; }



    public Integer getFontBarcode() { return fontBarcode; }
    public void setFontBarcode(Integer fontBarcode) { this.fontBarcode = fontBarcode; }

    public Integer getFontQuantity() { return fontQuantity; }
    public void setFontQuantity(Integer fontQuantity) { this.fontQuantity = fontQuantity; }

    public Integer getFontPerCustomer() { return fontPerCustomer; }
    public void setFontPerCustomer(Integer fontPerCustomer) { this.fontPerCustomer = fontPerCustomer; }

    public String getProductNameOverride() { return productNameOverride; }
    public void setProductNameOverride(String productNameOverride) { this.productNameOverride = productNameOverride; }

    public Integer getNameMaxChars() { return nameMaxChars; }
    public void setNameMaxChars(Integer nameMaxChars) { this.nameMaxChars = nameMaxChars; }

    @Override
    public String toString()
    {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("priceTemplateId",      getPriceTemplateId())
                .append("priceTemplateName",     getPriceTemplateName())
                .append("size",                  getSize())
                .append("widthMm",               getWidthMm())
                .append("heightMm",              getHeightMm())
                .append("status",                getStatus())
                .append("isDefault",             getIsDefault())
                .append("delFlag",               getDelFlag())
                .append("priceAfterIntegerFontSize",  getPriceAfterIntegerFontSize())
                .append("priceAfterDecimalFontSize",  getPriceAfterDecimalFontSize())
                .append("priceBeforeIntegerFontSize", getPriceBeforeIntegerFontSize())
                .append("priceBeforeDecimalFontSize", getPriceBeforeDecimalFontSize())
                .append("fontProductName",       getFontProductName())

                .append("fontBarcode",           getFontBarcode())
                .append("fontQuantity",          getFontQuantity())
                .append("fontPerCustomer",       getFontPerCustomer())
                .append("productNameOverride",   getProductNameOverride())
                .append("nameMaxChars",          getNameMaxChars())
                .append("createBy",              getCreateBy())
                .append("createTime",            getCreateTime())
                .append("updateBy",              getUpdateBy())
                .append("updateTime",            getUpdateTime())
                .append("remark",                getRemark())
                .toString();
    }
}
