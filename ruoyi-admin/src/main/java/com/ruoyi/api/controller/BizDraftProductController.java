package com.ruoyi.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.api.domain.BizDraftProduct;
import com.ruoyi.api.service.IBizDraftProductService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;

/**
 * Draft Product Controller
 * مسودة المنتجات — تُحفظ محلياً قبل الإرسال إلى D365
 */
@RestController
@RequestMapping("/draft")
public class BizDraftProductController extends BaseController
{
    @Autowired
    private IBizDraftProductService service;

    /** GET /draft/list — paginated list */
    @PreAuthorize("@ss.hasPermi('biz:draft:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizDraftProduct draft)
    {
        startPage();
        List<BizDraftProduct> list = service.selectList(draft);
        return getDataTable(list);
    }

    /** GET /draft/{draftId} — detail */
    @PreAuthorize("@ss.hasPermi('biz:draft:query')")
    @GetMapping("/{draftId}")
    public AjaxResult getInfo(@PathVariable Long draftId)
    {
        return success(service.selectById(draftId));
    }

    /** POST /draft — save as draft (NOT sent to D365 yet) */
    @PreAuthorize("@ss.hasPermi('biz:draft:add')")
    @Log(title = "Draft Product - Save", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizDraftProduct draft)
    {
        int rows = service.insert(draft);
        if (rows > 0) {
            return success(draft); // return with generated draftId
        }
        return error("فشل حفظ المسودة | Failed to save draft");
    }

    /** PUT /draft — update draft */
    @PreAuthorize("@ss.hasPermi('biz:draft:edit')")
    @Log(title = "Draft Product - Update", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizDraftProduct draft)
    {
        return toAjax(service.update(draft));
    }

    /** DELETE /draft/{draftIds} — soft delete */
    @PreAuthorize("@ss.hasPermi('biz:draft:remove')")
    @Log(title = "Draft Product - Delete", businessType = BusinessType.DELETE)
    @DeleteMapping("/{draftIds}")
    public AjaxResult remove(@PathVariable Long[] draftIds)
    {
        return toAjax(service.deleteByIds(draftIds));
    }

    /** POST /draft/submit/{draftId} — submit ONE draft to D365 */
    @PreAuthorize("@ss.hasPermi('biz:draft:submit')")
    @Log(title = "Draft Product - Submit to D365", businessType = BusinessType.UPDATE)
    @PostMapping("/submit/{draftId}")
    public AjaxResult submit(@PathVariable Long draftId)
    {
        try {
            BizDraftProduct result = service.submitToD365(draftId);
            if ("success".equals(result.getDraftStatus())) {
                return success("تم إرسال المنتج بنجاح إلى D365 | Product submitted to D365 successfully", result);
            } else {
                return error("فشل الإرسال إلى D365 | Submit to D365 failed: " +
                    (result.getErrorMessage() != null ? result.getErrorMessage() : "Unknown error"));
            }
        } catch (Exception e) {
            return error("خطأ في الإرسال | Submit error: " + e.getMessage());
        }
    }

    /** POST /draft/submitAll — submit ALL pending drafts to D365 */
    @PreAuthorize("@ss.hasPermi('biz:draft:submit')")
    @Log(title = "Draft Product - Submit All to D365", businessType = BusinessType.UPDATE)
    @PostMapping("/submitAll")
    public AjaxResult submitAll()
    {
        try {
            int count = service.submitAllDrafts();
            return success("تم إرسال " + count + " منتج بنجاح | " + count + " products submitted successfully");
        } catch (Exception e) {
            return error("خطأ في الإرسال الجماعي | Batch submit error: " + e.getMessage());
        }
    }
}
