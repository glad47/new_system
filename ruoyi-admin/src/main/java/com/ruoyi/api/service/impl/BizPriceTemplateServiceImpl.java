package com.ruoyi.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    /** Query price template by ID */
    @Override
    public BizPriceTemplate selectBizPriceTemplateById(Long priceTemplateId)
    {
        return bizPriceTemplateMapper.selectBizPriceTemplateById(priceTemplateId);
    }

    /** Query price template list */
    @Override
    public List<BizPriceTemplate> selectBizPriceTemplateList(BizPriceTemplate bizPriceTemplate)
    {
        return bizPriceTemplateMapper.selectBizPriceTemplateList(bizPriceTemplate);
    }

    /** Query all active price templates */
    @Override
    public List<BizPriceTemplate> selectAllActivePriceTemplates()
    {
        return bizPriceTemplateMapper.selectAllActivePriceTemplates();
    }

    /** Get the current default price template */
    @Override
    public BizPriceTemplate selectDefaultPriceTemplate()
    {
        return bizPriceTemplateMapper.selectDefaultPriceTemplate();
    }

    /**
     * Set a price template as the system default.
     * Step 1: clear is_default on all rows.
     * Step 2: set is_default = '1' on the target row.
     * Both steps run in a single transaction — if step 2 fails, step 1 is rolled back.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefaultPriceTemplate(Long priceTemplateId)
    {
        // Verify the target template actually exists and is not deleted
        BizPriceTemplate target = bizPriceTemplateMapper.selectBizPriceTemplateById(priceTemplateId);
        if (target == null)
        {
            throw new ServiceException("Price template not found: " + priceTemplateId);
        }
        // Step 1 — clear any existing default
        bizPriceTemplateMapper.clearAllDefaults();
        // Step 2 — mark the chosen template
        int rows = bizPriceTemplateMapper.setDefaultById(priceTemplateId);
        if (rows == 0)
        {
            throw new ServiceException("Failed to set default price template: " + priceTemplateId);
        }
    }

    /** Insert price template */
    @Override
    public int insertBizPriceTemplate(BizPriceTemplate bizPriceTemplate)
    {
        if (!checkPriceTemplateNameUnique(bizPriceTemplate))
        {
            throw new ServiceException("Price template name '" + bizPriceTemplate.getPriceTemplateName() + "' already exists");
        }
        // Default is_default to '0' if not set
        if (bizPriceTemplate.getIsDefault() == null)
        {
            bizPriceTemplate.setIsDefault("0");
        }
        bizPriceTemplate.setCreateBy(SecurityUtils.getUsername());
        bizPriceTemplate.setCreateTime(DateUtils.getNowDate());
        return bizPriceTemplateMapper.insertBizPriceTemplate(bizPriceTemplate);
    }

    /** Update price template */
    @Override
    public int updateBizPriceTemplate(BizPriceTemplate bizPriceTemplate)
    {
        if (!checkPriceTemplateNameUnique(bizPriceTemplate))
        {
            throw new ServiceException("Price template name '" + bizPriceTemplate.getPriceTemplateName() + "' already exists");
        }
        bizPriceTemplate.setUpdateBy(SecurityUtils.getUsername());
        bizPriceTemplate.setUpdateTime(DateUtils.getNowDate());
        return bizPriceTemplateMapper.updateBizPriceTemplate(bizPriceTemplate);
    }

    /** Delete price template by ID */
    @Override
    public int deleteBizPriceTemplateById(Long priceTemplateId)
    {
        return bizPriceTemplateMapper.deleteBizPriceTemplateById(priceTemplateId);
    }

    /** Batch delete price templates */
    @Override
    public int deleteBizPriceTemplateByIds(Long[] priceTemplateIds)
    {
        return bizPriceTemplateMapper.deleteBizPriceTemplateByIds(priceTemplateIds);
    }

    /** Check if price template name is unique */
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
