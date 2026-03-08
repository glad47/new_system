package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BizPriceCheckCarousel;
import com.ruoyi.common.core.domain.AjaxResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * Price Check Carousel Service Interface
 * 
 * @author ruoyi
 * @date 2026-02-15
 */
public interface IBizPriceCheckCarouselService 
{
    /**
     * Query carousel image by ID
     * 
     * @param carouselId Carousel ID
     * @return Carousel image
     */
    public BizPriceCheckCarousel selectBizPriceCheckCarouselByCarouselId(Long carouselId);

    /**
     * Query carousel image list
     * 
     * @param bizPriceCheckCarousel Carousel image
     * @return Carousel image collection
     */
    public List<BizPriceCheckCarousel> selectBizPriceCheckCarouselList(BizPriceCheckCarousel bizPriceCheckCarousel);

    /**
     * Add carousel image
     * 
     * @param bizPriceCheckCarousel Carousel image
     * @return Result
     */
    public int insertBizPriceCheckCarousel(BizPriceCheckCarousel bizPriceCheckCarousel);

    /**
     * Update carousel image
     * 
     * @param bizPriceCheckCarousel Carousel image
     * @return Result
     */
    public int updateBizPriceCheckCarousel(BizPriceCheckCarousel bizPriceCheckCarousel);

    /**
     * Delete carousel images by IDs
     * 
     * @param carouselIds IDs to delete
     * @return Result
     */
    public int deleteBizPriceCheckCarouselByCarouselIds(Long[] carouselIds);

    /**
     * Delete carousel image by ID
     * 
     * @param carouselId Carousel ID
     * @return Result
     */
    public int deleteBizPriceCheckCarouselByCarouselId(Long carouselId);

    /**
     * Upload carousel image file
     * 
     * @param file Image file
     * @param imageName Image display name
     * @param displayOrder Display order
     * @param displayDuration Display duration in milliseconds
     * @return Upload result
     */
    public AjaxResult uploadCarouselImage(MultipartFile file, String imageName, Integer displayOrder, Integer displayDuration);
}
