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
 * Template Item Entity biz_template_item
 * 
 * @author ruoyi
 */
public class BizTemplateItem extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Template Item ID */
    private Long templateItemId;

    /** Item Name */
    @Excel(name = "Item Name")
    @NotBlank(message = "Item name cannot be empty")
    @Size(min = 0, max = 100, message = "Item name cannot exceed 100 characters")
    private String itemName;

    /** Size */
    @Excel(name = "Size")
    @Size(min = 0, max = 50, message = "Size cannot exceed 50 characters")
    private String size;

    /** Status (0=Normal, 1=Disabled) */
    @Excel(name = "Status", readConverterExp = "0=Normal,1=Disabled")
    private String status;

    /** Delete Flag (0=Exist, 2=Deleted) */
    private String delFlag;

    /** Image list - for display purposes */
    private List<BizTemplateItemImage> images;

    /** Image count - for list view */
    private Integer imageCount;

    public void setTemplateItemId(Long templateItemId)
    {
        this.templateItemId = templateItemId;
    }

    public Long getTemplateItemId()
    {
        return templateItemId;
    }

    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }

    public String getItemName()
    {
        return itemName;
    }

    public void setSize(String size)
    {
        this.size = size;
    }

    public String getSize()
    {
        return size;
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

    public List<BizTemplateItemImage> getImages()
    {
        return images;
    }

    public void setImages(List<BizTemplateItemImage> images)
    {
        this.images = images;
    }

    public Integer getImageCount()
    {
        return imageCount;
    }

    public void setImageCount(Integer imageCount)
    {
        this.imageCount = imageCount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("templateItemId", getTemplateItemId())
            .append("itemName", getItemName())
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
