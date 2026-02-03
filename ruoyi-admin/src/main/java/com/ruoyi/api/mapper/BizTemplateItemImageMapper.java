package com.ruoyi.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.api.domain.BizTemplateItemImage;

/**
 * Template Item Image Mapper Interface
 * 
 * @author ruoyi
 */
public interface BizTemplateItemImageMapper
{
    /**
     * Query image by ID
     * 
     * @param imageId Image ID
     * @return Image
     */
    public BizTemplateItemImage selectBizTemplateItemImageById(Long imageId);

    /**
     * Query images by template item ID
     * 
     * @param templateItemId Template item ID
     * @return Image list
     */
    public List<BizTemplateItemImage> selectBizTemplateItemImageByItemId(Long templateItemId);

    /**
     * Query image list
     * 
     * @param bizTemplateItemImage Query conditions
     * @return Image list
     */
    public List<BizTemplateItemImage> selectBizTemplateItemImageList(BizTemplateItemImage bizTemplateItemImage);

    /**
     * Insert image
     * 
     * @param bizTemplateItemImage Image
     * @return Rows affected
     */
    public int insertBizTemplateItemImage(BizTemplateItemImage bizTemplateItemImage);

    /**
     * Batch insert images
     * 
     * @param images Image list
     * @return Rows affected
     */
    public int batchInsertBizTemplateItemImage(@Param("list") List<BizTemplateItemImage> images);

    /**
     * Update image
     * 
     * @param bizTemplateItemImage Image
     * @return Rows affected
     */
    public int updateBizTemplateItemImage(BizTemplateItemImage bizTemplateItemImage);

    /**
     * Delete image by ID (soft delete)
     * 
     * @param imageId Image ID
     * @return Rows affected
     */
    public int deleteBizTemplateItemImageById(Long imageId);

    /**
     * Batch delete images (soft delete)
     * 
     * @param imageIds Image IDs
     * @return Rows affected
     */
    public int deleteBizTemplateItemImageByIds(Long[] imageIds);

    /**
     * Delete images by template item ID (soft delete)
     * 
     * @param templateItemId Template item ID
     * @return Rows affected
     */
    public int deleteBizTemplateItemImageByItemId(Long templateItemId);

    /**
     * Get max sort order for a template item
     * 
     * @param templateItemId Template item ID
     * @return Max sort order
     */
    public Integer getMaxSortOrderByItemId(Long templateItemId);
}
