package com.ruoyi.api.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.api.domain.BizTemplate;
import com.ruoyi.api.domain.BizTemplateItem;
import com.ruoyi.api.domain.BizTemplatePrice;
import com.ruoyi.api.domain.BizTemplatePriceItem;
import com.ruoyi.api.mapper.BizTemplateItemMapper;
import com.ruoyi.api.mapper.BizTemplateMapper;
import com.ruoyi.api.mapper.BizTemplatePriceItemMapper;
import com.ruoyi.api.mapper.BizTemplatePriceMapper;
import com.ruoyi.api.service.IBizTemplateService;
import com.ruoyi.common.constant.UserConstants;
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
    private BizTemplatePriceMapper bizTemplatePriceMapper;

    @Autowired
    private BizTemplatePriceItemMapper bizTemplatePriceItemMapper;

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
     * Query template by ID with all relations
     */
    @Override
    public BizTemplate selectBizTemplateByIdWithRelations(Long templateId)
    {
        BizTemplate template = bizTemplateMapper.selectBizTemplateByIdWithRelations(templateId);
        if (template != null && template.getTemplatePrice() != null)
        {
            // Load template items with images
            List<BizTemplatePriceItem> priceItems = bizTemplatePriceItemMapper
                .selectBizTemplatePriceItemByPriceIdWithDetails(template.getTemplatePrice().getTemplatePriceId());
            
            List<BizTemplateItem> items = new ArrayList<>();
            for (BizTemplatePriceItem priceItem : priceItems)
            {
                if (priceItem.getTemplateItem() != null)
                {
                    items.add(priceItem.getTemplateItem());
                }
            }
            template.setTemplateItems(items);
            
            // Set convenience fields
            template.setDefaultItemId(template.getTemplatePrice().getDefaultItemId());
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
     * Query template list with relations
     */
    @Override
    public List<BizTemplate> selectBizTemplateListWithRelations(BizTemplate bizTemplate)
    {
        return bizTemplateMapper.selectBizTemplateListWithRelations(bizTemplate);
    }

    /**
     * Insert template with template price and all active template items
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBizTemplate(BizTemplate bizTemplate)
    {
        String username = SecurityUtils.getUsername();
        
        // Validate default item ID
        if (bizTemplate.getDefaultItemId() == null)
        {
            throw new ServiceException("Default template item must be selected");
        }
        
        // Check template name uniqueness
        if (!checkTemplateNameUnique(bizTemplate))
        {
            throw new ServiceException("Template name '" + bizTemplate.getTemplateName() + "' already exists");
        }
        
        // 1. Insert template
        bizTemplate.setCreateBy(username);
        bizTemplate.setCreateTime(DateUtils.getNowDate());
        int rows = bizTemplateMapper.insertBizTemplate(bizTemplate);
        
        if (rows > 0)
        {
            // 2. Insert template price (1:1)
            BizTemplatePrice templatePrice = new BizTemplatePrice();
            templatePrice.setTemplateId(bizTemplate.getTemplateId());
            templatePrice.setDefaultItemId(bizTemplate.getDefaultItemId());
            templatePrice.setCreateBy(username);
            templatePrice.setCreateTime(DateUtils.getNowDate());
            bizTemplatePriceMapper.insertBizTemplatePrice(templatePrice);
            
            // 3. Insert template price items for ALL active template items
            List<BizTemplateItem> allActiveItems = bizTemplateItemMapper.selectAllActiveTemplateItems();
            if (allActiveItems != null && !allActiveItems.isEmpty())
            {
                List<BizTemplatePriceItem> priceItems = new ArrayList<>();
                int sortOrder = 0;
                for (BizTemplateItem item : allActiveItems)
                {
                    BizTemplatePriceItem priceItem = new BizTemplatePriceItem();
                    priceItem.setTemplatePriceId(templatePrice.getTemplatePriceId());
                    priceItem.setTemplateItemId(item.getTemplateItemId());
                    priceItem.setSortOrder(sortOrder++);
                    priceItem.setCreateBy(username);
                    priceItem.setCreateTime(DateUtils.getNowDate());
                    priceItems.add(priceItem);
                }
                if (!priceItems.isEmpty())
                {
                    bizTemplatePriceItemMapper.batchInsertBizTemplatePriceItem(priceItems);
                }
            }
        }
        
        return rows;
    }

    /**
     * Update template
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBizTemplate(BizTemplate bizTemplate)
    {
        String username = SecurityUtils.getUsername();
        
        // Validate default item ID
        if (bizTemplate.getDefaultItemId() == null)
        {
            throw new ServiceException("Default template item must be selected");
        }
        
        // Check template name uniqueness
        if (!checkTemplateNameUnique(bizTemplate))
        {
            throw new ServiceException("Template name '" + bizTemplate.getTemplateName() + "' already exists");
        }
        
        // 1. Update template
        bizTemplate.setUpdateBy(username);
        bizTemplate.setUpdateTime(DateUtils.getNowDate());
        int rows = bizTemplateMapper.updateBizTemplate(bizTemplate);
        
        if (rows > 0)
        {
            // 2. Update template price (change default item if needed)
            BizTemplatePrice templatePrice = bizTemplatePriceMapper
                .selectBizTemplatePriceByTemplateId(bizTemplate.getTemplateId());
            
            if (templatePrice != null)
            {
                templatePrice.setDefaultItemId(bizTemplate.getDefaultItemId());
                templatePrice.setUpdateBy(username);
                templatePrice.setUpdateTime(DateUtils.getNowDate());
                bizTemplatePriceMapper.updateBizTemplatePrice(templatePrice);
            }
            else
            {
                // Create template price if not exists (edge case)
                templatePrice = new BizTemplatePrice();
                templatePrice.setTemplateId(bizTemplate.getTemplateId());
                templatePrice.setDefaultItemId(bizTemplate.getDefaultItemId());
                templatePrice.setCreateBy(username);
                templatePrice.setCreateTime(DateUtils.getNowDate());
                bizTemplatePriceMapper.insertBizTemplatePrice(templatePrice);
                
                // Also create price items
                syncTemplatePriceItems(bizTemplate.getTemplateId());
            }
        }
        
        return rows;
    }

    /**
     * Delete template by ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBizTemplateById(Long templateId)
    {
        // Get template price first
        BizTemplatePrice templatePrice = bizTemplatePriceMapper.selectBizTemplatePriceByTemplateId(templateId);
        
        if (templatePrice != null)
        {
            // Delete price items first
            bizTemplatePriceItemMapper.deleteBizTemplatePriceItemByPriceId(templatePrice.getTemplatePriceId());
            
            // Delete template price
            bizTemplatePriceMapper.deleteBizTemplatePriceByTemplateId(templateId);
        }
        
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
        for (Long templateId : templateIds)
        {
            BizTemplatePrice templatePrice = bizTemplatePriceMapper.selectBizTemplatePriceByTemplateId(templateId);
            if (templatePrice != null)
            {
                bizTemplatePriceItemMapper.deleteBizTemplatePriceItemByPriceId(templatePrice.getTemplatePriceId());
                bizTemplatePriceMapper.deleteBizTemplatePriceByTemplateId(templateId);
            }
        }
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
     * Sync template price items with all active template items
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncTemplatePriceItems(Long templateId)
    {
        String username = SecurityUtils.getUsername();
        
        BizTemplatePrice templatePrice = bizTemplatePriceMapper.selectBizTemplatePriceByTemplateId(templateId);
        if (templatePrice == null)
        {
            return 0;
        }
        
        // Get existing price items
        List<BizTemplatePriceItem> existingItems = bizTemplatePriceItemMapper
            .selectBizTemplatePriceItemByPriceId(templatePrice.getTemplatePriceId());
        
        // Get all active template items
        List<BizTemplateItem> allActiveItems = bizTemplateItemMapper.selectAllActiveTemplateItems();
        
        // Find items that need to be added
        List<BizTemplatePriceItem> itemsToAdd = new ArrayList<>();
        int maxSortOrder = 0;
        for (BizTemplatePriceItem existing : existingItems)
        {
            if (existing.getSortOrder() != null && existing.getSortOrder() > maxSortOrder)
            {
                maxSortOrder = existing.getSortOrder();
            }
        }
        
        for (BizTemplateItem item : allActiveItems)
        {
            boolean found = false;
            for (BizTemplatePriceItem existing : existingItems)
            {
                if (existing.getTemplateItemId().equals(item.getTemplateItemId()))
                {
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                BizTemplatePriceItem priceItem = new BizTemplatePriceItem();
                priceItem.setTemplatePriceId(templatePrice.getTemplatePriceId());
                priceItem.setTemplateItemId(item.getTemplateItemId());
                priceItem.setSortOrder(++maxSortOrder);
                priceItem.setCreateBy(username);
                priceItem.setCreateTime(DateUtils.getNowDate());
                itemsToAdd.add(priceItem);
            }
        }
        
        if (!itemsToAdd.isEmpty())
        {
            return bizTemplatePriceItemMapper.batchInsertBizTemplatePriceItem(itemsToAdd);
        }
        
        return 0;
    }

    /**
     * Sync all templates with new template item
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int syncAllTemplatesWithNewItem(Long templateItemId)
    {
        String username = SecurityUtils.getUsername();
        
        // Get all templates
        BizTemplate query = new BizTemplate();
        List<BizTemplate> templates = bizTemplateMapper.selectBizTemplateList(query);
        
        int totalRows = 0;
        for (BizTemplate template : templates)
        {
            BizTemplatePrice templatePrice = bizTemplatePriceMapper
                .selectBizTemplatePriceByTemplateId(template.getTemplateId());
            
            if (templatePrice != null)
            {
                // Check if item already exists
                int exists = bizTemplatePriceItemMapper.checkTemplateItemExists(
                    templatePrice.getTemplatePriceId(), templateItemId);
                
                if (exists == 0)
                {
                    // Get max sort order
                    List<BizTemplatePriceItem> existingItems = bizTemplatePriceItemMapper
                        .selectBizTemplatePriceItemByPriceId(templatePrice.getTemplatePriceId());
                    int maxSortOrder = 0;
                    for (BizTemplatePriceItem item : existingItems)
                    {
                        if (item.getSortOrder() != null && item.getSortOrder() > maxSortOrder)
                        {
                            maxSortOrder = item.getSortOrder();
                        }
                    }
                    
                    // Add new price item
                    BizTemplatePriceItem priceItem = new BizTemplatePriceItem();
                    priceItem.setTemplatePriceId(templatePrice.getTemplatePriceId());
                    priceItem.setTemplateItemId(templateItemId);
                    priceItem.setSortOrder(maxSortOrder + 1);
                    priceItem.setCreateBy(username);
                    priceItem.setCreateTime(DateUtils.getNowDate());
                    totalRows += bizTemplatePriceItemMapper.insertBizTemplatePriceItem(priceItem);
                }
            }
        }
        
        return totalRows;
    }
}
