package com.ruoyi.api.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.api.domain.BizD365DefaultValue;
import com.ruoyi.api.service.IBizD365DefaultValueService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * القيم الافتراضية لنقاط النهاية | D365 Endpoint Default Values Controller
 */
@RestController
@RequestMapping("/d365defaults")
public class BizD365DefaultValueController extends BaseController {

    @Autowired
    private IBizD365DefaultValueService service;

    /** GET /d365defaults/list — paginated list for admin UI */
    @PreAuthorize("@ss.hasPermi('biz:defaults:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizD365DefaultValue query) {
        startPage();
        List<BizD365DefaultValue> list = service.selectList(query);
        return getDataTable(list);
    }

    /** GET /d365defaults/{id} — single record */
    @PreAuthorize("@ss.hasPermi('biz:defaults:list')")
    @GetMapping("/{defaultId}")
    public AjaxResult getInfo(@PathVariable Long defaultId) {
        return success(service.selectById(defaultId));
    }

    /** GET /d365defaults/map — all defaults as {endpointName: defaultValue} for product form */
    @PreAuthorize("@ss.hasPermi('biz:product:query')")
    @GetMapping("/map")
    public AjaxResult getDefaultsMap() {
        Map<String, String> map = service.getAllDefaultsMap();
        return success(map);
    }

    /** POST /d365defaults — add new endpoint default */
    @PreAuthorize("@ss.hasPermi('biz:defaults:edit')")
    @PostMapping
    public AjaxResult add(@RequestBody BizD365DefaultValue record) {
        return toAjax(service.insert(record));
    }

    /** PUT /d365defaults — update endpoint default (set the chosen default value) */
    @PreAuthorize("@ss.hasPermi('biz:defaults:edit')")
    @PutMapping
    public AjaxResult edit(@RequestBody BizD365DefaultValue record) {
        return toAjax(service.update(record));
    }

    /** DELETE /d365defaults/{ids} — soft delete */
    @PreAuthorize("@ss.hasPermi('biz:defaults:edit')")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(service.deleteByIds(ids));
    }
}
