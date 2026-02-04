package com.ruoyi.api.mapper;

import java.util.List;
import com.ruoyi.api.domain.BizPriceTemplate;

/**
 * Price Template Mapper Interface
 * 
 * @author ruoyi
 */
public interface BizPriceTemplateMapper
{
    /**
     * Query price template by ID
     * 
     * @param priceTemplateId price template ID
     * @return price template
     */
    public BizPriceTemplate selectBizPriceTemplateById(Long priceTemplateId);

    /**
     * Query price template list
     * 
     * @param bizPriceTemplate price template query params
     * @return price template list
     */
    public List<BizPriceTemplate> selectBizPriceTemplateList(BizPriceTemplate bizPriceTemplate);

    /**
     * Query all active price templates (for dropdown)
     * 
     * @return active price template list
     */
    public List<BizPriceTemplate> selectAllActivePriceTemplates();

    /**
     * Insert price template
     * 
     * @param bizPriceTemplate price template
     * @return affected rows
     */
    public int insertBizPriceTemplate(BizPriceTemplate bizPriceTemplate);

    /**
     * Update price template
     * 
     * @param bizPriceTemplate price template
     * @return affected rows
     */
    public int updateBizPriceTemplate(BizPriceTemplate bizPriceTemplate);

    /**
     * Delete price template by ID
     * 
     * @param priceTemplateId price template ID
     * @return affected rows
     */
    public int deleteBizPriceTemplateById(Long priceTemplateId);

    /**
     * Batch delete price templates
     * 
     * @param priceTemplateIds price template IDs
     * @return affected rows
     */
    public int deleteBizPriceTemplateByIds(Long[] priceTemplateIds);

    /**
     * Check if price template name is unique
     * 
     * @param priceTemplateName price template name
     * @return price template info
     */
    public BizPriceTemplate checkPriceTemplateNameUnique(String priceTemplateName);
}
