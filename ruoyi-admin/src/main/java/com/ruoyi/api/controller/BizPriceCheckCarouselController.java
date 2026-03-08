package com.ruoyi.web.controller.system;

import java.util.List;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.BizPriceCheckCarousel;
import com.ruoyi.system.service.IBizPriceCheckCarouselService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Price Check Carousel Controller
 * 
 * @author ruoyi
 * @date 2026-02-15
 */
@RestController
@RequestMapping("/system/pricecheck/carousel")
public class BizPriceCheckCarouselController extends BaseController
{
    @Autowired
    private IBizPriceCheckCarouselService bizPriceCheckCarouselService;

    /**
     * Query carousel image list
     */
    @PreAuthorize("@ss.hasPermi('system:carousel:list')")
    @GetMapping("/list")
    public TableDataInfo list(BizPriceCheckCarousel bizPriceCheckCarousel)
    {
        startPage();
        List<BizPriceCheckCarousel> list = bizPriceCheckCarouselService.selectBizPriceCheckCarouselList(bizPriceCheckCarousel);
        return getDataTable(list);
    }

    /**
     * Query active carousel images (for kiosk display - no auth required)
     */
    @GetMapping("/active")
    public AjaxResult getActiveCarousel()
    {
        BizPriceCheckCarousel query = new BizPriceCheckCarousel();
        query.setIsActive("1");
        query.setStatus("0");
        List<BizPriceCheckCarousel> list = bizPriceCheckCarouselService.selectBizPriceCheckCarouselList(query);
        return AjaxResult.success(list);
    }

    /**
     * Export carousel image list
     */
    @PreAuthorize("@ss.hasPermi('system:carousel:export')")
    @Log(title = "Price Check Carousel", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BizPriceCheckCarousel bizPriceCheckCarousel)
    {
        List<BizPriceCheckCarousel> list = bizPriceCheckCarouselService.selectBizPriceCheckCarouselList(bizPriceCheckCarousel);
        ExcelUtil<BizPriceCheckCarousel> util = new ExcelUtil<BizPriceCheckCarousel>(BizPriceCheckCarousel.class);
        util.exportExcel(response, list, "Carousel Image Data");
    }

    /**
     * Get carousel image details
     */
    @PreAuthorize("@ss.hasPermi('system:carousel:query')")
    @GetMapping(value = "/{carouselId}")
    public AjaxResult getInfo(@PathVariable("carouselId") Long carouselId)
    {
        return AjaxResult.success(bizPriceCheckCarouselService.selectBizPriceCheckCarouselByCarouselId(carouselId));
    }

    /**
     * Add carousel image
     */
    @PreAuthorize("@ss.hasPermi('system:carousel:add')")
    @Log(title = "Price Check Carousel", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BizPriceCheckCarousel bizPriceCheckCarousel)
    {
        return toAjax(bizPriceCheckCarouselService.insertBizPriceCheckCarousel(bizPriceCheckCarousel));
    }

    /**
     * Upload carousel image
     */
    @PreAuthorize("@ss.hasPermi('system:carousel:add')")
    @Log(title = "Upload Carousel Image", businessType = BusinessType.INSERT)
    @PostMapping("/upload")
    public AjaxResult uploadImage(MultipartFile file, String imageName, Integer displayOrder, Integer displayDuration)
    {
        try {
            return bizPriceCheckCarouselService.uploadCarouselImage(file, imageName, displayOrder, displayDuration);
        } catch (Exception e) {
            return AjaxResult.error("Upload failed: " + e.getMessage());
        }
    }

    /**
     * Modify carousel image
     */
    @PreAuthorize("@ss.hasPermi('system:carousel:edit')")
    @Log(title = "Price Check Carousel", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BizPriceCheckCarousel bizPriceCheckCarousel)
    {
        return toAjax(bizPriceCheckCarouselService.updateBizPriceCheckCarousel(bizPriceCheckCarousel));
    }

    /**
     * Delete carousel image
     */
    @PreAuthorize("@ss.hasPermi('system:carousel:remove')")
    @Log(title = "Price Check Carousel", businessType = BusinessType.DELETE)
	@DeleteMapping("/{carouselIds}")
    public AjaxResult remove(@PathVariable Long[] carouselIds)
    {
        return toAjax(bizPriceCheckCarouselService.deleteBizPriceCheckCarouselByCarouselIds(carouselIds));
    }
}
