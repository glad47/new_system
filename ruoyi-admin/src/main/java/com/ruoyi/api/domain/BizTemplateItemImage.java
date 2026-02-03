package com.ruoyi.api.domain;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Template Item Image Entity biz_template_item_image
 * 
 * @author ruoyi
 */
public class BizTemplateItemImage extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Image ID */
    private Long imageId;

    /** Template Item ID */
    @NotNull(message = "Template Item ID cannot be empty")
    private Long templateItemId;

    /** Image URL (MinIO) */
    @Excel(name = "Image URL")
    @NotBlank(message = "Image URL cannot be empty")
    @Size(min = 0, max = 500, message = "Image URL cannot exceed 500 characters")
    private String imageUrl;

    /** Image Name */
    @Excel(name = "Image Name")
    @Size(min = 0, max = 200, message = "Image name cannot exceed 200 characters")
    private String imageName;

    /** Image Type (e.g., jpg, png) */
    @Excel(name = "Image Type")
    @Size(min = 0, max = 50, message = "Image type cannot exceed 50 characters")
    private String imageType;

    /** Image Size (bytes) */
    @Excel(name = "Image Size")
    private Long imageSize;

    /** Sort Order */
    @Excel(name = "Sort Order")
    private Integer sortOrder;

    /** Delete Flag (0=Exist, 2=Deleted) */
    private String delFlag;

    public void setImageId(Long imageId)
    {
        this.imageId = imageId;
    }

    public Long getImageId()
    {
        return imageId;
    }

    public void setTemplateItemId(Long templateItemId)
    {
        this.templateItemId = templateItemId;
    }

    public Long getTemplateItemId()
    {
        return templateItemId;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageName(String imageName)
    {
        this.imageName = imageName;
    }

    public String getImageName()
    {
        return imageName;
    }

    public void setImageType(String imageType)
    {
        this.imageType = imageType;
    }

    public String getImageType()
    {
        return imageType;
    }

    public void setImageSize(Long imageSize)
    {
        this.imageSize = imageSize;
    }

    public Long getImageSize()
    {
        return imageSize;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("imageId", getImageId())
            .append("templateItemId", getTemplateItemId())
            .append("imageUrl", getImageUrl())
            .append("imageName", getImageName())
            .append("imageType", getImageType())
            .append("imageSize", getImageSize())
            .append("sortOrder", getSortOrder())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
