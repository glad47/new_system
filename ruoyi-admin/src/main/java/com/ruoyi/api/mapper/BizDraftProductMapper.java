package com.ruoyi.api.mapper;

import java.util.List;
import com.ruoyi.api.domain.BizDraftProduct;

public interface BizDraftProductMapper
{
    List<BizDraftProduct> selectBizDraftProductList(BizDraftProduct draft);
    BizDraftProduct selectBizDraftProductById(Long draftId);
    int insertBizDraftProduct(BizDraftProduct draft);
    int updateBizDraftProduct(BizDraftProduct draft);
    int deleteBizDraftProductByIds(Long[] draftIds);
}
