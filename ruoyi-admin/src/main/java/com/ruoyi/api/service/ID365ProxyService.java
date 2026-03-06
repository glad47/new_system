package com.ruoyi.api.service;

import java.util.Map;

public interface ID365ProxyService
{
    String getAccessToken();
    String listReleasedProducts(Map<String, Object> contract);
    String createProduct(Map<String, Object> contract);
    String updateProduct(Map<String, Object> contract);
    String callLookup(String operation, Map<String, Object> extraParams);
}
