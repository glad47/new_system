package com.ruoyi.api.service;

import com.ruoyi.api.domain.dto.PrintLabelDTO;

/**
 * Printing Service Interface
 *
 * Handles barcode lookup + default template resolution for external printing systems.
 *
 * @author ruoyi
 */
public interface IBizPrintingService
{
    /**
     * Look up a product by barcode and resolve the default template context.
     *
     * Flow:
     *   1. Look up product by barcode (mock data for now, future: ERP/POS API)
     *   2. Load the system-default template (biz_template.is_default = '1')
     *   3. Load all template items with their price template relations
     *   4. Find the default template-item (biz_template_item.is_default = '1')
     *   5. Bundle everything into a PrintLabelDTO
     *
     * @param barcode the product barcode
     * @return PrintLabelDTO with product + template + items + price template,
     *         or null if product not found
     */
    PrintLabelDTO lookupByBarcode(String barcode);

    /**
     * Look up a product by barcode using a specific template ID
     * (instead of the system default).
     *
     * @param barcode    the product barcode
     * @param templateId the template to use
     * @return PrintLabelDTO or null if product not found
     */
    PrintLabelDTO lookupByBarcodeWithTemplate(String barcode, Long templateId);
}
