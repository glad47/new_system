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

@RestController
@RequestMapping("/draft")
public class BizDraftProductController extends BaseController
{
    @Autowired
    private IBizDraftProductService service;

    @PreAuthorize("@ss.hasPermi('biz:draft:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizDraftProduct draft) {
        startPage();
        return getDataTable(service.selectList(draft));
    }

    @PreAuthorize("@ss.hasPermi('biz:draft:query')")
    @GetMapping("/{draftId}")
    public AjaxResult getInfo(@PathVariable Long draftId) {
        return success(service.selectById(draftId));
    }

    /** POST /draft — create new draft */
    @PreAuthorize("@ss.hasPermi('biz:draft:add')")
    @PostMapping
    public AjaxResult add(@RequestBody BizDraftProduct draft) {
        int rows = service.insert(draft);
        return rows > 0 ? success(draft) : error("فشل حفظ المسودة");
    }

    /** PUT /draft — update draft */
    @PreAuthorize("@ss.hasPermi('biz:draft:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody BizDraftProduct draft) {
        return toAjax(service.update(draft));
    }

    /**
     * PUT /draft/autoSave — background auto-save (no validation, no logging)
     * Called silently from frontend on every field change
     */
    @PreAuthorize("@ss.hasPermi('biz:draft:edit')")
    @PutMapping("/autoSave")
    public AjaxResult autoSave(@RequestBody BizDraftProduct draft) {
        if (draft.getDraftId() == null) {
            // First save — insert
            int rows = service.insert(draft);
            return rows > 0 ? success(draft) : error("Auto-save failed");
        } else {
            // Subsequent saves — update
            service.update(draft);
            return success(draft);
        }
    }

    @PreAuthorize("@ss.hasPermi('biz:draft:remove')")
    @DeleteMapping("/{draftIds}")
    public AjaxResult remove(@PathVariable Long[] draftIds) {
        return toAjax(service.deleteByIds(draftIds));
    }

    /** POST /draft/submit/{draftId} — submit to D365, delete draft on success */
    @PreAuthorize("@ss.hasPermi('biz:draft:submit')")
    @Log(title = "Draft - Submit to D365", businessType = BusinessType.UPDATE)
    @PostMapping("/submit/{draftId}")
    public AjaxResult submit(@PathVariable Long draftId) {
        try {
            BizDraftProduct result = service.submitToD365(draftId);
            if ("success".equals(result.getDraftStatus())) {
                // Delete draft after successful D365 submission
                service.deleteByIds(new Long[]{draftId});
                return success(result);
            }
            return error("فشل الإرسال: " + (result.getErrorMessage() != null ? result.getErrorMessage() : "Unknown"));
        } catch (Exception e) {
            return error("خطأ: " + e.getMessage());
        }
    }

    /** POST /draft/submitAll — submit all pending drafts, delete successful ones */
    @PreAuthorize("@ss.hasPermi('biz:draft:submit')")
    @Log(title = "Draft - Submit All", businessType = BusinessType.UPDATE)
    @PostMapping("/submitAll")
    public AjaxResult submitAll() {
        try {
            int count = service.submitAllDrafts();
            return success("تم إرسال " + count + " منتج | " + count + " submitted");
        } catch (Exception e) {
            return error("خطأ: " + e.getMessage());
        }
    }
}
