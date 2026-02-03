package com.ruoyi.api.service;

import java.util.List;
import com.ruoyi.api.domain.BizTemplate;

/**
 * Template Service Interface
 * 
 * @author ruoyi
 */
public interface IBizTemplateService
{
    /**
     * Query template by ID
     * 
     * @param templateId Template ID
     * @return Template
     */
    public BizTemplate selectBizTemplateById(Long templateId);

    /**
     * Query template by ID with all relations
     * 
     * @param templateId Template ID
     * @return Template with relations
     */
    public BizTemplate selectBizTemplateByIdWithRelations(Long templateId);

    /**
     * Query template list
     * 
     * @param bizTemplate Query conditions
     * @return Template list
     */
    public List<BizTemplate> selectBizTemplateList(BizTemplate bizTemplate);

    /**
     * Query template list with relations
     * 
     * @param bizTemplate Query conditions
     * @return Template list with relations
     */
    public List<BizTemplate> selectBizTemplateListWithRelations(BizTemplate bizTemplate);

    /**
     * Insert template with template price and all template items
     * This creates:
     * 1. biz_template record
     * 2. biz_template_price record (with default_item_id)
     * 3. biz_template_price_item records (for ALL active template items)
     * 
     * @param bizTemplate Template
     * @return Rows affected
     */
    public int insertBizTemplate(BizTemplate bizTemplate);

    /**
     * Update template
     * This updates:
     * 1. biz_template record
     * 2. biz_template_price record (default_item_id can be changed)
     * 3. Optionally sync template_price_items
     * 
     * @param bizTemplate Template
     * @return Rows affected
     */
    public int updateBizTemplate(BizTemplate bizTemplate);

    /**
     * Delete template by ID
     * This deletes:
     * 1. biz_template record
     * 2. biz_template_price record (cascade)
     * 3. biz_template_price_item records (cascade)
     * 
     * @param templateId Template ID
     * @return Rows affected
     */
    public int deleteBizTemplateById(Long templateId);

    /**
     * Batch delete templates
     * 
     * @param templateIds Template IDs
     * @return Rows affected
     */
    public int deleteBizTemplateByIds(Long[] templateIds);

    /**
     * Check if template name is unique
     * 
     * @param bizTemplate Template
     * @return true if unique
     */
    public boolean checkTemplateNameUnique(BizTemplate bizTemplate);

    /**
     * Sync template price items with all active template items
     * Called when new template items are added
     * 
     * @param templateId Template ID
     * @return Rows affected
     */
    public int syncTemplatePriceItems(Long templateId);

    /**
     * Sync all templates with new template item
     * Called when a new template item is created
     * 
     * @param templateItemId New template item ID
     * @return Rows affected
     */
    public int syncAllTemplatesWithNewItem(Long templateItemId);
}
