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

    @Autowired private BizDraftProductMapper mapper;
    @Autowired private ID365ProxyService d365ProxyService;

    @Override public List<BizDraftProduct> selectList(BizDraftProduct d) { return mapper.selectBizDraftProductList(d); }
    @Override public BizDraftProduct selectById(Long id) { return mapper.selectBizDraftProductById(id); }

    @Override
    public int insert(BizDraftProduct d) {
        d.setDraftStatus("draft");
        d.setSubmitCount(0);
        try { d.setCreateBy(SecurityUtils.getUsername()); } catch (Exception ignored) {}
        return mapper.insertBizDraftProduct(d);
    }

    @Override
    public int update(BizDraftProduct d) {
        try { d.setUpdateBy(SecurityUtils.getUsername()); } catch (Exception ignored) {}
        return mapper.updateBizDraftProduct(d);
    }

    @Override public int deleteByIds(Long[] ids) { return mapper.deleteBizDraftProductByIds(ids); }

    @Override
    public BizDraftProduct submitToD365(Long draftId) {
        BizDraftProduct draft = mapper.selectBizDraftProductById(draftId);
        if (draft == null) throw new RuntimeException("Draft not found: " + draftId);

        Map<String, Object> contract = draft.toD365Contract();
        draft.setDraftStatus("submitted");
        draft.setSubmitCount((draft.getSubmitCount() != null ? draft.getSubmitCount() : 0) + 1);
        draft.setLastSubmitTime(new Date());
        try { draft.setUpdateBy(SecurityUtils.getUsername()); } catch (Exception ignored) {}
        mapper.updateBizDraftProduct(draft);

        try {
            String response = d365ProxyService.createProduct(contract);
            boolean isError = response != null && (response.contains("\"error\"") || response.contains("\"Error\"") || response.contains("\"exception\""));
            if (isError) {
                draft.setDraftStatus("failed");
                draft.setErrorMessage(response.length() > 2000 ? response.substring(0, 2000) : response);
            } else {
                draft.setDraftStatus("success");
                draft.setErrorMessage(null);
            }
            draft.setD365Response(response != null && response.length() > 4000 ? response.substring(0, 4000) : response);
        } catch (Exception e) {
            log.error("D365 submit failed for draft {}", draftId, e);
            draft.setDraftStatus("failed");
            draft.setErrorMessage(e.getMessage() != null && e.getMessage().length() > 2000 ? e.getMessage().substring(0, 2000) : e.getMessage());
        }
        mapper.updateBizDraftProduct(draft);
        return draft;
    }

    @Override
    public int submitAllDrafts() {
        BizDraftProduct q = new BizDraftProduct();
        q.setDraftStatus("draft");
        List<BizDraftProduct> drafts = mapper.selectBizDraftProductList(q);
        int ok = 0;
        for (BizDraftProduct d : drafts) {
            try {
                BizDraftProduct r = submitToD365(d.getDraftId());
                if ("success".equals(r.getDraftStatus())) {
                    mapper.deleteBizDraftProductByIds(new Long[]{d.getDraftId()});
                    ok++;
                }
            } catch (Exception e) {
                log.error("Batch submit fail for draft {}", d.getDraftId(), e);
            }
        }
        return ok;
    }
}
