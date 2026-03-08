package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Price Check Carousel Object biz_price_check_carousel
 * 
 * @author ruoyi
 * @date 2026-02-15
 */
public class BizPriceCheckCarousel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** Carousel Image ID */
    private Long carouselId;

    /** Image Display Name */
    @Excel(name = "Image Display Name")
    private String imageName;

    /** Image URL (MinIO) */
    @Excel(name = "Image URL")
    private String imageUrl;

    /** Original image filename */
    @Excel(name = "Original Filename")
    private String originalFilename;

    /** Image MIME type */
    @Excel(name = "Image Type")
    private String imageType;

    /** Image file size in bytes */
    @Excel(name = "Image Size")
    private Long imageSize;

    /** Display order */
    @Excel(name = "Display Order")
    private Integer displayOrder;

    /** Is active (0=No, 1=Yes) */
    @Excel(name = "Is Active")
    private String isActive;

    /** Display duration in milliseconds */
    @Excel(name = "Display Duration")
    private Integer displayDuration;

    /** Status (0=Normal, 1=Disabled) */
    @Excel(name = "Status")
    private String status;

    /** Delete Flag */
    private String delFlag;

    public void setCarouselId(Long carouselId) 
    {
        this.carouselId = carouselId;
    }

    public Long getCarouselId() 
    {
        return carouselId;
    }

    public void setImageName(String imageName) 
    {
        this.imageName = imageName;
    }

    public String getImageName() 
    {
        return imageName;
    }

    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }

    public void setOriginalFilename(String originalFilename) 
    {
        this.originalFilename = originalFilename;
    }

    public String getOriginalFilename() 
    {
        return originalFilename;
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

    public void setDisplayOrder(Integer displayOrder) 
    {
        this.displayOrder = displayOrder;
    }

    public Integer getDisplayOrder() 
    {
        return displayOrder;
    }

    public void setIsActive(String isActive) 
    {
        this.isActive = isActive;
    }

    public String getIsActive() 
    {
        return isActive;
    }

    public void setDisplayDuration(Integer displayDuration) 
    {
        this.displayDuration = displayDuration;
    }

    public Integer getDisplayDuration() 
    {
        return displayDuration;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("carouselId", getCarouselId())
            .append("imageName", getImageName())
            .append("imageUrl", getImageUrl())
            .append("originalFilename", getOriginalFilename())
            .append("imageType", getImageType())
            .append("imageSize", getImageSize())
            .append("displayOrder", getDisplayOrder())
            .append("isActive", getIsActive())
            .append("displayDuration", getDisplayDuration())
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
