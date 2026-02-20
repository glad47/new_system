package com.ruoyi.api.service;

import java.util.List;
import com.ruoyi.api.domain.BizPriceTemplate;

/**
 * Price Template Service Interface
 *
 * @author ruoyi
 */
public interface IBizPriceTemplateService
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
     * Get the current default price template
     */
    public BizPriceTemplate selectDefaultPriceTemplate();

    /**
     * Set a price template as the system default.
     * Clears any existing default then marks the given ID as default — atomically in one transaction.
     *
     * @param priceTemplateId the ID to make default
     */
    public void setDefaultPriceTemplate(Long priceTemplateId);

    /**
     * Insert price template
     */
    public int insertBizPriceTemplate(BizPriceTemplate bizPriceTemplate);

    /**
     * Update price template
     */
    public int updateBizPriceTemplate(BizPriceTemplate bizPriceTemplate);

    /**
     * Delete price template by ID
     */
    public int deleteBizPriceTemplateById(Long priceTemplateId);

    /**
     * Batch delete price templates
     */
    public int deleteBizPriceTemplateByIds(Long[] priceTemplateIds);

    /**
     * Check if price template name is unique
     */
    public boolean checkPriceTemplateNameUnique(BizPriceTemplate bizPriceTemplate);
}
