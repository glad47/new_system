package com.ruoyi.api.mapper;

import java.util.List;
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
     * @param templateItemId template item ID
     * @return template item
     */
    public BizTemplateItem selectBizTemplateItemById(Long templateItemId);

    /**
     * Query template item by ID with price template relation
     * 
     * @param templateItemId template item ID
     * @return template item with price template
     */
    public BizTemplateItem selectBizTemplateItemByIdWithRelation(Long templateItemId);

    /**
     * Query template items by template ID
     * 
     * @param templateId template ID
     * @return template item list
     */
    public List<BizTemplateItem> selectBizTemplateItemsByTemplateId(Long templateId);

    /**
     * Query template items by template ID with price template relation
     * 
     * @param templateId template ID
     * @return template item list with price template
     */
    public List<BizTemplateItem> selectBizTemplateItemsByTemplateIdWithRelation(Long templateId);

    /**
     * Insert template item
     * 
     * @param bizTemplateItem template item
     * @return affected rows
     */
    public int insertBizTemplateItem(BizTemplateItem bizTemplateItem);

    /**
     * Batch insert template items
     * 
     * @param templateItems template item list
     * @return affected rows
     */
    public int batchInsertBizTemplateItem(List<BizTemplateItem> templateItems);

    /**
     * Update template item
     * 
     * @param bizTemplateItem template item
     * @return affected rows
     */
    public int updateBizTemplateItem(BizTemplateItem bizTemplateItem);

    /**
     * Delete template item by ID
     * 
     * @param templateItemId template item ID
     * @return affected rows
     */
    public int deleteBizTemplateItemById(Long templateItemId);

    /**
     * Delete template items by template ID
     * 
     * @param templateId template ID
     * @return affected rows
     */
    public int deleteBizTemplateItemsByTemplateId(Long templateId);

    /**
     * Batch delete template items
     * 
     * @param templateItemIds template item IDs
     * @return affected rows
     */
    public int deleteBizTemplateItemByIds(Long[] templateItemIds);
}
