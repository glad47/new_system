package com.ruoyi.api.mapper;

import java.util.List;
import com.ruoyi.api.domain.BizTemplate;

/**
 * Template Mapper Interface
 * 
 * @author ruoyi
 */
public interface BizTemplateMapper
{
    /**
     * Query template by ID
     * 
     * @param templateId Template ID
     * @return Template
     */
    public BizTemplate selectBizTemplateById(Long templateId);

    /**
     * Query template by ID with all relations (price, items, images)
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
     * Query template list with template price and default item info
     * 
     * @param bizTemplate Query conditions
     * @return Template list with relations
     */
    public List<BizTemplate> selectBizTemplateListWithRelations(BizTemplate bizTemplate);

    /**
     * Insert template
     * 
     * @param bizTemplate Template
     * @return Rows affected
     */
    public int insertBizTemplate(BizTemplate bizTemplate);

    /**
     * Update template
     * 
     * @param bizTemplate Template
     * @return Rows affected
     */
    public int updateBizTemplate(BizTemplate bizTemplate);

    /**
     * Delete template by ID (soft delete)
     * 
     * @param templateId Template ID
     * @return Rows affected
     */
    public int deleteBizTemplateById(Long templateId);

    /**
     * Batch delete templates (soft delete)
     * 
     * @param templateIds Template IDs
     * @return Rows affected
     */
    public int deleteBizTemplateByIds(Long[] templateIds);

    /**
     * Check if template name exists
     * 
     * @param templateName Template name
     * @return Template with same name
     */
    public BizTemplate checkTemplateNameUnique(String templateName);
}
