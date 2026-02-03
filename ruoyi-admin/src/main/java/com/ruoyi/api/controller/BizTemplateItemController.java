package com.ruoyi.api.controller;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.api.domain.BizTemplateItem;
import com.ruoyi.api.domain.BizTemplateItemImage;
import com.ruoyi.api.service.IBizTemplateItemService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * Template Item Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/templateItem")
public class BizTemplateItemController extends BaseController
{
    @Autowired
    private IBizTemplateItemService bizTemplateItemService;

    /**
     * Query template item list (paginated)
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizTemplateItem bizTemplateItem)
    {
        startPage();
        List<BizTemplateItem> list = bizTemplateItemService.selectBizTemplateItemList(bizTemplateItem);
        return getDataTable(list);
    }

    /**
     * Query all active template items (for dropdown/selection)
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:list')")
    @GetMapping("/listAll")
    public AjaxResult listAll()
    {
        List<BizTemplateItem> list = bizTemplateItemService.selectAllActiveTemplateItems();
        return success(list);
    }

    /**
     * Export template item list
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:export')")
    @Log(title = "Template Item", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizTemplateItem bizTemplateItem)
    {
        List<BizTemplateItem> list = bizTemplateItemService.selectBizTemplateItemList(bizTemplateItem);
        ExcelUtil<BizTemplateItem> util = new ExcelUtil<BizTemplateItem>(BizTemplateItem.class);
        util.exportExcel(response, list, "Template Item Data");
    }

    /**
     * Get template item detail
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:query')")
    @GetMapping(value = "/{templateItemId}")
    public AjaxResult getInfo(@PathVariable("templateItemId") Long templateItemId)
    {
        return success(bizTemplateItemService.selectBizTemplateItemByIdWithImages(templateItemId));
    }

    /**
     * Add template item
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:add')")
    @Log(title = "Template Item", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizTemplateItem bizTemplateItem)
    {
        return toAjax(bizTemplateItemService.insertBizTemplateItemWithImages(bizTemplateItem));
    }

    /**
     * Update template item
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:edit')")
    @Log(title = "Template Item", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizTemplateItem bizTemplateItem)
    {
        return toAjax(bizTemplateItemService.updateBizTemplateItemWithImages(bizTemplateItem));
    }

    /**
     * Delete template item
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:remove')")
    @Log(title = "Template Item", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateItemIds}")
    public AjaxResult remove(@PathVariable Long[] templateItemIds)
    {
        return toAjax(bizTemplateItemService.deleteBizTemplateItemByIds(templateItemIds));
    }

    /**
     * Add image to template item
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:edit')")
    @Log(title = "Template Item Image", businessType = BusinessType.INSERT)
    @PostMapping("/image")
    public AjaxResult addImage(@Validated @RequestBody BizTemplateItemImage image)
    {
        return toAjax(bizTemplateItemService.addImageToTemplateItem(image));
    }

    /**
     * Remove image from template item
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:edit')")
    @Log(title = "Template Item Image", businessType = BusinessType.DELETE)
    @DeleteMapping("/image/{imageId}")
    public AjaxResult removeImage(@PathVariable Long imageId)
    {
        return toAjax(bizTemplateItemService.removeImageFromTemplateItem(imageId));
    }

    /**
     * Check if template item is in use
     */
    @PreAuthorize("@ss.hasPermi('biz:templateItem:query')")
    @GetMapping("/checkInUse/{templateItemId}")
    public AjaxResult checkInUse(@PathVariable Long templateItemId)
    {
        return success(bizTemplateItemService.isTemplateItemInUse(templateItemId));
    }
}
