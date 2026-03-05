package com.ruoyi.api.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.api.domain.BizDraftProduct;
import com.ruoyi.api.mapper.BizDraftProductMapper;
import com.ruoyi.api.service.IBizDraftProductService;
import com.ruoyi.api.service.ID365ProxyService;
import com.ruoyi.common.utils.SecurityUtils;

@Service
public class BizDraftProductServiceImpl implements IBizDraftProductService
{
    private static final Logger log = LoggerFactory.getLogger(BizDraftProductServiceImpl.class);

    @Autowired
    private BizDraftProductMapper mapper;

    @Autowired
    private ID365ProxyService d365ProxyService;

    @Override
    public List<BizDraftProduct> selectList(BizDraftProduct draft) {
        return mapper.selectBizDraftProductList(draft);
    }

    @Override
    public BizDraftProduct selectById(Long draftId) {
        return mapper.selectBizDraftProductById(draftId);
    }

    @Override
    public int insert(BizDraftProduct draft) {
        draft.setDraftStatus("draft");
        draft.setSubmitCount(0);
        draft.setCreateBy(SecurityUtils.getUsername());
        return mapper.insertBizDraftProduct(draft);
    }

    @Override
    public int update(BizDraftProduct draft) {
        draft.setUpdateBy(SecurityUtils.getUsername());
        return mapper.updateBizDraftProduct(draft);
    }

    @Override
    public int deleteByIds(Long[] draftIds) {
        return mapper.deleteBizDraftProductByIds(draftIds);
    }

    @Override
    public BizDraftProduct submitToD365(Long draftId)
    {
        BizDraftProduct draft = mapper.selectBizDraftProductById(draftId);
        if (draft == null) {
            throw new RuntimeException("Draft not found: " + draftId);
        }

        // Build D365 contract from draft fields
        Map<String, Object> contract = draft.toD365Contract();

        // Update status to "submitted"
        draft.setDraftStatus("submitted");
        draft.setSubmitCount((draft.getSubmitCount() != null ? draft.getSubmitCount() : 0) + 1);
        draft.setLastSubmitTime(new Date());
        draft.setUpdateBy(SecurityUtils.getUsername());
        mapper.updateBizDraftProduct(draft);

        try
        {
            // Call D365 via backend proxy
            String response = d365ProxyService.createProduct(contract);
            log.info("D365 createProduct response for draft {}: {}", draftId, response);

            // Check response for success/failure
            // D365 typically returns an error message or the product number on success
            boolean isError = response != null && (
                response.contains("\"error\"") ||
                response.contains("\"Error\"") ||
                response.contains("\"exception\"") ||
                response.contains("\"Exception\"")
            );

            if (isError) {
                draft.setDraftStatus("failed");
                draft.setErrorMessage(response != null && response.length() > 2000
                    ? response.substring(0, 2000) : response);
            } else {
                draft.setDraftStatus("success");
                draft.setErrorMessage(null);
            }
            draft.setD365Response(response != null && response.length() > 4000
                ? response.substring(0, 4000) : response);
        }
        catch (Exception e)
        {
            log.error("D365 submit failed for draft {}", draftId, e);
            draft.setDraftStatus("failed");
            draft.setErrorMessage(e.getMessage() != null && e.getMessage().length() > 2000
                ? e.getMessage().substring(0, 2000) : e.getMessage());
        }

        draft.setUpdateBy(SecurityUtils.getUsername());
        mapper.updateBizDraftProduct(draft);
        return draft;
    }

    @Override
    public int submitAllDrafts()
    {
        BizDraftProduct query = new BizDraftProduct();
        query.setDraftStatus("draft");
        List<BizDraftProduct> drafts = mapper.selectBizDraftProductList(query);

        int successCount = 0;
        for (BizDraftProduct draft : drafts) {
            try {
                BizDraftProduct result = submitToD365(draft.getDraftId());
                if ("success".equals(result.getDraftStatus())) {
                    successCount++;
                }
            } catch (Exception e) {
                log.error("Batch submit failed for draft {}", draft.getDraftId(), e);
            }
        }
        return successCount;
    }
}
