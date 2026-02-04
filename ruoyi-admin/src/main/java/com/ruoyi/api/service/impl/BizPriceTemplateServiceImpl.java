package com.ruoyi.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.api.domain.BizPriceTemplate;
import com.ruoyi.api.mapper.BizPriceTemplateMapper;
import com.ruoyi.api.service.IBizPriceTemplateService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * Price Template Service Implementation
 * 
 * @author ruoyi
 */
@Service
public class BizPriceTemplateServiceImpl implements IBizPriceTemplateService
{
    @Autowired
    private BizPriceTemplateMapper bizPriceTemplateMapper;

    /**
     * Query price template by ID
     */
    @Override
    public BizPriceTemplate selectBizPriceTemplateById(Long priceTemplateId)
    {
        return bizPriceTemplateMapper.selectBizPriceTemplateById(priceTemplateId);
    }

    /**
     * Query price template list
     */
    @Override
    public List<BizPriceTemplate> selectBizPriceTemplateList(BizPriceTemplate bizPriceTemplate)
    {
        return bizPriceTemplateMapper.selectBizPriceTemplateList(bizPriceTemplate);
    }

    /**
     * Query all active price templates
     */
    @Override
    public List<BizPriceTemplate> selectAllActivePriceTemplates()
    {
        return bizPriceTemplateMapper.selectAllActivePriceTemplates();
    }

    /**
     * Insert price template
     */
    @Override
    public int insertBizPriceTemplate(BizPriceTemplate bizPriceTemplate)
    {
        // Check name uniqueness
        if (!checkPriceTemplateNameUnique(bizPriceTemplate))
        {
            throw new ServiceException("Price template name '" + bizPriceTemplate.getPriceTemplateName() + "' already exists");
        }
        
        bizPriceTemplate.setCreateBy(SecurityUtils.getUsername());
        bizPriceTemplate.setCreateTime(DateUtils.getNowDate());
        return bizPriceTemplateMapper.insertBizPriceTemplate(bizPriceTemplate);
    }

    /**
     * Update price template
     */
    @Override
    public int updateBizPriceTemplate(BizPriceTemplate bizPriceTemplate)
    {
        // Check name uniqueness
        if (!checkPriceTemplateNameUnique(bizPriceTemplate))
        {
            throw new ServiceException("Price template name '" + bizPriceTemplate.getPriceTemplateName() + "' already exists");
        }
        
        bizPriceTemplate.setUpdateBy(SecurityUtils.getUsername());
        bizPriceTemplate.setUpdateTime(DateUtils.getNowDate());
        return bizPriceTemplateMapper.updateBizPriceTemplate(bizPriceTemplate);
    }

    /**
     * Delete price template by ID
     */
    @Override
    public int deleteBizPriceTemplateById(Long priceTemplateId)
    {
        return bizPriceTemplateMapper.deleteBizPriceTemplateById(priceTemplateId);
    }

    /**
     * Batch delete price templates
     */
    @Override
    public int deleteBizPriceTemplateByIds(Long[] priceTemplateIds)
    {
        return bizPriceTemplateMapper.deleteBizPriceTemplateByIds(priceTemplateIds);
    }

    /**
     * Check if price template name is unique
     */
    @Override
    public boolean checkPriceTemplateNameUnique(BizPriceTemplate bizPriceTemplate)
    {
        Long priceTemplateId = bizPriceTemplate.getPriceTemplateId() == null ? -1L : bizPriceTemplate.getPriceTemplateId();
        BizPriceTemplate info = bizPriceTemplateMapper.checkPriceTemplateNameUnique(bizPriceTemplate.getPriceTemplateName());
        if (info != null && !info.getPriceTemplateId().equals(priceTemplateId))
        {
            return false;
        }
        return true;
    }
}
