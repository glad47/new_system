package com.ruoyi.api.controller;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.api.service.IBizTradeAgreementConfigService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * إعدادات الاتفاقيات التجارية | Trade Agreement Configuration Controller
 */
@RestController
@RequestMapping("/tradeAgreementConfig")
public class BizTradeAgreementConfigController extends BaseController {

    @Autowired
    private IBizTradeAgreementConfigService service;

    /**
     * GET /tradeAgreementConfig — load all config as map
     */
    @PreAuthorize("@ss.hasPermi('biz:tradeconfig:query')")
    @GetMapping
    public AjaxResult getConfig() {
        Map<String, String> map = service.getAllConfigMap();
        return success(map);
    }

    /**
     * POST /tradeAgreementConfig — save config map from frontend
     */
    @PreAuthorize("@ss.hasPermi('biz:tradeconfig:edit')")
    @PostMapping
    public AjaxResult saveConfig(@RequestBody Map<String, String> configMap) {
        int count = service.saveConfig(configMap);
        return toAjax(count > 0 ? 1 : 0);
    }
}
