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
     * Insert template
     * 
     * @param bizTemplate template
     * @return affected rows
     */
    public int insertBizTemplate(BizTemplate bizTemplate);

    /**
     * Update template
     * 
     * @param bizTemplate template
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
     * @param templateName template name
     * @return template info
     */
    public BizTemplate checkTemplateNameUnique(String templateName);
}
