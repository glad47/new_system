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
@RequestMapping("/template")
public class BizTemplateController extends BaseController
{
    @Autowired
    private IBizTemplateService bizTemplateService;

    /** GET /template/list — paginated list with items */
    @PreAuthorize("@ss.hasPermi('biz:template:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizTemplate bizTemplate)
    {
        startPage();
        List<BizTemplate> list = bizTemplateService.selectBizTemplateListWithItems(bizTemplate);
        return getDataTable(list);
    }

    /** GET /template/default — get the current system-default template */
    @PreAuthorize("@ss.hasPermi('biz:template:query')")
    @GetMapping("/default")
    public AjaxResult getDefault()
    {
        return success(bizTemplateService.selectDefaultTemplate());
    }

    /** POST /template/export */
    @PreAuthorize("@ss.hasPermi('biz:template:export')")
    @Log(title = "Template", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizTemplate bizTemplate)
    {
        List<BizTemplate> list = bizTemplateService.selectBizTemplateList(bizTemplate);
        ExcelUtil<BizTemplate> util = new ExcelUtil<>(BizTemplate.class);
        util.exportExcel(response, list, "Template Data");
    }

    /** GET /template/{templateId} — detail with items */
    @PreAuthorize("@ss.hasPermi('biz:template:query')")
    @GetMapping("/{templateId}")
    public AjaxResult getInfo(@PathVariable("templateId") Long templateId)
    {
        return success(bizTemplateService.selectBizTemplateByIdWithItems(templateId));
    }

    /** POST /template — add */
    @PreAuthorize("@ss.hasPermi('biz:template:add')")
    @Log(title = "Template", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizTemplate bizTemplate)
    {
        return toAjax(bizTemplateService.insertBizTemplate(bizTemplate));
    }

    /** PUT /template — update */
    @PreAuthorize("@ss.hasPermi('biz:template:edit')")
    @Log(title = "Template", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizTemplate bizTemplate)
    {
        return toAjax(bizTemplateService.updateBizTemplate(bizTemplate));
    }

    /**
     * PUT /template/setDefault/{templateId}
     * Atomically clears the old default and marks the new one.
     */
    @PreAuthorize("@ss.hasPermi('biz:template:edit')")
    @Log(title = "Template - Set Default", businessType = BusinessType.UPDATE)
    @PutMapping("/setDefault/{templateId}")
    public AjaxResult setDefault(@PathVariable("templateId") Long templateId)
    {
        bizTemplateService.setDefaultTemplate(templateId);
        return success();
    }

    /** DELETE /template/{templateIds} — soft delete with cascade */
    @PreAuthorize("@ss.hasPermi('biz:template:remove')")
    @Log(title = "Template", businessType = BusinessType.DELETE)
    @DeleteMapping("/{templateIds}")
    public AjaxResult remove(@PathVariable Long[] templateIds)
    {
        return toAjax(bizTemplateService.deleteBizTemplateByIds(templateIds));
    }

    /** GET /template/checkName */
    @PreAuthorize("@ss.hasPermi('biz:template:query')")
    @GetMapping("/checkName")
    public AjaxResult checkTemplateName(BizTemplate bizTemplate)
    {
        return success(bizTemplateService.checkTemplateNameUnique(bizTemplate));
    }
}