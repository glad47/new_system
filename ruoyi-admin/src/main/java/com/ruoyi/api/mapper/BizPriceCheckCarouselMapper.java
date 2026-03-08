package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.BizPriceCheckCarousel;

/**
 * Price Check Carousel Mapper Interface
 * 
 * @author ruoyi
 * @date 2026-02-15
 */
public interface BizPriceCheckCarouselMapper 
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
     * Delete carousel image by ID
     * 
     * @param carouselId Carousel ID
     * @return Result
     */
    public int deleteBizPriceCheckCarouselByCarouselId(Long carouselId);

    /**
     * Batch delete carousel images
     * 
     * @param carouselIds IDs to delete
     * @return Result
     */
    public int deleteBizPriceCheckCarouselByCarouselIds(Long[] carouselIds);
}
