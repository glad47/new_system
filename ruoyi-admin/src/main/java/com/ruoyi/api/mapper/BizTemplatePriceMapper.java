package com.ruoyi.api.mapper;

import java.util.List;
import com.ruoyi.api.domain.BizTemplatePrice;

/**
 * Template Price Mapper Interface
 * 
 * @author ruoyi
 */
public interface BizTemplatePriceMapper
{
    /**
     * Query template price by ID
     * 
     * @param templatePriceId Template price ID
     * @return Template price
     */
    public BizTemplatePrice selectBizTemplatePriceById(Long templatePriceId);

    /**
     * Query template price by template ID
     * 
     * @param templateId Template ID
     * @return Template price
     */
    public BizTemplatePrice selectBizTemplatePriceByTemplateId(Long templateId);

    /**
     * Query template price by template ID with items
     * 
     * @param templateId Template ID
     * @return Template price with items
     */
    public BizTemplatePrice selectBizTemplatePriceByTemplateIdWithItems(Long templateId);

    /**
     * Query template price list
     * 
     * @param bizTemplatePrice Query conditions
     * @return Template price list
     */
    public List<BizTemplatePrice> selectBizTemplatePriceList(BizTemplatePrice bizTemplatePrice);

    /**
     * Insert template price
     * 
     * @param bizTemplatePrice Template price
     * @return Rows affected
     */
    public int insertBizTemplatePrice(BizTemplatePrice bizTemplatePrice);

    /**
     * Update template price
     * 
     * @param bizTemplatePrice Template price
     * @return Rows affected
     */
    public int updateBizTemplatePrice(BizTemplatePrice bizTemplatePrice);

    /**
     * Delete template price by ID (soft delete)
     * 
     * @param templatePriceId Template price ID
     * @return Rows affected
     */
    public int deleteBizTemplatePriceById(Long templatePriceId);

    /**
     * Delete template price by template ID (soft delete)
     * 
     * @param templateId Template ID
     * @return Rows affected
     */
    public int deleteBizTemplatePriceByTemplateId(Long templateId);
}
