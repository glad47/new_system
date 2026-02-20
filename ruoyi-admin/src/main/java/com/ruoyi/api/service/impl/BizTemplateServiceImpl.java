package com.ruoyi.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.api.domain.BizTemplate;
import com.ruoyi.api.domain.BizTemplateItem;
import com.ruoyi.api.mapper.BizTemplateItemMapper;
import com.ruoyi.api.mapper.BizTemplateMapper;
import com.ruoyi.api.service.IBizTemplateService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;

/**
 * Template Service Implementation
 *
 * @author ruoyi
 */
@Service
public class BizTemplateServiceImpl implements IBizTemplateService
{
    @Autowired
    private BizTemplateMapper bizTemplateMapper;

    @Autowired
    private BizTemplateItemMapper bizTemplateItemMapper;

    @Override
    public BizTemplate selectBizTemplateById(Long templateId)
    {
        return bizTemplateMapper.selectBizTemplateById(templateId);
    }

    @Override
    public BizTemplate selectBizTemplateByIdWithItems(Long templateId)
    {
        BizTemplate template = bizTemplateMapper.selectBizTemplateById(templateId);
        if (template != null)
        {
            List<BizTemplateItem> items = bizTemplateItemMapper.selectBizTemplateItemsByTemplateIdWithRelation(templateId);
            template.setTemplateItems(items);
        }
        return template;
    }

    @Override
    public List<BizTemplate> selectBizTemplateList(BizTemplate bizTemplate)
    {
        return bizTemplateMapper.selectBizTemplateList(bizTemplate);
    }

    @Override
    public List<BizTemplate> selectBizTemplateListWithItems(BizTemplate bizTemplate)
    {
        List<BizTemplate> templates = bizTemplateMapper.selectBizTemplateList(bizTemplate);
        for (BizTemplate template : templates)
        {
            List<BizTemplateItem> items = bizTemplateItemMapper.selectBizTemplateItemsByTemplateIdWithRelation(template.getTemplateId());
            template.setTemplateItems(items);
        }
        return templates;
    }

    @Override
    public BizTemplate selectDefaultTemplate()
    {
        return bizTemplateMapper.selectDefaultTemplate();
    }

    /**
     * Set template as system default — atomic two-step inside one transaction.
     * Step 1: clear all existing defaults.
     * Step 2: mark the chosen template.
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setDefaultTemplate(Long templateId)
    {
        BizTemplate target = bizTemplateMapper.selectBizTemplateById(templateId);
        if (target == null)
        {
            throw new ServiceException("Template not found: " + templateId);
        }
        bizTemplateMapper.clearAllTemplateDefaults();
        int rows = bizTemplateMapper.setTemplateDefaultById(templateId);
        if (rows == 0)
        {
            throw new ServiceException("Failed to set default template: " + templateId);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBizTemplate(BizTemplate bizTemplate)
    {
        String username = SecurityUtils.getUsername();

        if (!checkTemplateNameUnique(bizTemplate))
        {
            throw new ServiceException("Template name '" + bizTemplate.getTemplateName() + "' already exists");
        }

        validateTemplateItems(bizTemplate.getTemplateItems());

        // Default is_default to '0' if not provided
        if (bizTemplate.getIsDefault() == null)
        {
            bizTemplate.setIsDefault("0");
        }

        bizTemplate.setCreateBy(username);
        bizTemplate.setCreateTime(DateUtils.getNowDate());
        int rows = bizTemplateMapper.insertBizTemplate(bizTemplate);

        if (rows > 0 && bizTemplate.getTemplateItems() != null && !bizTemplate.getTemplateItems().isEmpty())
        {
            for (int i = 0; i < bizTemplate.getTemplateItems().size(); i++)
            {
                BizTemplateItem item = bizTemplate.getTemplateItems().get(i);
                item.setTemplateId(bizTemplate.getTemplateId());
                item.setSortOrder(i);
                item.setCreateBy(username);
                item.setCreateTime(DateUtils.getNowDate());
            }
            bizTemplateItemMapper.batchInsertBizTemplateItem(bizTemplate.getTemplateItems());
        }

        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBizTemplate(BizTemplate bizTemplate)
    {
        String username = SecurityUtils.getUsername();

        if (!checkTemplateNameUnique(bizTemplate))
        {
            throw new ServiceException("Template name '" + bizTemplate.getTemplateName() + "' already exists");
        }

        validateTemplateItems(bizTemplate.getTemplateItems());

        bizTemplate.setUpdateBy(username);
        bizTemplate.setUpdateTime(DateUtils.getNowDate());
        int rows = bizTemplateMapper.updateBizTemplate(bizTemplate);

        if (rows > 0)
        {
            bizTemplateItemMapper.deleteBizTemplateItemsByTemplateId(bizTemplate.getTemplateId());

            if (bizTemplate.getTemplateItems() != null && !bizTemplate.getTemplateItems().isEmpty())
            {
                for (int i = 0; i < bizTemplate.getTemplateItems().size(); i++)
                {
                    BizTemplateItem item = bizTemplate.getTemplateItems().get(i);
                    item.setTemplateItemId(null);
                    item.setTemplateId(bizTemplate.getTemplateId());
                    item.setSortOrder(i);
                    item.setCreateBy(username);
                    item.setCreateTime(DateUtils.getNowDate());
                }
                bizTemplateItemMapper.batchInsertBizTemplateItem(bizTemplate.getTemplateItems());
            }
        }

        return rows;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBizTemplateById(Long templateId)
    {
        bizTemplateItemMapper.deleteBizTemplateItemsByTemplateId(templateId);
        return bizTemplateMapper.deleteBizTemplateById(templateId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBizTemplateByIds(Long[] templateIds)
    {
        for (Long templateId : templateIds)
        {
            bizTemplateItemMapper.deleteBizTemplateItemsByTemplateId(templateId);
        }
        return bizTemplateMapper.deleteBizTemplateByIds(templateIds);
    }

    @Override
    public boolean checkTemplateNameUnique(BizTemplate bizTemplate)
    {
        Long templateId = bizTemplate.getTemplateId() == null ? -1L : bizTemplate.getTemplateId();
        BizTemplate info = bizTemplateMapper.checkTemplateNameUnique(bizTemplate.getTemplateName());
        return info == null || info.getTemplateId().equals(templateId);
    }

    private void validateTemplateItems(List<BizTemplateItem> items)
    {
        if (items == null || items.isEmpty())
        {
            throw new ServiceException("Template must have at least one item");
        }
        long defaultCount = items.stream().filter(i -> "1".equals(i.getIsDefault())).count();
        if (defaultCount == 0) throw new ServiceException("Template must have one default item");
        if (defaultCount > 1)  throw new ServiceException("Template can only have one default item");
    }
}