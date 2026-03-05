package com.ruoyi.api.service;

import java.util.List;
import com.ruoyi.api.domain.BizDraftProduct;

public interface IBizDraftProductService
{
    List<BizDraftProduct> selectList(BizDraftProduct draft);
    BizDraftProduct selectById(Long draftId);
    int insert(BizDraftProduct draft);
    int update(BizDraftProduct draft);
    int deleteByIds(Long[] draftIds);

    /** Submit a single draft to D365 — updates status to success/failed */
    BizDraftProduct submitToD365(Long draftId);

    /** Submit all drafts with status='draft' — returns count of successful submissions */
    int submitAllDrafts();
}
