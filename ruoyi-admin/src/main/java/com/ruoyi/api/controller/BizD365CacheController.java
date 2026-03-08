package com.ruoyi.api.controller;

import java.util.Collections;
import java.util.List;
import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.api.domain.BizD365Cache;
import com.ruoyi.api.service.IBizD365CacheService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;

@RestController
@RequestMapping("/d365cache")
public class BizD365CacheController extends BaseController {

    @Autowired private IBizD365CacheService service;

    @PreAuthorize("@ss.hasPermi('biz:cache:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizD365Cache cache) {
        startPage();
        return getDataTable(service.selectList(cache));
    }

    @PreAuthorize("@ss.hasPermi('biz:cache:list')")
    @GetMapping("/{cacheId}")
    public AjaxResult getInfo(@PathVariable Long cacheId) {
        return success(service.selectById(cacheId));
    }

    /** GET /d365cache/cached/{endpointName} — get cached data for frontend (parsed JSON) */
    @PreAuthorize("@ss.hasPermi('biz:product:query')")
    @GetMapping("/cached/{endpointName}")
    public AjaxResult getCached(@PathVariable String endpointName, @RequestParam(required = false) String extraParams) {
        String data = service.getCachedData(endpointName, extraParams);
        if (data == null) return success(Collections.emptyList());
        // Parse to return proper JSON not string
        try {
            String s = data.trim();
            if (s.startsWith("\"")) { try { s = JSON.parseObject(s, String.class); } catch (Exception ignored) {} }
            if (s.trim().startsWith("[")) return success(JSON.parseArray(s));
            if (s.trim().startsWith("{")) return success(JSON.parseObject(s));
        } catch (Exception ignored) {}
        return success(data);
    }

    @PreAuthorize("@ss.hasPermi('biz:cache:list')")
    @PostMapping
    public AjaxResult add(@RequestBody BizD365Cache cache) {
        return toAjax(service.insert(cache));
    }

    @PreAuthorize("@ss.hasPermi('biz:cache:list')")
    @PutMapping
    public AjaxResult edit(@RequestBody BizD365Cache cache) {
        return toAjax(service.update(cache));
    }

    @PreAuthorize("@ss.hasPermi('biz:cache:list')")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(service.deleteByIds(ids));
    }

    /** POST /d365cache/sync/{cacheId} — sync one endpoint */
    @PreAuthorize("@ss.hasPermi('biz:cache:list')")
    @PostMapping("/sync/{cacheId}")
    public AjaxResult syncOne(@PathVariable Long cacheId) {
        BizD365Cache r = service.syncEndpoint(cacheId);
        if (r != null && "success".equals(r.getSyncStatus())) return success(r);
        return error("Sync failed: " + (r != null ? r.getErrorMessage() : "not found"));
    }

    /** POST /d365cache/syncAll — sync all endpoints */
    @PreAuthorize("@ss.hasPermi('biz:cache:list')")
    @PostMapping("/syncAll")
    public AjaxResult syncAll() {
        int count = service.syncAll();
        return success("تم مزامنة " + count + " | Synced " + count);
    }
}
