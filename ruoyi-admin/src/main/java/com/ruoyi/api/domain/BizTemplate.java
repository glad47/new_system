package com.ruoyi.api.domain;

import java.util.List;
import jakarta.validation.constraints.NotBlank;
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

    /** Is Default Template (0=No, 1=Yes) */
    @Excel(name = "Is Default", readConverterExp = "0=No,1=Yes")
    private String isDefault;

    /** Delete Flag (0=Exist, 2=Deleted) */
    private String delFlag;

    /** Template Items - one-to-many */
    private List<BizTemplateItem> templateItems;

    public Long getTemplateId() { return templateId; }
    public void setTemplateId(Long templateId) { this.templateId = templateId; }

    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getIsDefault() { return isDefault; }
    public void setIsDefault(String isDefault) { this.isDefault = isDefault; }

    public String getDelFlag() { return delFlag; }
    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }

    public List<BizTemplateItem> getTemplateItems() { return templateItems; }
    public void setTemplateItems(List<BizTemplateItem> templateItems) { this.templateItems = templateItems; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("templateId", getTemplateId())
                .append("templateName", getTemplateName())
                .append("note", getNote())
                .append("status", getStatus())
                .append("isDefault", getIsDefault())
                .append("delFlag", getDelFlag())
                .append("createBy", getCreateBy())
                .append("createTime", getCreateTime())
                .append("updateBy", getUpdateBy())
                .append("updateTime", getUpdateTime())
                .append("remark", getRemark())
                .toString();
    }
}