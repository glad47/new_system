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
import com.ruoyi.common.utils.StringUtils;

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

    /**
     * Query template by ID
     */
    @Override
    public BizTemplate selectBizTemplateById(Long templateId)
    {
        return bizTemplateMapper.selectBizTemplateById(templateId);
    }

    /**
     * Query template by ID with template items
     */
    @Override
    public BizTemplate selectBizTemplateByIdWithItems(Long templateId)
    {
        BizTemplate template = bizTemplateMapper.selectBizTemplateById(templateId);
        if (template != null)
        {
            // Load template items with price template relation
            List<BizTemplateItem> items = bizTemplateItemMapper.selectBizTemplateItemsByTemplateIdWithRelation(templateId);
            template.setTemplateItems(items);
        }
        return template;
    }

    /**
     * Query template list
     */
    @Override
    public List<BizTemplate> selectBizTemplateList(BizTemplate bizTemplate)
    {
        return bizTemplateMapper.selectBizTemplateList(bizTemplate);
    }

    /**
     * Query template list with template items
     */
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

    /**
     * Insert template with items
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBizTemplate(BizTemplate bizTemplate)
    {
        String username = SecurityUtils.getUsername();
        
        // Check template name uniqueness
        if (!checkTemplateNameUnique(bizTemplate))
        {
            throw new ServiceException("Template name '" + bizTemplate.getTemplateName() + "' already exists");
        }
        
        // Validate at least one template item with isDefault = '1'
        validateTemplateItems(bizTemplate.getTemplateItems());
        
        // 1. Insert template
        bizTemplate.setCreateBy(username);
        bizTemplate.setCreateTime(DateUtils.getNowDate());
        int rows = bizTemplateMapper.insertBizTemplate(bizTemplate);
        
        if (rows > 0 && bizTemplate.getTemplateItems() != null && !bizTemplate.getTemplateItems().isEmpty())
        {
            // 2. Insert template items
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

    /**
     * Update template with items
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBizTemplate(BizTemplate bizTemplate)
    {
        String username = SecurityUtils.getUsername();
        
        // Check template name uniqueness
        if (!checkTemplateNameUnique(bizTemplate))
        {
            throw new ServiceException("Template name '" + bizTemplate.getTemplateName() + "' already exists");
        }
        
        // Validate at least one template item with isDefault = '1'
        validateTemplateItems(bizTemplate.getTemplateItems());
        
        // 1. Update template
        bizTemplate.setUpdateBy(username);
        bizTemplate.setUpdateTime(DateUtils.getNowDate());
        int rows = bizTemplateMapper.updateBizTemplate(bizTemplate);
        
        if (rows > 0)
        {
            // 2. Delete existing template items
            bizTemplateItemMapper.deleteBizTemplateItemsByTemplateId(bizTemplate.getTemplateId());
            
            // 3. Insert new template items
            if (bizTemplate.getTemplateItems() != null && !bizTemplate.getTemplateItems().isEmpty())
            {
                for (int i = 0; i < bizTemplate.getTemplateItems().size(); i++)
                {
                    BizTemplateItem item = bizTemplate.getTemplateItems().get(i);
                    item.setTemplateItemId(null); // New insert
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

    /**
     * Delete template by ID (cascade deletes items)
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBizTemplateById(Long templateId)
    {
        // Delete template items first
        bizTemplateItemMapper.deleteBizTemplateItemsByTemplateId(templateId);
        
        // Delete template
        return bizTemplateMapper.deleteBizTemplateById(templateId);
    }

    /**
     * Batch delete templates
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBizTemplateByIds(Long[] templateIds)
    {
        // Delete template items for each template
        for (Long templateId : templateIds)
        {
            bizTemplateItemMapper.deleteBizTemplateItemsByTemplateId(templateId);
        }
        
        // Delete templates
        return bizTemplateMapper.deleteBizTemplateByIds(templateIds);
    }

    /**
     * Check if template name is unique
     */
    @Override
    public boolean checkTemplateNameUnique(BizTemplate bizTemplate)
    {
        Long templateId = bizTemplate.getTemplateId() == null ? -1L : bizTemplate.getTemplateId();
        BizTemplate info = bizTemplateMapper.checkTemplateNameUnique(bizTemplate.getTemplateName());
        if (info != null && !info.getTemplateId().equals(templateId))
        {
            return false;
        }
        return true;
    }

    /**
     * Validate template items
     */
    private void validateTemplateItems(List<BizTemplateItem> items)
    {
        if (items == null || items.isEmpty())
        {
            throw new ServiceException("Template must have at least one item");
        }
        
        // Check that exactly one item is marked as default
        long defaultCount = items.stream()
            .filter(item -> "1".equals(item.getIsDefault()))
            .count();
        
        if (defaultCount == 0)
        {
            throw new ServiceException("Template must have one default item");
        }
        
        if (defaultCount > 1)
        {
            throw new ServiceException("Template can only have one default item");
        }
    }
}
