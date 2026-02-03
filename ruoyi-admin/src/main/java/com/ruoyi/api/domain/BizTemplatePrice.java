package com.ruoyi.api.domain;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Template Price Entity biz_template_price
 * 1:1 relationship with biz_template
 * 
 * @author ruoyi
 */
public class BizTemplatePrice extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Template Price ID */
    private Long templatePriceId;

    /** Template ID (1:1 relationship) */
    @NotNull(message = "Template ID cannot be empty")
    private Long templateId;

    /** Default Template Item ID (Required) */
    @NotNull(message = "Default item must be selected")
    private Long defaultItemId;

    /** Delete Flag (0=Exist, 2=Deleted) */
    private String delFlag;

    /** Default Template Item - for display */
    private BizTemplateItem defaultItem;

    /** Price Items - linked template items */
    private List<BizTemplatePriceItem> priceItems;

    /** Template Items with details - for display */
    private List<BizTemplateItem> templateItems;

    public void setTemplatePriceId(Long templatePriceId)
    {
        this.templatePriceId = templatePriceId;
    }

    public Long getTemplatePriceId()
    {
        return templatePriceId;
    }

    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }

    public Long getTemplateId()
    {
        return templateId;
    }

    public void setDefaultItemId(Long defaultItemId)
    {
        this.defaultItemId = defaultItemId;
    }

    public Long getDefaultItemId()
    {
        return defaultItemId;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public BizTemplateItem getDefaultItem()
    {
        return defaultItem;
    }

    public void setDefaultItem(BizTemplateItem defaultItem)
    {
        this.defaultItem = defaultItem;
    }

    public List<BizTemplatePriceItem> getPriceItems()
    {
        return priceItems;
    }

    public void setPriceItems(List<BizTemplatePriceItem> priceItems)
    {
        this.priceItems = priceItems;
    }

    public List<BizTemplateItem> getTemplateItems()
    {
        return templateItems;
    }

    public void setTemplateItems(List<BizTemplateItem> templateItems)
    {
        this.templateItems = templateItems;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("templatePriceId", getTemplatePriceId())
            .append("templateId", getTemplateId())
            .append("defaultItemId", getDefaultItemId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
