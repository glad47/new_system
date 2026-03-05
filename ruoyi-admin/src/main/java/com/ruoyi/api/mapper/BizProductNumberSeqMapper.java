package com.ruoyi.api.mapper;

import java.util.List;
import com.ruoyi.api.domain.BizProductNumberSeq;

public interface BizProductNumberSeqMapper
{
    List<BizProductNumberSeq> selectBizProductNumberSeqList(BizProductNumberSeq seq);
    BizProductNumberSeq selectBizProductNumberSeqById(Long seqId);
    int updateBizProductNumberSeq(BizProductNumberSeq seq);
    int insertBizProductNumberSeq(BizProductNumberSeq seq);
    int deleteBizProductNumberSeqByIds(Long[] seqIds);
    /** Atomically increment and return next value */
    int incrementCurrentValue(Long seqId);
}
