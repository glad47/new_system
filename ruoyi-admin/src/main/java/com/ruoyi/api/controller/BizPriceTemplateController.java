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
     * GET /priceTemplate/list
     * Query paginated price template list
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
     * GET /priceTemplate/listAll
     * Query all active price templates (for dropdowns)
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:query')")
    @GetMapping("/listAll")
    public AjaxResult listAll()
    {
        List<BizPriceTemplate> list = bizPriceTemplateService.selectAllActivePriceTemplates();
        return success(list);
    }

    /**
     * GET /priceTemplate/default
     * Get the current system-default price template
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:query')")
    @GetMapping("/default")
    public AjaxResult getDefault()
    {
        return success(bizPriceTemplateService.selectDefaultPriceTemplate());
    }

    /**
     * POST /priceTemplate/export
     * Export price template list to Excel
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:export')")
    @Log(title = "Price Template", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPriceTemplate bizPriceTemplate)
    {
        List<BizPriceTemplate> list = bizPriceTemplateService.selectBizPriceTemplateList(bizPriceTemplate);
        ExcelUtil<BizPriceTemplate> util = new ExcelUtil<>(BizPriceTemplate.class);
        util.exportExcel(response, list, "Price Template Data");
    }

    /**
     * GET /priceTemplate/{priceTemplateId}
     * Get price template detail by ID
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:query')")
    @GetMapping("/{priceTemplateId}")
    public AjaxResult getInfo(@PathVariable("priceTemplateId") Long priceTemplateId)
    {
        return success(bizPriceTemplateService.selectBizPriceTemplateById(priceTemplateId));
    }

    /**
     * POST /priceTemplate
     * Add a new price template
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:add')")
    @Log(title = "Price Template", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizPriceTemplate bizPriceTemplate)
    {
        return toAjax(bizPriceTemplateService.insertBizPriceTemplate(bizPriceTemplate));
    }

    /**
     * PUT /priceTemplate
     * Update an existing price template
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:edit')")
    @Log(title = "Price Template", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizPriceTemplate bizPriceTemplate)
    {
        return toAjax(bizPriceTemplateService.updateBizPriceTemplate(bizPriceTemplate));
    }

    /**
     * PUT /priceTemplate/setDefault/{priceTemplateId}
     * Set the given price template as the system default.
     * Atomically clears the old default and sets the new one.
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:edit')")
    @Log(title = "Price Template - Set Default", businessType = BusinessType.UPDATE)
    @PutMapping("/setDefault/{priceTemplateId}")
    public AjaxResult setDefault(@PathVariable("priceTemplateId") Long priceTemplateId)
    {
        bizPriceTemplateService.setDefaultPriceTemplate(priceTemplateId);
        return success();
    }

    /**
     * DELETE /priceTemplate/{priceTemplateIds}
     * Soft-delete one or more price templates
     */
    @PreAuthorize("@ss.hasPermi('biz:priceTemplate:remove')")
    @Log(title = "Price Template", businessType = BusinessType.DELETE)
    @DeleteMapping("/{priceTemplateIds}")
    public AjaxResult remove(@PathVariable Long[] priceTemplateIds)
    {
        return toAjax(bizPriceTemplateService.deleteBizPriceTemplateByIds(priceTemplateIds));
    }
}
