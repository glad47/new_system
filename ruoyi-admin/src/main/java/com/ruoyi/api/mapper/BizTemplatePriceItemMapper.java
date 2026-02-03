package com.ruoyi.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.ruoyi.api.domain.BizTemplatePriceItem;

/**
 * Template Price Item Mapper Interface
 * 
 * @author ruoyi
 */
public interface BizTemplatePriceItemMapper
{
    /**
     * Query template price item by ID
     * 
     * @param id ID
     * @return Template price item
     */
    public BizTemplatePriceItem selectBizTemplatePriceItemById(Long id);

    /**
     * Query template price items by template price ID
     * 
     * @param templatePriceId Template price ID
     * @return Template price item list
     */
    public List<BizTemplatePriceItem> selectBizTemplatePriceItemByPriceId(Long templatePriceId);

    /**
     * Query template price items by template price ID with template item details
     * 
     * @param templatePriceId Template price ID
     * @return Template price item list with details
     */
    public List<BizTemplatePriceItem> selectBizTemplatePriceItemByPriceIdWithDetails(Long templatePriceId);

    /**
     * Query template price item list
     * 
     * @param bizTemplatePriceItem Query conditions
     * @return Template price item list
     */
    public List<BizTemplatePriceItem> selectBizTemplatePriceItemList(BizTemplatePriceItem bizTemplatePriceItem);

    /**
     * Insert template price item
     * 
     * @param bizTemplatePriceItem Template price item
     * @return Rows affected
     */
    public int insertBizTemplatePriceItem(BizTemplatePriceItem bizTemplatePriceItem);

    /**
     * Batch insert template price items
     * 
     * @param list Template price item list
     * @return Rows affected
     */
    public int batchInsertBizTemplatePriceItem(@Param("list") List<BizTemplatePriceItem> list);

    /**
     * Update template price item
     * 
     * @param bizTemplatePriceItem Template price item
     * @return Rows affected
     */
    public int updateBizTemplatePriceItem(BizTemplatePriceItem bizTemplatePriceItem);

    /**
     * Delete template price item by ID (soft delete)
     * 
     * @param id ID
     * @return Rows affected
     */
    public int deleteBizTemplatePriceItemById(Long id);

    /**
     * Delete template price items by template price ID (soft delete)
     * 
     * @param templatePriceId Template price ID
     * @return Rows affected
     */
    public int deleteBizTemplatePriceItemByPriceId(Long templatePriceId);

    /**
     * Batch delete template price items (soft delete)
     * 
     * @param ids IDs
     * @return Rows affected
     */
    public int deleteBizTemplatePriceItemByIds(Long[] ids);

    /**
     * Check if template item exists in price items
     * 
     * @param templatePriceId Template price ID
     * @param templateItemId Template item ID
     * @return Count
     */
    public int checkTemplateItemExists(@Param("templatePriceId") Long templatePriceId, 
                                       @Param("templateItemId") Long templateItemId);
}
