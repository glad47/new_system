package com.ruoyi.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.api.domain.BizProductNumberSeq;
import com.ruoyi.api.service.IBizProductNumberSeqService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;

/**
 * Product Number Sequence Controller
 * تسلسل أرقام المنتجات
 */
@RestController
@RequestMapping("/productseq")
public class BizProductNumberSeqController extends BaseController
{
    @Autowired
    private IBizProductNumberSeqService service;

    @PreAuthorize("@ss.hasPermi('biz:productseq:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizProductNumberSeq seq)
    {
        startPage();
        List<BizProductNumberSeq> list = service.selectList(seq);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('biz:productseq:query')")
    @GetMapping("/{seqId}")
    public AjaxResult getInfo(@PathVariable Long seqId)
    {
        return success(service.selectById(seqId));
    }

    @PreAuthorize("@ss.hasPermi('biz:productseq:edit')")
    @Log(title = "Product Number Seq", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizProductNumberSeq seq)
    {
        return toAjax(service.insert(seq));
    }

    @PreAuthorize("@ss.hasPermi('biz:productseq:edit')")
    @Log(title = "Product Number Seq", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizProductNumberSeq seq)
    {
        return toAjax(service.update(seq));
    }

    @PreAuthorize("@ss.hasPermi('biz:productseq:edit')")
    @DeleteMapping("/{seqIds}")
    public AjaxResult remove(@PathVariable Long[] seqIds)
    {
        return toAjax(service.deleteByIds(seqIds));
    }

    /** POST /productseq/nextNumber/{seqId} — get next product number */
    @PreAuthorize("@ss.hasPermi('biz:productseq:query')")
    @PostMapping("/nextNumber/{seqId}")
    public AjaxResult getNextNumber(@PathVariable Long seqId)
    {
        String nextNumber = service.getNextProductNumber(seqId);
        return success(nextNumber);
    }
}
