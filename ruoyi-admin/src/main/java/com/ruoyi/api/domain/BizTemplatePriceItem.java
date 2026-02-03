package com.ruoyi.api.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Template Price Item Entity biz_template_price_item
 * Junction table linking template_price to template_item
 * 
 * @author ruoyi
 */
public class BizTemplatePriceItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** ID */
    private Long id;

    /** Template Price ID */
    @NotNull(message = "Template Price ID cannot be empty")
    private Long templatePriceId;

    /** Template Item ID */
    @NotNull(message = "Template Item ID cannot be empty")
    private Long templateItemId;

    /** Sort Order */
    private Integer sortOrder;

    /** Delete Flag (0=Exist, 2=Deleted) */
    private String delFlag;

    /** Template Item - for display */
    private BizTemplateItem templateItem;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public void setTemplatePriceId(Long templatePriceId)
    {
        this.templatePriceId = templatePriceId;
    }

    public Long getTemplatePriceId()
    {
        return templatePriceId;
    }

    public void setTemplateItemId(Long templateItemId)
    {
        this.templateItemId = templateItemId;
    }

    public Long getTemplateItemId()
    {
        return templateItemId;
    }

    public void setSortOrder(Integer sortOrder)
    {
        this.sortOrder = sortOrder;
    }

    public Integer getSortOrder()
    {
        return sortOrder;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public BizTemplateItem getTemplateItem()
    {
        return templateItem;
    }

    public void setTemplateItem(BizTemplateItem templateItem)
    {
        this.templateItem = templateItem;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("templatePriceId", getTemplatePriceId())
            .append("templateItemId", getTemplateItemId())
            .append("sortOrder", getSortOrder())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
