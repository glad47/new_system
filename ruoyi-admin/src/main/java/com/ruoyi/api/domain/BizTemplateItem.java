package com.ruoyi.api.domain;

import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Template Item Entity biz_template_item
 * Each item belongs to a Template and has ONE image
 * 
 * @author ruoyi
 */
public class BizTemplateItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Template Item ID */
    private Long templateItemId;

    /** Template ID (FK) - belongs to template */
    private Long templateId;

    /** Price Template ID (FK, optional) - reference to price template */
    private Long priceTemplateId;

    /** Price Template - loaded relation */
    private BizPriceTemplate priceTemplate;

    /** Item Name (fallback if no price template) */
    @Excel(name = "Item Name")
    @Size(min = 0, max = 100, message = "Item name cannot exceed 100 characters")
    private String itemName;

    /** Image URL (MinIO) - ONE image per item */
    @Excel(name = "Image URL")
    @Size(min = 0, max = 500, message = "Image URL cannot exceed 500 characters")
    private String imageUrl;

    /** Image Name */
    @Size(min = 0, max = 200, message = "Image name cannot exceed 200 characters")
    private String imageName;

    /** Image Type (e.g., jpg, png) */
    @Size(min = 0, max = 50, message = "Image type cannot exceed 50 characters")
    private String imageType;

    /** Image Size (bytes) */
    private Long imageSize;

    /**
     * Image field annotations - stored as JSON string in DB.
     * Contains normalized (0-1) coordinates for each field position on the image.
     * Example: {"productName":{"fieldKey":"productName","label":"Product Name","x":0.05,"y":0.08,"width":0.9,"height":0.15},...}
     */
    private String imageAnnotations;

    // ── Font sizes (pt) stored per template item ──────────────────────────────
    /** Font size (pt) for digits LEFT  of decimal in the NEW price  e.g. "5"   in "5.80" */
    private Integer priceAfterIntegerFontSize;

    /** Font size (pt) for digits RIGHT of decimal in the NEW price  e.g. ".80" in "5.80" */
    private Integer priceAfterDecimalFontSize;

    /** Font size (pt) for digits LEFT  of decimal in the OLD price  e.g. "8"   in "8.80" */
    private Integer priceBeforeIntegerFontSize;

    /** Font size (pt) for digits RIGHT of decimal in the OLD price  e.g. ".80" in "8.80" */
    private Integer priceBeforeDecimalFontSize;

    /** Font size (pt) for the product name */
    private Integer fontProductName;

    /** Font size (pt) for the barcode */
    private Integer fontBarcode;

    /** Font size (pt) for the quantity field */
    private Integer fontQuantity;

    /** Font size (pt) for the per-customer limit field */
    private Integer fontPerCustomer;

    // ── Font colors (CSS hex e.g. #ffffff) stored per template item ───────────
    /** Font color for the product name */
    private String colorProductName;

    /** Font color for the new price (priceAfter) */
    private String colorPriceAfter;

    /** Font color for the old price (priceBefore) */
    private String colorPriceBefore;

    /** Font color for the barcode */
    private String colorBarcode;

    /** Font color for the quantity field */
    private String colorQuantity;

    /** Font color for the per-customer field */
    private String colorPerCustomer;

    /** Is Default Item (0=No, 1=Yes) */
    @Excel(name = "Is Default", readConverterExp = "0=No,1=Yes")
    private String isDefault;

    /** Sort Order */
    private Integer sortOrder;

    /** Status (0=Normal, 1=Disabled) */
    @Excel(name = "Status", readConverterExp = "0=Normal,1=Disabled")
    private String status;

    /** Delete Flag (0=Exist, 2=Deleted) */
    private String delFlag;

    public Long getTemplateItemId()
    {
        return templateItemId;
    }

    public void setTemplateItemId(Long templateItemId)
    {
        this.templateItemId = templateItemId;
    }

    public Long getTemplateId()
    {
        return templateId;
    }

    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }

    public Long getPriceTemplateId()
    {
        return priceTemplateId;
    }

    public void setPriceTemplateId(Long priceTemplateId)
    {
        this.priceTemplateId = priceTemplateId;
    }

    public BizPriceTemplate getPriceTemplate()
    {
        return priceTemplate;
    }

    public void setPriceTemplate(BizPriceTemplate priceTemplate)
    {
        this.priceTemplate = priceTemplate;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setImageName(String imageName)
    {
        this.imageName = imageName;
    }

    public String getImageType()
    {
        return imageType;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }

    public Long getImageSize()
    {
        return imageSize;
    }

    public void setImageSize(Long imageSize)
    {
        this.imageSize = imageSize;
    }

    public String getImageAnnotations()
    {
        return imageAnnotations;
    }

    public void setImageAnnotations(String imageAnnotations)
    {
        this.imageAnnotations = imageAnnotations;
    }

    public String getIsDefault()
    {
        return isDefault;
    }

    public void setIsDefault(String isDefault)
    {
        this.isDefault = isDefault;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
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

    public Integer getPriceAfterIntegerFontSize() { return priceAfterIntegerFontSize; }
    public void setPriceAfterIntegerFontSize(Integer v) { this.priceAfterIntegerFontSize = v; }

    public Integer getPriceAfterDecimalFontSize() { return priceAfterDecimalFontSize; }
    public void setPriceAfterDecimalFontSize(Integer v) { this.priceAfterDecimalFontSize = v; }

    public Integer getPriceBeforeIntegerFontSize() { return priceBeforeIntegerFontSize; }
    public void setPriceBeforeIntegerFontSize(Integer v) { this.priceBeforeIntegerFontSize = v; }

    public Integer getPriceBeforeDecimalFontSize() { return priceBeforeDecimalFontSize; }
    public void setPriceBeforeDecimalFontSize(Integer v) { this.priceBeforeDecimalFontSize = v; }

    public Integer getFontProductName() { return fontProductName; }
    public void setFontProductName(Integer v) { this.fontProductName = v; }

    public Integer getFontBarcode() { return fontBarcode; }
    public void setFontBarcode(Integer v) { this.fontBarcode = v; }

    public Integer getFontQuantity() { return fontQuantity; }
    public void setFontQuantity(Integer v) { this.fontQuantity = v; }

    public Integer getFontPerCustomer() { return fontPerCustomer; }
    public void setFontPerCustomer(Integer v) { this.fontPerCustomer = v; }

    public String getColorProductName() { return colorProductName; }
    public void setColorProductName(String v) { this.colorProductName = v; }

    public String getColorPriceAfter() { return colorPriceAfter; }
    public void setColorPriceAfter(String v) { this.colorPriceAfter = v; }

    public String getColorPriceBefore() { return colorPriceBefore; }
    public void setColorPriceBefore(String v) { this.colorPriceBefore = v; }

    public String getColorBarcode() { return colorBarcode; }
    public void setColorBarcode(String v) { this.colorBarcode = v; }

    public String getColorQuantity() { return colorQuantity; }
    public void setColorQuantity(String v) { this.colorQuantity = v; }

    public String getColorPerCustomer() { return colorPerCustomer; }
    public void setColorPerCustomer(String v) { this.colorPerCustomer = v; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("templateItemId", getTemplateItemId())
            .append("templateId", getTemplateId())
            .append("priceTemplateId", getPriceTemplateId())
            .append("itemName", getItemName())
            .append("imageUrl", getImageUrl())
            .append("imageAnnotations", getImageAnnotations())
            .append("isDefault", getIsDefault())
            .append("sortOrder", getSortOrder())
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
