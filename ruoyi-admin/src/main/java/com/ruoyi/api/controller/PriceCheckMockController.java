package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * Mock Product API Controller for Price Check Kiosk
 * This is a mock API for demonstration purposes
 * 
 * @author ruoyi
 * @date 2026-02-15
 */
@RestController
@RequestMapping("/api/pricecheck")
public class PriceCheckMockController extends BaseController
{
    // Mock product database
    private static final Map<String, ProductInfo> MOCK_PRODUCTS = new HashMap<>();
    
    static {
        // Initialize mock products
        MOCK_PRODUCTS.put("1234567890123", new ProductInfo(
            "1234567890123",
            "Organic Fresh Milk 1L",
            new BigDecimal("12.50"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/milk.jpg",
            "Dairy",
            "Fresh organic milk from local farms",
            150
        ));
        
        MOCK_PRODUCTS.put("2345678901234", new ProductInfo(
            "2345678901234",
            "Whole Wheat Bread",
            new BigDecimal("8.99"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/bread.jpg",
            "Bakery",
            "Freshly baked whole wheat bread",
            80
        ));
        
        MOCK_PRODUCTS.put("3456789012345", new ProductInfo(
            "3456789012345",
            "Red Apples 1kg",
            new BigDecimal("15.00"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/apples.jpg",
            "Fruits",
            "Crisp and sweet red apples",
            200
        ));
        
        MOCK_PRODUCTS.put("4567890123456", new ProductInfo(
            "4567890123456",
            "Chicken Breast 500g",
            new BigDecimal("25.00"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/chicken.jpg",
            "Meat",
            "Fresh chicken breast",
            60
        ));
        
        MOCK_PRODUCTS.put("5678901234567", new ProductInfo(
            "5678901234567",
            "Orange Juice 1L",
            new BigDecimal("18.50"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/juice.jpg",
            "Beverages",
            "100% pure orange juice",
            120
        ));
        
        MOCK_PRODUCTS.put("6789012345678", new ProductInfo(
            "6789012345678",
            "Basmati Rice 5kg",
            new BigDecimal("45.00"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/rice.jpg",
            "Grains",
            "Premium basmati rice",
            90
        ));
        
        MOCK_PRODUCTS.put("7890123456789", new ProductInfo(
            "7890123456789",
            "Extra Virgin Olive Oil 500ml",
            new BigDecimal("35.00"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/oil.jpg",
            "Cooking",
            "Cold pressed olive oil",
            70
        ));
        
        MOCK_PRODUCTS.put("8901234567890", new ProductInfo(
            "8901234567890",
            "Fresh Eggs 12 pack",
            new BigDecimal("22.00"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/eggs.jpg",
            "Dairy",
            "Free range eggs",
            100
        ));
        
        MOCK_PRODUCTS.put("9012345678901", new ProductInfo(
            "9012345678901",
            "Tomatoes 1kg",
            new BigDecimal("12.00"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/tomatoes.jpg",
            "Vegetables",
            "Fresh ripe tomatoes",
            180
        ));
        
        MOCK_PRODUCTS.put("0123456789012", new ProductInfo(
            "0123456789012",
            "Cheddar Cheese 200g",
            new BigDecimal("28.00"),
            "SAR",
            "http://localhost:9000/ruoyi-bucket/products/cheese.jpg",
            "Dairy",
            "Aged cheddar cheese",
            85
        ));
    }

    /**
     * Get product by barcode
     * No authentication required for kiosk
     */
    @GetMapping("/product/{barcode}")
    public AjaxResult getProductByBarcode(@PathVariable String barcode)
    {
        ProductInfo product = MOCK_PRODUCTS.get(barcode);
        
        if (product != null) {
            return AjaxResult.success(product);
        } else {
            return AjaxResult.error("Product not found for barcode: " + barcode);
        }
    }

    /**
     * Search products by name
     */
    @GetMapping("/search")
    public AjaxResult searchProducts(@RequestParam(required = false) String query)
    {
        if (query == null || query.trim().isEmpty()) {
            return AjaxResult.success(new ArrayList<>(MOCK_PRODUCTS.values()));
        }
        
        String searchTerm = query.toLowerCase();
        List<ProductInfo> results = new ArrayList<>();
        
        for (ProductInfo product : MOCK_PRODUCTS.values()) {
            if (product.getProductName().toLowerCase().contains(searchTerm) ||
                product.getCategory().toLowerCase().contains(searchTerm)) {
                results.add(product);
            }
        }
        
        return AjaxResult.success(results);
    }

    /**
     * Get all products
     */
    @GetMapping("/products")
    public AjaxResult getAllProducts()
    {
        return AjaxResult.success(new ArrayList<>(MOCK_PRODUCTS.values()));
    }

    /**
     * Product Info Inner Class
     */
    public static class ProductInfo {
        private String barcode;
        private String productName;
        private BigDecimal price;
        private String currency;
        private String imageUrl;
        private String category;
        private String description;
        private Integer stockQuantity;

        public ProductInfo(String barcode, String productName, BigDecimal price, 
                          String currency, String imageUrl, String category, 
                          String description, Integer stockQuantity) {
            this.barcode = barcode;
            this.productName = productName;
            this.price = price;
            this.currency = currency;
            this.imageUrl = imageUrl;
            this.category = category;
            this.description = description;
            this.stockQuantity = stockQuantity;
        }

        // Getters and Setters
        public String getBarcode() { return barcode; }
        public void setBarcode(String barcode) { this.barcode = barcode; }
        
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        
        public String getCurrency() { return currency; }
        public void setCurrency(String currency) { this.currency = currency; }
        
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public Integer getStockQuantity() { return stockQuantity; }
        public void setStockQuantity(Integer stockQuantity) { this.stockQuantity = stockQuantity; }
    }
}
