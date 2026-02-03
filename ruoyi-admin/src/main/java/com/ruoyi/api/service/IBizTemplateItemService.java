package com.ruoyi.api.service;

import java.util.List;
import com.ruoyi.api.domain.BizTemplateItem;
import com.ruoyi.api.domain.BizTemplateItemImage;

/**
 * Template Item Service Interface
 * 
 * @author ruoyi
 */
public interface IBizTemplateItemService
{
    /**
     * Query template item by ID
     * 
     * @param templateItemId Template item ID
     * @return Template item
     */
    public BizTemplateItem selectBizTemplateItemById(Long templateItemId);

    /**
     * Query template item by ID with images
     * 
     * @param templateItemId Template item ID
     * @return Template item with images
     */
    public BizTemplateItem selectBizTemplateItemByIdWithImages(Long templateItemId);

    /**
     * Query template item list
     * 
     * @param bizTemplateItem Query conditions
     * @return Template item list
     */
    public List<BizTemplateItem> selectBizTemplateItemList(BizTemplateItem bizTemplateItem);

    /**
     * Query all active template items
     * 
     * @return All active template items
     */
    public List<BizTemplateItem> selectAllActiveTemplateItems();

    /**
     * Insert template item
     * 
     * @param bizTemplateItem Template item
     * @return Rows affected
     */
    public int insertBizTemplateItem(BizTemplateItem bizTemplateItem);

    /**
     * Insert template item with images
     * 
     * @param bizTemplateItem Template item with images
     * @return Rows affected
     */
    public int insertBizTemplateItemWithImages(BizTemplateItem bizTemplateItem);

    /**
     * Update template item
     * 
     * @param bizTemplateItem Template item
     * @return Rows affected
     */
    public int updateBizTemplateItem(BizTemplateItem bizTemplateItem);

    /**
     * Update template item with images
     * 
     * @param bizTemplateItem Template item with images
     * @return Rows affected
     */
    public int updateBizTemplateItemWithImages(BizTemplateItem bizTemplateItem);

    /**
     * Delete template item by ID
     * 
     * @param templateItemId Template item ID
     * @return Rows affected
     */
    public int deleteBizTemplateItemById(Long templateItemId);

    /**
     * Batch delete template items
     * 
     * @param templateItemIds Template item IDs
     * @return Rows affected
     */
    public int deleteBizTemplateItemByIds(Long[] templateItemIds);

    /**
     * Add image to template item
     * 
     * @param image Image
     * @return Rows affected
     */
    public int addImageToTemplateItem(BizTemplateItemImage image);

    /**
     * Remove image from template item
     * 
     * @param imageId Image ID
     * @return Rows affected
     */
    public int removeImageFromTemplateItem(Long imageId);

    /**
     * Check if template item is in use
     * 
     * @param templateItemId Template item ID
     * @return true if in use
     */
    public boolean isTemplateItemInUse(Long templateItemId);
}
