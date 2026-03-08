package com.ruoyi.system.service.impl;

import java.util.List;

import com.ruoyi.api.config.MinioConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.system.mapper.BizPriceCheckCarouselMapper;
import com.ruoyi.system.domain.BizPriceCheckCarousel;
import com.ruoyi.system.service.IBizPriceCheckCarouselService;
import com.ruoyi.common.config.RuoYiConfig;
import com.ruoyi.common.utils.file.FileUploadUtils;
import com.ruoyi.common.utils.StringUtils;

/**
 * Price Check Carousel Service Implementation
 * 
 * @author ruoyi
 * @date 2026-02-15
 */
@Service
public class BizPriceCheckCarouselServiceImpl implements IBizPriceCheckCarouselService 
{
    @Autowired
    private BizPriceCheckCarouselMapper bizPriceCheckCarouselMapper;

    /**
     * Query carousel image by ID
     * 
     * @param carouselId Carousel ID
     * @return Carousel image
     */
    @Override
    public BizPriceCheckCarousel selectBizPriceCheckCarouselByCarouselId(Long carouselId)
    {
        return bizPriceCheckCarouselMapper.selectBizPriceCheckCarouselByCarouselId(carouselId);
    }

    /**
     * Query carousel image list
     * 
     * @param bizPriceCheckCarousel Carousel image
     * @return Carousel image collection
     */
    @Override
    public List<BizPriceCheckCarousel> selectBizPriceCheckCarouselList(BizPriceCheckCarousel bizPriceCheckCarousel)
    {
        return bizPriceCheckCarouselMapper.selectBizPriceCheckCarouselList(bizPriceCheckCarousel);
    }

    /**
     * Add carousel image
     * 
     * @param bizPriceCheckCarousel Carousel image
     * @return Result
     */
    @Override
    public int insertBizPriceCheckCarousel(BizPriceCheckCarousel bizPriceCheckCarousel)
    {
        bizPriceCheckCarousel.setCreateBy(SecurityUtils.getUsername());
        bizPriceCheckCarousel.setCreateTime(DateUtils.getNowDate());
        return bizPriceCheckCarouselMapper.insertBizPriceCheckCarousel(bizPriceCheckCarousel);
    }

    /**
     * Update carousel image
     * 
     * @param bizPriceCheckCarousel Carousel image
     * @return Result
     */
    @Override
    public int updateBizPriceCheckCarousel(BizPriceCheckCarousel bizPriceCheckCarousel)
    {
        bizPriceCheckCarousel.setUpdateBy(SecurityUtils.getUsername());
        bizPriceCheckCarousel.setUpdateTime(DateUtils.getNowDate());
        return bizPriceCheckCarouselMapper.updateBizPriceCheckCarousel(bizPriceCheckCarousel);
    }

    /**
     * Delete carousel images by IDs
     * 
     * @param carouselIds IDs to delete
     * @return Result
     */
    @Override
    public int deleteBizPriceCheckCarouselByCarouselIds(Long[] carouselIds)
    {
        return bizPriceCheckCarouselMapper.deleteBizPriceCheckCarouselByCarouselIds(carouselIds);
    }

    /**
     * Delete carousel image by ID
     * 
     * @param carouselId Carousel ID
     * @return Result
     */
    @Override
    public int deleteBizPriceCheckCarouselByCarouselId(Long carouselId)
    {
        return bizPriceCheckCarouselMapper.deleteBizPriceCheckCarouselByCarouselId(carouselId);
    }

    /**
     * Upload carousel image file
     * 
     * @param file Image file
     * @param imageName Image display name
     * @param displayOrder Display order
     * @param displayDuration Display duration in milliseconds
     * @return Upload result
     */
    @Override
    public AjaxResult uploadCarouselImage(MultipartFile file, String imageName, Integer displayOrder, Integer displayDuration)
    {
        try {
            // Validate file
            if (file.isEmpty()) {
                return AjaxResult.error("Upload file cannot be empty");
            }

            // Get file info
            String originalFilename = file.getOriginalFilename();
            String contentType = file.getContentType();
            long fileSize = file.getSize();

            // Upload to MinIO (or your configured file storage)
            // This assumes you have FileUploadUtils configured for MinIO
            String filePath = FileUploadUtils.upload(RuoYiConfig.getProfile() + "/carousel", file);
            
            // Construct full URL (adjust according to your MinIO configuration)
            String baseUrl = MinioConfig.getEndpoint(); // e.g., http://localhost:9000/ruoyi-bucket
            String imageUrl = baseUrl + filePath;

            // Create carousel record
            BizPriceCheckCarousel carousel = new BizPriceCheckCarousel();
            carousel.setImageName(imageName);
            carousel.setImageUrl(imageUrl);
            carousel.setOriginalFilename(originalFilename);
            carousel.setImageType(contentType);
            carousel.setImageSize(fileSize);
            carousel.setDisplayOrder(displayOrder != null ? displayOrder : 1);
            carousel.setDisplayDuration(displayDuration != null ? displayDuration : 5000);
            carousel.setIsActive("1");
            carousel.setStatus("0");
            carousel.setDelFlag("0");
            carousel.setCreateBy(SecurityUtils.getUsername());
            carousel.setCreateTime(DateUtils.getNowDate());

            int result = bizPriceCheckCarouselMapper.insertBizPriceCheckCarousel(carousel);
            
            if (result > 0) {
                return AjaxResult.success("Upload successful", carousel);
            } else {
                return AjaxResult.error("Upload failed");
            }
        } catch (Exception e) {
            return AjaxResult.error("Upload error: " + e.getMessage());
        }
    }
}
