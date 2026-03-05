package com.ruoyi.api.domain;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import jakarta.validation.constraints.NotBlank;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Draft Product Entity — biz_draft_product
 * Saved locally before submitting to D365
 *
 * مسودة المنتج — تُحفظ محلياً قبل الإرسال إلى D365
 */
public class BizDraftProduct extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long draftId;

    // ── D365 Contract Fields ──

    @Excel(name = "Company")
    private String company;

    @Excel(name = "Product Name")
    @NotBlank(message = "Product name is required")
    private String productName;

    @Excel(name = "Product Number")
    private String productNumber;

    private String searchName;
    private String productType;
    private String productSubType;

    // Groups
    private String itemGroupId;
    private String itemModelGroupId;
    private String storageDimGroupId;
    private String trackingDimGroupId;
    private String buyerGroupId;

    // Units
    private String inventUnitId;
    private String purchUnitId;
    private String salesUnitId;
    private String unitSeqGroupId;

    // Pricing
    private BigDecimal purchasePrice;
    private BigDecimal salePrice;
    private String purchTaxGroupId;
    private String salesTaxGroupId;
    private Integer useLatestPurchPrice;
    private Integer useLatestCostPrice;
    private BigDecimal overDeliveryPct;
    private BigDecimal underDeliveryPct;

    // Vendor
    private String vendorAccount;
    private String vendorItemNumber;
    private String approvedVendorCheckMethod;

    // Other
    private String lifecycleStateId;
    private String retailCategoryId;
    private String financialDimItemGroup;
    private String mobileDeviceDescLine1;
    private String mobileDeviceDescLine2;

    // ── Draft Status Tracking ──

    @Excel(name = "Draft Status")
    private String draftStatus;       // draft / submitted / success / failed

    private Integer submitCount;
    private Date lastSubmitTime;
    private String d365Response;
    private String errorMessage;

    private String status;
    private String delFlag;

    // ── Build D365 contract map for API call ──
    public Map<String, Object> toD365Contract()
    {
        Map<String, Object> c = new LinkedHashMap<>();
        if (company != null)            c.put("company", company);
        if (productName != null)        c.put("productName", productName);
        if (productNumber != null)      c.put("productNumber", productNumber);
        if (searchName != null)         c.put("searchName", searchName);
        if (productType != null)        c.put("productType", productType);
        if (productSubType != null)     c.put("productSubType", productSubType);
        if (itemGroupId != null)        c.put("itemGroupId", itemGroupId);
        if (itemModelGroupId != null)   c.put("itemModelGroupId", itemModelGroupId);
        if (storageDimGroupId != null)  c.put("storageDimGroupId", storageDimGroupId);
        if (trackingDimGroupId != null) c.put("trackingDimGroupId", trackingDimGroupId);
        if (inventUnitId != null)       c.put("inventUnitId", inventUnitId);
        if (purchUnitId != null)        c.put("purchUnitId", purchUnitId);
        if (salesUnitId != null)        c.put("salesUnitId", salesUnitId);
        if (purchTaxGroupId != null)    c.put("purchTaxGroupId", purchTaxGroupId);
        if (salesTaxGroupId != null)    c.put("salesTaxGroupId", salesTaxGroupId);
        if (purchasePrice != null)      c.put("purchasePrice", purchasePrice);
        if (salePrice != null)          c.put("salePrice", salePrice);
        if (useLatestPurchPrice != null) c.put("useLatestPurchPrice", useLatestPurchPrice);
        if (useLatestCostPrice != null)  c.put("useLatestCostPrice", useLatestCostPrice);
        if (lifecycleStateId != null)   c.put("lifecycleStateId", lifecycleStateId);
        if (buyerGroupId != null)       c.put("buyerGroupId", buyerGroupId);
        if (vendorAccount != null)      c.put("vendorAccount", vendorAccount);
        if (vendorItemNumber != null)   c.put("vendorItemNumber", vendorItemNumber);
        if (overDeliveryPct != null)    c.put("overDeliveryPct", overDeliveryPct);
        if (underDeliveryPct != null)   c.put("underDeliveryPct", underDeliveryPct);
        if (retailCategoryId != null)   c.put("retailCategoryId", retailCategoryId);
        if (financialDimItemGroup != null) c.put("financialDimItemGroup", financialDimItemGroup);
        if (unitSeqGroupId != null)     c.put("unitSeqGroupId", unitSeqGroupId);
        if (mobileDeviceDescLine1 != null) c.put("mobileDeviceDescLine1", mobileDeviceDescLine1);
        if (mobileDeviceDescLine2 != null) c.put("mobileDeviceDescLine2", mobileDeviceDescLine2);
        if (approvedVendorCheckMethod != null) c.put("approvedVendorCheckMethod", approvedVendorCheckMethod);
        return c;
    }

    // ── Getters & Setters ──

    public Long getDraftId() { return draftId; }
    public void setDraftId(Long draftId) { this.draftId = draftId; }

    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }

    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductNumber() { return productNumber; }
    public void setProductNumber(String productNumber) { this.productNumber = productNumber; }

    public String getSearchName() { return searchName; }
    public void setSearchName(String searchName) { this.searchName = searchName; }

    public String getProductType() { return productType; }
    public void setProductType(String productType) { this.productType = productType; }

    public String getProductSubType() { return productSubType; }
    public void setProductSubType(String productSubType) { this.productSubType = productSubType; }

    public String getItemGroupId() { return itemGroupId; }
    public void setItemGroupId(String itemGroupId) { this.itemGroupId = itemGroupId; }

    public String getItemModelGroupId() { return itemModelGroupId; }
    public void setItemModelGroupId(String itemModelGroupId) { this.itemModelGroupId = itemModelGroupId; }

    public String getStorageDimGroupId() { return storageDimGroupId; }
    public void setStorageDimGroupId(String storageDimGroupId) { this.storageDimGroupId = storageDimGroupId; }

    public String getTrackingDimGroupId() { return trackingDimGroupId; }
    public void setTrackingDimGroupId(String trackingDimGroupId) { this.trackingDimGroupId = trackingDimGroupId; }

    public String getBuyerGroupId() { return buyerGroupId; }
    public void setBuyerGroupId(String buyerGroupId) { this.buyerGroupId = buyerGroupId; }

    public String getInventUnitId() { return inventUnitId; }
    public void setInventUnitId(String inventUnitId) { this.inventUnitId = inventUnitId; }

    public String getPurchUnitId() { return purchUnitId; }
    public void setPurchUnitId(String purchUnitId) { this.purchUnitId = purchUnitId; }

    public String getSalesUnitId() { return salesUnitId; }
    public void setSalesUnitId(String salesUnitId) { this.salesUnitId = salesUnitId; }

    public String getUnitSeqGroupId() { return unitSeqGroupId; }
    public void setUnitSeqGroupId(String unitSeqGroupId) { this.unitSeqGroupId = unitSeqGroupId; }

    public BigDecimal getPurchasePrice() { return purchasePrice; }
    public void setPurchasePrice(BigDecimal purchasePrice) { this.purchasePrice = purchasePrice; }

    public BigDecimal getSalePrice() { return salePrice; }
    public void setSalePrice(BigDecimal salePrice) { this.salePrice = salePrice; }

    public String getPurchTaxGroupId() { return purchTaxGroupId; }
    public void setPurchTaxGroupId(String purchTaxGroupId) { this.purchTaxGroupId = purchTaxGroupId; }

    public String getSalesTaxGroupId() { return salesTaxGroupId; }
    public void setSalesTaxGroupId(String salesTaxGroupId) { this.salesTaxGroupId = salesTaxGroupId; }

    public Integer getUseLatestPurchPrice() { return useLatestPurchPrice; }
    public void setUseLatestPurchPrice(Integer useLatestPurchPrice) { this.useLatestPurchPrice = useLatestPurchPrice; }

    public Integer getUseLatestCostPrice() { return useLatestCostPrice; }
    public void setUseLatestCostPrice(Integer useLatestCostPrice) { this.useLatestCostPrice = useLatestCostPrice; }

    public BigDecimal getOverDeliveryPct() { return overDeliveryPct; }
    public void setOverDeliveryPct(BigDecimal overDeliveryPct) { this.overDeliveryPct = overDeliveryPct; }

    public BigDecimal getUnderDeliveryPct() { return underDeliveryPct; }
    public void setUnderDeliveryPct(BigDecimal underDeliveryPct) { this.underDeliveryPct = underDeliveryPct; }

    public String getVendorAccount() { return vendorAccount; }
    public void setVendorAccount(String vendorAccount) { this.vendorAccount = vendorAccount; }

    public String getVendorItemNumber() { return vendorItemNumber; }
    public void setVendorItemNumber(String vendorItemNumber) { this.vendorItemNumber = vendorItemNumber; }

    public String getApprovedVendorCheckMethod() { return approvedVendorCheckMethod; }
    public void setApprovedVendorCheckMethod(String approvedVendorCheckMethod) { this.approvedVendorCheckMethod = approvedVendorCheckMethod; }

    public String getLifecycleStateId() { return lifecycleStateId; }
    public void setLifecycleStateId(String lifecycleStateId) { this.lifecycleStateId = lifecycleStateId; }

    public String getRetailCategoryId() { return retailCategoryId; }
    public void setRetailCategoryId(String retailCategoryId) { this.retailCategoryId = retailCategoryId; }

    public String getFinancialDimItemGroup() { return financialDimItemGroup; }
    public void setFinancialDimItemGroup(String financialDimItemGroup) { this.financialDimItemGroup = financialDimItemGroup; }

    public String getMobileDeviceDescLine1() { return mobileDeviceDescLine1; }
    public void setMobileDeviceDescLine1(String mobileDeviceDescLine1) { this.mobileDeviceDescLine1 = mobileDeviceDescLine1; }

    public String getMobileDeviceDescLine2() { return mobileDeviceDescLine2; }
    public void setMobileDeviceDescLine2(String mobileDeviceDescLine2) { this.mobileDeviceDescLine2 = mobileDeviceDescLine2; }

    public String getDraftStatus() { return draftStatus; }
    public void setDraftStatus(String draftStatus) { this.draftStatus = draftStatus; }

    public Integer getSubmitCount() { return submitCount; }
    public void setSubmitCount(Integer submitCount) { this.submitCount = submitCount; }

    public Date getLastSubmitTime() { return lastSubmitTime; }
    public void setLastSubmitTime(Date lastSubmitTime) { this.lastSubmitTime = lastSubmitTime; }

    public String getD365Response() { return d365Response; }
    public void setD365Response(String d365Response) { this.d365Response = d365Response; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDelFlag() { return delFlag; }
    public void setDelFlag(String delFlag) { this.delFlag = delFlag; }
}
