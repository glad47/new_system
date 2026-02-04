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
     * @param templateId template ID
     * @return template
     */
    public BizTemplate selectBizTemplateById(Long templateId);

    /**
     * Query template by ID with template items
     * 
     * @param templateId template ID
     * @return template with items
     */
    public BizTemplate selectBizTemplateByIdWithItems(Long templateId);

    /**
     * Query template list
     * 
     * @param bizTemplate template query params
     * @return template list
     */
    public List<BizTemplate> selectBizTemplateList(BizTemplate bizTemplate);

    /**
     * Query template list with template items
     * 
     * @param bizTemplate template query params
     * @return template list with items
     */
    public List<BizTemplate> selectBizTemplateListWithItems(BizTemplate bizTemplate);

    /**
     * Insert template with items
     * 
     * @param bizTemplate template with items
     * @return affected rows
     */
    public int insertBizTemplate(BizTemplate bizTemplate);

    /**
     * Update template with items
     * 
     * @param bizTemplate template with items
     * @return affected rows
     */
    public int updateBizTemplate(BizTemplate bizTemplate);

    /**
     * Delete template by ID
     * 
     * @param templateId template ID
     * @return affected rows
     */
    public int deleteBizTemplateById(Long templateId);

    /**
     * Batch delete templates
     * 
     * @param templateIds template IDs
     * @return affected rows
     */
    public int deleteBizTemplateByIds(Long[] templateIds);

    /**
     * Check if template name is unique
     * 
     * @param bizTemplate template
     * @return true if unique
     */
    public boolean checkTemplateNameUnique(BizTemplate bizTemplate);
}
