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
     */
    public BizPriceTemplate selectBizPriceTemplateById(Long priceTemplateId);

    /**
     * Query price template list
     */
    public List<BizPriceTemplate> selectBizPriceTemplateList(BizPriceTemplate bizPriceTemplate);

    /**
     * Query all active price templates (for dropdown)
     */
    public List<BizPriceTemplate> selectAllActivePriceTemplates();

    /**
     * Query the current default price template
     */
    public BizPriceTemplate selectDefaultPriceTemplate();

    /**
     * Clear is_default = '0' on all rows (step 1 of set-default)
     */
    public int clearAllDefaults();

    /**
     * Set is_default = '1' on the given row (step 2 of set-default)
     */
    public int setDefaultById(Long priceTemplateId);

    /**
     * Insert price template
     */
    public int insertBizPriceTemplate(BizPriceTemplate bizPriceTemplate);

    /**
     * Update price template
     */
    public int updateBizPriceTemplate(BizPriceTemplate bizPriceTemplate);

    /**
     * Delete price template by ID (soft delete)
     */
    public int deleteBizPriceTemplateById(Long priceTemplateId);

    /**
     * Batch delete price templates (soft delete)
     */
    public int deleteBizPriceTemplateByIds(Long[] priceTemplateIds);

    /**
     * Check if price template name is unique
     */
    public BizPriceTemplate checkPriceTemplateNameUnique(String priceTemplateName);
}
