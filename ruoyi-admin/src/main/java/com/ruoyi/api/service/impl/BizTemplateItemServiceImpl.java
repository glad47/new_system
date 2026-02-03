package com.ruoyi.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.api.domain.BizTemplateItem;
import com.ruoyi.api.domain.BizTemplateItemImage;
import com.ruoyi.api.mapper.BizTemplateItemImageMapper;
import com.ruoyi.api.mapper.BizTemplateItemMapper;
import com.ruoyi.api.service.IBizTemplateItemService;
import com.ruoyi.api.service.IBizTemplateService;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * Template Item Service Implementation
 * 
 * @author ruoyi
 */
@Service
public class BizTemplateItemServiceImpl implements IBizTemplateItemService
{
    @Autowired
    private BizTemplateItemMapper bizTemplateItemMapper;

    @Autowired
    private BizTemplateItemImageMapper bizTemplateItemImageMapper;

    @Autowired
    private IBizTemplateService bizTemplateService;

    /**
     * Query template item by ID
     */
    @Override
    public BizTemplateItem selectBizTemplateItemById(Long templateItemId)
    {
        return bizTemplateItemMapper.selectBizTemplateItemById(templateItemId);
    }

    /**
     * Query template item by ID with images
     */
    @Override
    public BizTemplateItem selectBizTemplateItemByIdWithImages(Long templateItemId)
    {
        return bizTemplateItemMapper.selectBizTemplateItemByIdWithImages(templateItemId);
    }

    /**
     * Query template item list
     */
    @Override
    public List<BizTemplateItem> selectBizTemplateItemList(BizTemplateItem bizTemplateItem)
    {
        return bizTemplateItemMapper.selectBizTemplateItemListWithImageCount(bizTemplateItem);
    }

    /**
     * Query all active template items
     */
    @Override
    public List<BizTemplateItem> selectAllActiveTemplateItems()
    {
        return bizTemplateItemMapper.selectAllActiveTemplateItems();
    }

    /**
     * Insert template item
     */
    @Override
    public int insertBizTemplateItem(BizTemplateItem bizTemplateItem)
    {
        String username = SecurityUtils.getUsername();
        bizTemplateItem.setCreateBy(username);
        bizTemplateItem.setCreateTime(DateUtils.getNowDate());
        return bizTemplateItemMapper.insertBizTemplateItem(bizTemplateItem);
    }

    /**
     * Insert template item with images
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertBizTemplateItemWithImages(BizTemplateItem bizTemplateItem)
    {
        String username = SecurityUtils.getUsername();
        bizTemplateItem.setCreateBy(username);
        bizTemplateItem.setCreateTime(DateUtils.getNowDate());
        
        // Insert template item
        int rows = bizTemplateItemMapper.insertBizTemplateItem(bizTemplateItem);
        
        // Insert images if present
        if (rows > 0 && bizTemplateItem.getImages() != null && !bizTemplateItem.getImages().isEmpty())
        {
            int sortOrder = 0;
            for (BizTemplateItemImage image : bizTemplateItem.getImages())
            {
                image.setTemplateItemId(bizTemplateItem.getTemplateItemId());
                image.setCreateBy(username);
                image.setCreateTime(DateUtils.getNowDate());
                image.setSortOrder(sortOrder++);
            }
            bizTemplateItemImageMapper.batchInsertBizTemplateItemImage(bizTemplateItem.getImages());
        }

        // Sync all existing templates with this new template item
        if (rows > 0)
        {
            bizTemplateService.syncAllTemplatesWithNewItem(bizTemplateItem.getTemplateItemId());
        }
        
        return rows;
    }

    /**
     * Update template item
     */
    @Override
    public int updateBizTemplateItem(BizTemplateItem bizTemplateItem)
    {
        String username = SecurityUtils.getUsername();
        bizTemplateItem.setUpdateBy(username);
        bizTemplateItem.setUpdateTime(DateUtils.getNowDate());
        return bizTemplateItemMapper.updateBizTemplateItem(bizTemplateItem);
    }

    /**
     * Update template item with images
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBizTemplateItemWithImages(BizTemplateItem bizTemplateItem)
    {
        String username = SecurityUtils.getUsername();
        bizTemplateItem.setUpdateBy(username);
        bizTemplateItem.setUpdateTime(DateUtils.getNowDate());
        
        // Update template item
        int rows = bizTemplateItemMapper.updateBizTemplateItem(bizTemplateItem);
        
        // Handle images - delete old and insert new if images list is provided
        if (bizTemplateItem.getImages() != null)
        {
            // Soft delete existing images
            bizTemplateItemImageMapper.deleteBizTemplateItemImageByItemId(bizTemplateItem.getTemplateItemId());
            
            // Insert new images
            if (!bizTemplateItem.getImages().isEmpty())
            {
                int sortOrder = 0;
                for (BizTemplateItemImage image : bizTemplateItem.getImages())
                {
                    image.setTemplateItemId(bizTemplateItem.getTemplateItemId());
                    image.setCreateBy(username);
                    image.setCreateTime(DateUtils.getNowDate());
                    image.setSortOrder(sortOrder++);
                }
                bizTemplateItemImageMapper.batchInsertBizTemplateItemImage(bizTemplateItem.getImages());
            }
        }
        
        return rows;
    }

    /**
     * Delete template item by ID
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBizTemplateItemById(Long templateItemId)
    {
        // Check if in use
        if (isTemplateItemInUse(templateItemId))
        {
            throw new ServiceException("Cannot delete template item that is in use as default item");
        }
        
        // Soft delete images first
        bizTemplateItemImageMapper.deleteBizTemplateItemImageByItemId(templateItemId);
        
        // Soft delete template item
        return bizTemplateItemMapper.deleteBizTemplateItemById(templateItemId);
    }

    /**
     * Batch delete template items
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteBizTemplateItemByIds(Long[] templateItemIds)
    {
        for (Long templateItemId : templateItemIds)
        {
            if (isTemplateItemInUse(templateItemId))
            {
                BizTemplateItem item = bizTemplateItemMapper.selectBizTemplateItemById(templateItemId);
                throw new ServiceException("Cannot delete template item '" + 
                    (item != null ? item.getItemName() : templateItemId) + "' that is in use as default item");
            }
            // Soft delete images
            bizTemplateItemImageMapper.deleteBizTemplateItemImageByItemId(templateItemId);
        }
        return bizTemplateItemMapper.deleteBizTemplateItemByIds(templateItemIds);
    }

    /**
     * Add image to template item
     */
    @Override
    public int addImageToTemplateItem(BizTemplateItemImage image)
    {
        String username = SecurityUtils.getUsername();
        image.setCreateBy(username);
        image.setCreateTime(DateUtils.getNowDate());
        
        // Get max sort order
        Integer maxSortOrder = bizTemplateItemImageMapper.getMaxSortOrderByItemId(image.getTemplateItemId());
        image.setSortOrder(maxSortOrder != null ? maxSortOrder + 1 : 0);
        
        return bizTemplateItemImageMapper.insertBizTemplateItemImage(image);
    }

    /**
     * Remove image from template item
     */
    @Override
    public int removeImageFromTemplateItem(Long imageId)
    {
        return bizTemplateItemImageMapper.deleteBizTemplateItemImageById(imageId);
    }

    /**
     * Check if template item is in use as default
     */
    @Override
    public boolean isTemplateItemInUse(Long templateItemId)
    {
        return bizTemplateItemMapper.checkTemplateItemInUse(templateItemId) > 0;
    }
}
