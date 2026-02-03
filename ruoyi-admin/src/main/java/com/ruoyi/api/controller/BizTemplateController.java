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
import com.ruoyi.api.domain.BizTemplate;
import com.ruoyi.api.service.IBizTemplateService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * Template Controller
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/api/template")
public class BizTemplateController extends BaseController
{
    @Autowired
    private IBizTemplateService bizTemplateService;

    /**
     * Query template list (paginated) with relations
     */
    @PreAuthorize("@ss.hasPermi('biz:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizTemplate bizTemplate)
    {
        startPage();
        List<BizTemplate> list = bizTemplateService.selectBizTemplateListWithRelations(bizTemplate);
        return getDataTable(list);
    }

    /**
     * Export template list
     */
    @PreAuthorize("@ss.hasPermi('biz:template:export')")
    @Log(title = "Template", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizTemplate bizTemplate)
    {
        List<BizTemplate> list = bizTemplateService.selectBizTemplateList(bizTemplate);
        ExcelUtil<BizTemplate> util = new ExcelUtil<BizTemplate>(BizTemplate.class);
        util.exportExcel(response, list, "Template Data");
    }

    /**
     * Get template detail with all relations
     */
    @PreAuthorize("@ss.hasPermi('biz:template:query')")
    @GetMapping(value = "/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        return success(bizTemplateService.selectBizTemplateByIdWithRelations(templateId));
    }

    /**
     * Add template
     * This automatically creates:
     * - biz_template record
     * - biz_template_price record (1:1)
     * - biz_template_price_item records (for ALL active template items)
     */
    @PreAuthorize("@ss.hasPermi('biz:template:add')")
    @Log(title = "Template", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizTemplate bizTemplate)
    {
        return toAjax(bizTemplateService.insertBizTemplate(bizTemplate));
    }

    /**
     * Update template
     */
    @PreAuthorize("@ss.hasPermi('biz:template:edit')")
    @Log(title = "Template", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizTemplate bizTemplate)
    {
        return toAjax(bizTemplateService.updateBizTemplate(bizTemplate));
    }

    /**
     * Delete template
     */
    @PreAuthorize("@ss.hasPermi('biz:template:remove')")
    @Log(title = "Template", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(bizTemplateService.deleteBizTemplateByIds(templateIds));
    }

    /**
     * Check template name uniqueness
     */
    @PreAuthorize("@ss.hasPermi('biz:template:query')")
    @GetMapping("/checkName")
    public AjaxResult checkTemplateName(BizTemplate bizTemplate)
    {
        return success(bizTemplateService.checkTemplateNameUnique(bizTemplate));
    }

    /**
     * Sync template price items with all active template items
     */
    @PreAuthorize("@ss.hasPermi('biz:template:edit')")
    @Log(title = "Template Sync", businessType = BusinessType.UPDATE)
    @PostMapping("/sync/{templateId}")
    public AjaxResult syncPriceItems(@PathVariable Long templateId)
    {
        return toAjax(bizTemplateService.syncTemplatePriceItems(templateId));
    }
}
