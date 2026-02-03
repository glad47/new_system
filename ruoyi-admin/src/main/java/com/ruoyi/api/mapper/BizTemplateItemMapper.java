package com.ruoyi.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.api.domain.BizTemplateItem;

/**
 * Template Item Mapper Interface
 * 
 * @author ruoyi
 */
public interface BizTemplateItemMapper
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
     * Query template item list with image count
     * 
     * @param bizTemplateItem Query conditions
     * @return Template item list with image count
     */
    public List<BizTemplateItem> selectBizTemplateItemListWithImageCount(BizTemplateItem bizTemplateItem);

    /**
     * Query all active template items
     * 
     * @return All active template items
     */
    public List<BizTemplateItem> selectAllActiveTemplateItems();

    /**
     * Query template items by IDs
     * 
     * @param templateItemIds Template item IDs
     * @return Template items
     */
    public List<BizTemplateItem> selectBizTemplateItemByIds(@Param("templateItemIds") List<Long> templateItemIds);

    /**
     * Insert template item
     * 
     * @param bizTemplateItem Template item
     * @return Rows affected
     */
    public int insertBizTemplateItem(BizTemplateItem bizTemplateItem);

    /**
     * Update template item
     * 
     * @param bizTemplateItem Template item
     * @return Rows affected
     */
    public int updateBizTemplateItem(BizTemplateItem bizTemplateItem);

    /**
     * Delete template item by ID (soft delete)
     * 
     * @param templateItemId Template item ID
     * @return Rows affected
     */
    public int deleteBizTemplateItemById(Long templateItemId);

    /**
     * Batch delete template items (soft delete)
     * 
     * @param templateItemIds Template item IDs
     * @return Rows affected
     */
    public int deleteBizTemplateItemByIds(Long[] templateItemIds);

    /**
     * Check if template item is used in any template
     * 
     * @param templateItemId Template item ID
     * @return Count of usage
     */
    public int checkTemplateItemInUse(Long templateItemId);
}
