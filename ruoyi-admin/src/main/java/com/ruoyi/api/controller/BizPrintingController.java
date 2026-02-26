package com.ruoyi.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ruoyi.api.domain.dto.PrintLabelDTO;
import com.ruoyi.api.service.IBizPrintingService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * Printing API Controller
 *
 * Provides endpoints for external printing systems to look up a product
 * by barcode and receive all template/label data needed to print.
 *
 * Endpoints:
 *   GET /printing/lookup/{barcode}                — uses system-default template
 *   GET /printing/lookup/{barcode}?templateId=101  — uses a specific template
 *   GET /printing/mockProducts                     — lists available mock barcodes
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/printing")
public class BizPrintingController extends BaseController
{
    @Autowired
    private IBizPrintingService bizPrintingService;

    /**
     * GET /printing/lookup/{barcode}
     *
     * Look up a product by barcode and return everything needed to print a label:
     *   - Product details (name, prices, barcode, quantity, perCustomer)
     *   - Default template with all template items
     *   - The default template-item (image URL, annotations, font sizes, colors)
     *   - The price template (physical label dimensions in mm)
     *
     * Optional query param:
     *   ?templateId=101  — use a specific template instead of the system default
     *
     * Response shape (AjaxResult.data):
     * {
     *   "barcode": "6901234567890",
     *   "productName": "Premium Coffee Beans",
     *   "productNameAr": "حبوب قهوة ممتازة",
     *   "priceBefore": 89.99,
     *   "priceAfter": 59.99,
     *   "quantity": 500,
     *   "perCustomer": 3,
     *   "template": {
     *     "templateId": 101,
     *     "templateName": "national day",
     *     "isDefault": "1",
     *     "templateItems": [ ... ]
     *   },
     *   "defaultItem": {
     *     "templateItemId": 125,
     *     "imageUrl": "http://...",
     *     "imageAnnotations": "{\"productName\":{...},\"priceAfter\":{...},...}",
     *     "fontProductName": 30,
     *     "fontBarcode": null,
     *     "colorProductName": "#ffffff",
     *     "colorPriceAfter": "#22aa44",
     *     "priceAfterIntegerFontSize": 120,
     *     "priceAfterDecimalFontSize": 40,
     *     "priceTemplate": { "priceTemplateId": 1, "size": "S", "widthMm": 110, "heightMm": 55 }
     *   },
     *   "priceTemplate": { "priceTemplateId": 1, "size": "S", "widthMm": 110, "heightMm": 55 },
     *   "allItems": [ ... ]
     * }
     */
//    @PreAuthorize("@ss.hasPermi('biz:template:query')")
    @GetMapping("/lookup/{barcode}")
    public AjaxResult lookup(
        @PathVariable("barcode") String barcode,
        @RequestParam(value = "templateId", required = false) Long templateId)
    {
        PrintLabelDTO result;

        if (templateId != null)
        {
            result = bizPrintingService.lookupByBarcodeWithTemplate(barcode, templateId);
        }
        else
        {
            result = bizPrintingService.lookupByBarcode(barcode);
        }

        if (result == null)
        {
            return error("Product not found for barcode: " + barcode);
        }

        return success(result);
    }

    /**
     * GET /printing/mockProducts
     *
     * Lists all available mock barcodes for testing.
     * External systems can use these to test the lookup endpoint.
     */
//    @PreAuthorize("@ss.hasPermi('biz:template:query')")
    @GetMapping("/mockProducts")
    public AjaxResult mockProducts()
    {
        // Return a simple list of available mock barcodes for testing
        String[][] products = {
            {"6901234567890", "Premium Coffee Beans",       "حبوب قهوة ممتازة"},
            {"6902345678901", "Organic Green Tea",           "شاي أخضر عضوي"},
            {"6903456789012", "Natural Honey 500g",          "عسل طبيعي 500غ"},
            {"6904567890123", "Extra Virgin Olive Oil 1L",   "زيت زيتون بكر ممتاز 1 لتر"},
            {"6905678901234", "Organic Dates 1kg",           "تمور عضوية 1 كيلو"},
        };

        java.util.List<java.util.Map<String, String>> list = new java.util.ArrayList<>();
        for (String[] p : products)
        {
            java.util.Map<String, String> m = new java.util.LinkedHashMap<>();
            m.put("barcode", p[0]);
            m.put("productName", p[1]);
            m.put("productNameAr", p[2]);
            list.add(m);
        }

        return success(list);
    }
}
