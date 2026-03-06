package com.ruoyi.api.service.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import com.ruoyi.api.domain.BizD365Config;
import com.ruoyi.api.service.IBizD365ConfigService;
import com.ruoyi.api.service.ID365ProxyService;

@Service
public class D365ProxyServiceImpl implements ID365ProxyService
{
    private static final Logger log = LoggerFactory.getLogger(D365ProxyServiceImpl.class);

    @Autowired private RestTemplate restTemplate;
    @Autowired private IBizD365ConfigService configService;

    private String cachedToken = null;
    private long tokenExpiry = 0;

    private Map<String, String> loadConfig() {
        Map<String, String> map = new HashMap<>();
        for (BizD365Config c : configService.selectBizD365ConfigList(new BizD365Config())) {
            map.put(c.getConfigKey(), c.getConfigValue());
        }
        return map;
    }

    @Override
    public synchronized String getAccessToken() {
        if (cachedToken != null && System.currentTimeMillis() < tokenExpiry) return cachedToken;
        Map<String, String> cfg = loadConfig();
        String tokenUrl = "https://login.microsoftonline.com/" + cfg.getOrDefault("d365.tenant.id", "") + "/oauth2/v2.0/token";
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("client_id", cfg.getOrDefault("d365.client.id", ""));
        body.add("client_secret", cfg.getOrDefault("d365.client.secret", ""));
        body.add("scope", cfg.getOrDefault("d365.base.url", "") + "/.default");
        try {
            ResponseEntity<Map> r = restTemplate.postForEntity(tokenUrl, new HttpEntity<>(body, h), Map.class);
            if (r.getStatusCode().is2xxSuccessful() && r.getBody() != null) {
                cachedToken = (String) r.getBody().get("access_token");
                long exp = r.getBody().get("expires_in") != null ? Long.parseLong(r.getBody().get("expires_in").toString()) : 3600;
                tokenExpiry = System.currentTimeMillis() + ((exp - 600) * 1000);
                log.info("D365 token OK");
                return cachedToken;
            }
            throw new RuntimeException("Token failed: " + r.getStatusCode());
        } catch (Exception e) {
            cachedToken = null; tokenExpiry = 0;
            throw new RuntimeException("Token error: " + e.getMessage(), e);
        }
    }

    private String callD365(String url, Object body) {
        String token = getAccessToken();
        HttpHeaders h = new HttpHeaders();
        h.setContentType(MediaType.APPLICATION_JSON);
        h.set("Accept", "application/json");
        h.setBearerAuth(token);
        HttpEntity<Object> req = new HttpEntity<>(body, h);
        log.info("D365 → {}", url);
        try {
            ResponseEntity<String> r = restTemplate.postForEntity(url, req, String.class);
            String rb = r.getBody();
            log.info("D365 ← {} len={}", r.getStatusCode(), rb != null ? rb.length() : 0);
            if (rb != null) log.debug("D365 ← {}", rb.length() > 500 ? rb.substring(0, 500) : rb);
            return rb;
        } catch (Exception e) {
            log.error("D365 FAIL → {} — {}", url, e.getMessage());
            if (e.getMessage() != null && e.getMessage().contains("401")) {
                cachedToken = null; tokenExpiry = 0;
                h.setBearerAuth(getAccessToken());
                return restTemplate.postForEntity(url, new HttpEntity<>(body, h), String.class).getBody();
            }
            throw new RuntimeException("D365 call failed: " + e.getMessage(), e);
        }
    }

    private String buildUrl(Map<String, String> cfg, String operation) {
        return cfg.getOrDefault("d365.base.url", "") + "/api/services/"
            + cfg.getOrDefault("d365.service.group", "QProductCreationServiceGroup") + "/"
            + cfg.getOrDefault("d365.service.name", "QProductCreationService") + "/" + operation;
    }

    @Override
    public String listReleasedProducts(Map<String, Object> contract) {
        Map<String, String> cfg = loadConfig();
        if (!contract.containsKey("company")) contract.put("company", cfg.getOrDefault("d365.default.company", "USMF"));
        if (!contract.containsKey("pageSize")) contract.put("pageSize", 20);
        if (!contract.containsKey("pageNumber")) contract.put("pageNumber", 1);
        Map<String, Object> body = new HashMap<>();
        body.put("_contract", contract);
        return callD365(buildUrl(cfg, "listReleasedProducts"), body);
    }

    @Override
    public String createProduct(Map<String, Object> contract) {
        Map<String, Object> body = new HashMap<>();
        body.put("_contract", contract);
        return callD365(buildUrl(loadConfig(), "createProduct"), body);
    }

    @Override
    public String updateProduct(Map<String, Object> contract) {
        Map<String, Object> body = new HashMap<>();
        body.put("_contract", contract);
        return callD365(buildUrl(loadConfig(), "updateProduct"), body);
    }

    @Override
    public String callLookup(String operation, Map<String, Object> extraParams) {
        Map<String, String> cfg = loadConfig();
        Map<String, Object> contract = new LinkedHashMap<>();
        contract.put("company", cfg.getOrDefault("d365.default.company", "USMF"));
        if (extraParams != null) contract.putAll(extraParams);
        Map<String, Object> body = new HashMap<>();
        body.put("_contract", contract);
        return callD365(buildUrl(cfg, operation), body);
    }
}
