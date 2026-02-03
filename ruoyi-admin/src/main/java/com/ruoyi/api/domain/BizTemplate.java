package com.ruoyi.api.domain;

import java.util.List;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Template Entity biz_template
 * 
 * @author ruoyi
 */
public class BizTemplate extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Template ID */
    private Long templateId;

    /** Template Name */
    @Excel(name = "Template Name")
    @NotBlank(message = "Template name cannot be empty")
    @Size(min = 0, max = 200, message = "Template name cannot exceed 200 characters")
    private String templateName;

    /** Template Note/Description */
    @Excel(name = "Note")
    private String note;

    /** Status (0=Normal, 1=Disabled) */
    @Excel(name = "Status", readConverterExp = "0=Normal,1=Disabled")
    private String status;

    /** Delete Flag (0=Exist, 2=Deleted) */
    private String delFlag;

    /** Template Price - 1:1 relationship */
    private BizTemplatePrice templatePrice;

    /** Template Items - joined through template_price_item */
    private List<BizTemplateItem> templateItems;

    /** Default Item ID - for form submission */
    private Long defaultItemId;

    /** Template Item IDs - for form submission */
    private List<Long> templateItemIds;

    public void setTemplateId(Long templateId)
    {
        this.templateId = templateId;
    }

    public Long getTemplateId()
    {
        return templateId;
    }

    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }

    public String getTemplateName()
    {
        return templateName;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public String getNote()
    {
        return note;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public BizTemplatePrice getTemplatePrice()
    {
        return templatePrice;
    }

    public void setTemplatePrice(BizTemplatePrice templatePrice)
    {
        this.templatePrice = templatePrice;
    }

    public List<BizTemplateItem> getTemplateItems()
    {
        return templateItems;
    }

    public void setTemplateItems(List<BizTemplateItem> templateItems)
    {
        this.templateItems = templateItems;
    }

    public Long getDefaultItemId()
    {
        return defaultItemId;
    }

    public void setDefaultItemId(Long defaultItemId)
    {
        this.defaultItemId = defaultItemId;
    }

    public List<Long> getTemplateItemIds()
    {
        return templateItemIds;
    }

    public void setTemplateItemIds(List<Long> templateItemIds)
    {
        this.templateItemIds = templateItemIds;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("templateId", getTemplateId())
            .append("templateName", getTemplateName())
            .append("note", getNote())
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
