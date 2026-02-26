package com.ruoyi.api.service.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ruoyi.api.domain.BizTemplate;
import com.ruoyi.api.domain.BizTemplateItem;
import com.ruoyi.api.domain.dto.PrintLabelDTO;
import com.ruoyi.api.mapper.BizTemplateItemMapper;
import com.ruoyi.api.mapper.BizTemplateMapper;
import com.ruoyi.api.service.IBizPrintingService;

/**
 * Printing Service Implementation
 *
 * Combines mock product data with real template resolution from the database.
 * The mock product lookup can be replaced with an ERP/POS API call in production.
 *
 * @author ruoyi
 */
@Service
public class BizPrintingServiceImpl implements IBizPrintingService
{
    private static final Logger log = LoggerFactory.getLogger(BizPrintingServiceImpl.class);

    @Autowired
    private BizTemplateMapper bizTemplateMapper;

    @Autowired
    private BizTemplateItemMapper bizTemplateItemMapper;

    // ─────────────────────────────────────────────────────────────────────────
    // MOCK PRODUCT DATA
    // Replace this with a real ERP/POS API call in production.
    // ─────────────────────────────────────────────────────────────────────────
    private static final Map<String, MockProduct> MOCK_PRODUCTS = new HashMap<>();

    static
    {
        MOCK_PRODUCTS.put("6901234567890", new MockProduct(
            "6901234567890", "Premium Coffee Beans", "حبوب قهوة ممتازة",
            new BigDecimal("89.99"), new BigDecimal("59.99"), 500, 3));

        MOCK_PRODUCTS.put("6902345678901", new MockProduct(
            "6902345678901", "Organic Green Tea", "شاي أخضر عضوي",
            new BigDecimal("45.00"), new BigDecimal("29.99"), 200, 5));

        MOCK_PRODUCTS.put("6903456789012", new MockProduct(
            "6903456789012", "Natural Honey 500g", "عسل طبيعي 500غ",
            new BigDecimal("120.00"), new BigDecimal("99.00"), 100, 2));

        MOCK_PRODUCTS.put("6904567890123", new MockProduct(
            "6904567890123", "Extra Virgin Olive Oil 1L", "زيت زيتون بكر ممتاز 1 لتر",
            new BigDecimal("75.00"), new BigDecimal("49.99"), 300, 4));

        MOCK_PRODUCTS.put("6905678901234", new MockProduct(
            "6905678901234", "Organic Dates 1kg", "تمور عضوية 1 كيلو",
            new BigDecimal("55.00"), new BigDecimal("39.99"), 150, 6));
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PUBLIC API
    // ─────────────────────────────────────────────────────────────────────────

    @Override
    public PrintLabelDTO lookupByBarcode(String barcode)
    {
        // Step 1: Look up product
        MockProduct product = lookupProduct(barcode);
        if (product == null)
        {
            log.info("[Printing] Product not found for barcode: {}", barcode);
            return null;
        }

        // Step 2: Load system-default template
        BizTemplate defaultTemplate = bizTemplateMapper.selectDefaultTemplate();
        if (defaultTemplate == null)
        {
            log.warn("[Printing] No system-default template configured");
            return null;
        }

        return buildDTO(product, defaultTemplate);
    }

    @Override
    public PrintLabelDTO lookupByBarcodeWithTemplate(String barcode, Long templateId)
    {
        // Step 1: Look up product
        MockProduct product = lookupProduct(barcode);
        if (product == null)
        {
            log.info("[Printing] Product not found for barcode: {}", barcode);
            return null;
        }

        // Step 2: Load specific template
        BizTemplate template = bizTemplateMapper.selectBizTemplateById(templateId);
        if (template == null)
        {
            log.warn("[Printing] Template not found: {}", templateId);
            return null;
        }

        return buildDTO(product, template);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // PRIVATE HELPERS
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Build the full PrintLabelDTO from a product and a template.
     */
    private PrintLabelDTO buildDTO(MockProduct product, BizTemplate template)
    {
        // Load all template items with their price template relations (JOIN)
        List<BizTemplateItem> items = bizTemplateItemMapper
            .selectBizTemplateItemsByTemplateIdWithRelation(template.getTemplateId());
        template.setTemplateItems(items);

        // Find the default item (is_default = '1'), fallback to first item
        BizTemplateItem defaultItem = items.stream()
            .filter(i -> "1".equals(i.getIsDefault()))
            .findFirst()
            .orElse(items.isEmpty() ? null : items.get(0));

        // Build the response DTO
        PrintLabelDTO dto = new PrintLabelDTO();

        // Product data
        dto.setBarcode(product.barcode);
        dto.setProductName(product.productName);
        dto.setProductNameAr(product.productNameAr);
        dto.setPriceBefore(product.priceBefore);
        dto.setPriceAfter(product.priceAfter);
        dto.setQuantity(product.quantity);
        dto.setPerCustomer(product.perCustomer);

        // Template context
        dto.setTemplate(template);
        dto.setDefaultItem(defaultItem);
        dto.setPriceTemplate(defaultItem != null ? defaultItem.getPriceTemplate() : null);
        dto.setAllItems(items);

        log.info("[Printing] Resolved label for barcode={}, template={}, defaultItem={}, priceTemplate={}",
            product.barcode,
            template.getTemplateName(),
            defaultItem != null ? defaultItem.getTemplateItemId() : "none",
            defaultItem != null && defaultItem.getPriceTemplate() != null
                ? defaultItem.getPriceTemplate().getSize() : "none");

        return dto;
    }

    /**
     * Mock product lookup.
     * TODO: Replace with real ERP/POS API integration.
     */
    private MockProduct lookupProduct(String barcode)
    {
        return MOCK_PRODUCTS.get(barcode);
    }

    // ─────────────────────────────────────────────────────────────────────────
    // MOCK PRODUCT RECORD
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Simple record-style class for mock product data.
     * Replace with a real domain entity or DTO when integrating with ERP.
     */
    private static class MockProduct
    {
        final String barcode;
        final String productName;
        final String productNameAr;
        final BigDecimal priceBefore;
        final BigDecimal priceAfter;
        final Integer quantity;
        final Integer perCustomer;

        MockProduct(String barcode, String productName, String productNameAr,
                    BigDecimal priceBefore, BigDecimal priceAfter,
                    Integer quantity, Integer perCustomer)
        {
            this.barcode = barcode;
            this.productName = productName;
            this.productNameAr = productNameAr;
            this.priceBefore = priceBefore;
            this.priceAfter = priceAfter;
            this.quantity = quantity;
            this.perCustomer = perCustomer;
        }
    }
}
