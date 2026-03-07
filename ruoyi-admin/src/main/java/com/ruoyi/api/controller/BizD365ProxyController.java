package com.ruoyi.api.controller;

import java.util.Collections;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.alibaba.fastjson2.JSON;
import com.ruoyi.api.service.ID365ProxyService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;

@RestController
@RequestMapping("/d365proxy")
public class BizD365ProxyController extends BaseController
{
    private static final Logger log = LoggerFactory.getLogger(BizD365ProxyController.class);
    @Autowired private ID365ProxyService d365ProxyService;

    private Object parseD365(String raw) {
        if (raw == null || raw.isEmpty()) return Collections.emptyList();
        String s = raw.trim();
        if (s.startsWith("\"") && s.endsWith("\"")) {
            try { String u = JSON.parseObject(s, String.class); if (u != null) s = u.trim(); } catch (Exception ignored) {}
        }
        try {
            if (s.startsWith("[")) return JSON.parseArray(s);
            if (s.startsWith("{")) return JSON.parseObject(s);
        } catch (Exception e) { log.warn("Parse fail: {}", e.getMessage()); }
        return raw;
    }

    @PreAuthorize("@ss.hasPermi('biz:d365config:query')")
    @PostMapping("/token")
    public AjaxResult getToken() {
        try { String t = d365ProxyService.getAccessToken(); return success("OK (" + t.substring(0, 20) + "...)"); }
        catch (Exception e) { return error("Token failed: " + e.getMessage()); }
    }

    @PreAuthorize("@ss.hasPermi('biz:product:list')")
    @PostMapping("/listReleasedProducts")
    public AjaxResult listReleasedProducts(@RequestBody Map<String, Object> contract) {
        try { return success(parseD365(d365ProxyService.listReleasedProducts(contract))); }
        catch (Exception e) { return error("listReleasedProducts failed: " + e.getMessage()); }
    }

    @PreAuthorize("@ss.hasPermi('biz:product:add')")
    @Log(title = "D365 Create Product", businessType = BusinessType.INSERT)
    @PostMapping("/createProduct")
    public AjaxResult createProduct(@RequestBody Map<String, Object> contract) {
        try { return success(parseD365(d365ProxyService.createProduct(contract))); }
        catch (Exception e) { return error("createProduct failed: " + e.getMessage()); }
    }

    @PreAuthorize("@ss.hasPermi('biz:product:edit')")
    @Log(title = "D365 Update Product", businessType = BusinessType.UPDATE)
    @PostMapping("/updateProduct")
    public AjaxResult updateProduct(@RequestBody Map<String, Object> contract) {
        try { return success(parseD365(d365ProxyService.updateProduct(contract))); }
        catch (Exception e) { return error("updateProduct failed: " + e.getMessage()); }
    }

    @PreAuthorize("@ss.hasPermi('biz:product:query')")
    @PostMapping("/lookup/{operation}")
    public AjaxResult lookup(@PathVariable String operation, @RequestBody(required = false) Map<String, Object> extra) {
        String[] ok = {"getItemGroups","getItemModelGroups","getVendors","getBuyerGroups","getTaxItemGroups",
            "getStorageDimensionGroups","getTrackingDimensionGroups","getUnitsOfMeasure","getLifecycleStates",
            "getUnitSequenceGroups","getRetailCategories","getCategoryHierarchies","getProductTypes",
            "getProductSubTypes","getApprovedVendorCheckMethods", "getProductByNumber"};
        boolean allowed = false;
        for (String o : ok) if (o.equals(operation)) { allowed = true; break; }
        if (!allowed) return error("Not allowed: " + operation);
        try { return success(parseD365(d365ProxyService.callLookup(operation, extra))); }
        catch (Exception e) { return error("Lookup " + operation + " failed: " + e.getMessage()); }
    }
}
