package com.ruoyi.api.domain.dto;

import com.ruoyi.api.domain.BizPriceTemplate;
import com.ruoyi.api.domain.BizTemplate;
import com.ruoyi.api.domain.BizTemplateItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO returned by the barcode-lookup printing endpoint.
 *
 * Bundles everything an external system needs to render and print a label:
 *   - product details (from mock / future ERP API)
 *   - the system-default template (id, name)
 *   - the default template-item (image URL, annotations JSON, font sizes, colors)
 *   - the associated price template (physical dimensions in mm)
 *   - all available template items (so the caller can pick a different size)
 *
 * Example response shape:
 * {
 *   "barcode": "6901234567890",
 *   "productName": "Premium Coffee Beans",
 *   "productNameAr": "حبوب قهوة ممتازة",
 *   "priceBefore": 89.99,
 *   "priceAfter": 59.99,
 *   "quantity": 500,
 *   "perCustomer": 3,
 *   "template": { "templateId": 101, "templateName": "national day", ... },
 *   "defaultItem": { "templateItemId": 125, "imageUrl": "...", "imageAnnotations": "{...}", ... },
 *   "priceTemplate": { "priceTemplateId": 1, "size": "S", "widthMm": 110, "heightMm": 55, ... },
 *   "allItems": [ ... ]
 * }
 */
public class PrintLabelDTO implements Serializable
{
    private static final long serialVersionUID = 1L;

    // ── Product info ─────────────────────────────────────────────────────────
    private String barcode;
    private String productName;
    private String productNameAr;
    private BigDecimal priceBefore;
    private BigDecimal priceAfter;
    private Integer quantity;
    private Integer perCustomer;

    // ── Template context ─────────────────────────────────────────────────────

    /** The system-default template */
    private BizTemplate template;

    /** The default template-item (is_default='1') inside the default template */
    private BizTemplateItem defaultItem;

    /** The price template linked to the default item (has widthMm, heightMm) */
    private BizPriceTemplate priceTemplate;

    /** All active template items for this template (so caller can pick another size) */
    private List<BizTemplateItem> allItems;

    // ── Getters & Setters ────────────────────────────────────────────────────

    public String getBarcode() { return barcode; }
    public void setBarcode(String barcode) { this.barcode = barcode; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductNameAr() { return productNameAr; }
    public void setProductNameAr(String productNameAr) { this.productNameAr = productNameAr; }

    public BigDecimal getPriceBefore() { return priceBefore; }
    public void setPriceBefore(BigDecimal priceBefore) { this.priceBefore = priceBefore; }

    public BigDecimal getPriceAfter() { return priceAfter; }
    public void setPriceAfter(BigDecimal priceAfter) { this.priceAfter = priceAfter; }

    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    public Integer getPerCustomer() { return perCustomer; }
    public void setPerCustomer(Integer perCustomer) { this.perCustomer = perCustomer; }

    public BizTemplate getTemplate() { return template; }
    public void setTemplate(BizTemplate template) { this.template = template; }

    public BizTemplateItem getDefaultItem() { return defaultItem; }
    public void setDefaultItem(BizTemplateItem defaultItem) { this.defaultItem = defaultItem; }

    public BizPriceTemplate getPriceTemplate() { return priceTemplate; }
    public void setPriceTemplate(BizPriceTemplate priceTemplate) { this.priceTemplate = priceTemplate; }

    public List<BizTemplateItem> getAllItems() { return allItems; }
    public void setAllItems(List<BizTemplateItem> allItems) { this.allItems = allItems; }
}
