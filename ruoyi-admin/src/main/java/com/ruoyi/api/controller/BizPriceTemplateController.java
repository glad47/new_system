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
import com.ruoyi.api.domain.BizPriceTemplate;
import com.ruoyi.api.service.IBizPriceTemplateService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * Price Template Controller
 * Simple controller for price/size definitions
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/priceTemplate")
public class BizPriceTemplateController extends BaseController
{
    @Autowired
    private IBizPriceTemplateService bizPriceTemplateService;

    /**
     * Query price template list (paginated)
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizPriceTemplate bizPriceTemplate)
    {
        startPage();
        List<BizPriceTemplate> list = bizPriceTemplateService.selectBizPriceTemplateList(bizPriceTemplate);
        return getDataTable(list);
    }

    /**
     * Query all active price templates (for dropdown)
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:query')")
    @GetMapping("/listAll")
    public AjaxResult listAll()
    {
        List<BizPriceTemplate> list = bizPriceTemplateService.selectAllActivePriceTemplates();
        return success(list);
    }

    /**
     * Export price template list
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:export')")
    @Log(title = "Price Template", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPriceTemplate bizPriceTemplate)
    {
        List<BizPriceTemplate> list = bizPriceTemplateService.selectBizPriceTemplateList(bizPriceTemplate);
        ExcelUtil<BizPriceTemplate> util = new ExcelUtil<BizPriceTemplate>(BizPriceTemplate.class);
        util.exportExcel(response, list, "Price Template Data");
    }

    /**
     * Get price template detail
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:query')")
    @GetMapping(value = "/{priceTemplateId}")
    public AjaxResult getInfo(@PathVariable("priceTemplateId") Long priceTemplateId)
    {
        return success(bizPriceTemplateService.selectBizPriceTemplateById(priceTemplateId));
    }

    /**
     * Add price template
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:add')")
    @Log(title = "Price Template", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizPriceTemplate bizPriceTemplate)
    {
        return toAjax(bizPriceTemplateService.insertBizPriceTemplate(bizPriceTemplate));
    }

    /**
     * Update price template
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:edit')")
    @Log(title = "Price Template", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizPriceTemplate bizPriceTemplate)
    {
        return toAjax(bizPriceTemplateService.updateBizPriceTemplate(bizPriceTemplate));
    }

    /**
     * Delete price template
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:remove')")
    @Log(title = "Price Template", businessType = BusinessType.DELETE)
    @DeleteMapping("/{priceTemplateIds}")
    public AjaxResult remove(@PathVariable Long[] priceTemplateIds)
    {
        return toAjax(bizPriceTemplateService.deleteBizPriceTemplateByIds(priceTemplateIds));
    }
}
