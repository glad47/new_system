package com.ruoyi.api.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.api.domain.BizD365Config;
import com.ruoyi.api.service.IBizD365ConfigService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;

/**
 * D365 Configuration Controller
 * إعدادات D365
 */
@RestController
@RequestMapping("/d365config")
public class BizD365ConfigController extends BaseController
{
    @Autowired
    private IBizD365ConfigService service;

    /** GET /d365config/list */
    @PreAuthorize("@ss.hasPermi('biz:d365config:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizD365Config config)
    {
        startPage();
        List<BizD365Config> list = service.selectBizD365ConfigList(config);
        return getDataTable(list);
    }

    /** GET /d365config/all — all configs without pagination (for frontend usage) */
    @PreAuthorize("@ss.hasPermi('biz:d365config:query')")
    @GetMapping("/all")
    public AjaxResult all()
    {
        return success(service.selectBizD365ConfigList(new BizD365Config()));
    }

    /** GET /d365config/{configId} */
    @PreAuthorize("@ss.hasPermi('biz:d365config:query')")
    @GetMapping("/{configId}")
    public AjaxResult getInfo(@PathVariable Long configId)
    {
        return success(service.selectBizD365ConfigById(configId));
    }

    /** GET /d365config/key/{configKey} — get by key */
    @PreAuthorize("@ss.hasPermi('biz:d365config:query')")
    @GetMapping("/key/{configKey}")
    public AjaxResult getByKey(@PathVariable String configKey)
    {
        return success(service.selectBizD365ConfigByKey(configKey));
    }

    /** POST /d365config */
    @PreAuthorize("@ss.hasPermi('biz:d365config:add')")
    @Log(title = "D365 Config", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody BizD365Config config)
    {
        return toAjax(service.insertBizD365Config(config));
    }

    /** PUT /d365config */
    @PreAuthorize("@ss.hasPermi('biz:d365config:edit')")
    @Log(title = "D365 Config", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody BizD365Config config)
    {
        return toAjax(service.updateBizD365Config(config));
    }

    /** DELETE /d365config/{configIds} */
    @PreAuthorize("@ss.hasPermi('biz:d365config:remove')")
    @Log(title = "D365 Config", businessType = BusinessType.DELETE)
    @DeleteMapping("/{configIds}")
    public AjaxResult remove(@PathVariable Long[] configIds)
    {
        return toAjax(service.deleteBizD365ConfigByIds(configIds));
    }
}
