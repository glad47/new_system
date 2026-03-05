package com.ruoyi.api.service;

import java.util.List;
import com.ruoyi.api.domain.BizProductNumberSeq;

public interface IBizProductNumberSeqService
{
    List<BizProductNumberSeq> selectList(BizProductNumberSeq seq);
    BizProductNumberSeq selectById(Long seqId);
    int update(BizProductNumberSeq seq);
    int insert(BizProductNumberSeq seq);
    int deleteByIds(Long[] seqIds);
    /** Get next product number and increment the sequence */
    String getNextProductNumber(Long seqId);
}
