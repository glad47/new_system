package com.ruoyi.api.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.api.domain.BizProductNumberSeq;
import com.ruoyi.api.mapper.BizProductNumberSeqMapper;
import com.ruoyi.api.service.IBizProductNumberSeqService;

@Service
public class BizProductNumberSeqServiceImpl implements IBizProductNumberSeqService
{
    @Autowired
    private BizProductNumberSeqMapper mapper;

    @Override
    public List<BizProductNumberSeq> selectList(BizProductNumberSeq seq) {
        return mapper.selectBizProductNumberSeqList(seq);
    }

    @Override
    public BizProductNumberSeq selectById(Long seqId) {
        return mapper.selectBizProductNumberSeqById(seqId);
    }

    @Override
    public int update(BizProductNumberSeq seq) {
        return mapper.updateBizProductNumberSeq(seq);
    }

    @Override
    public int insert(BizProductNumberSeq seq) {
        return mapper.insertBizProductNumberSeq(seq);
    }

    @Override
    public int deleteByIds(Long[] seqIds) {
        return mapper.deleteBizProductNumberSeqByIds(seqIds);
    }

    @Override
    @Transactional
    public String getNextProductNumber(Long seqId)
    {
        BizProductNumberSeq seq = mapper.selectBizProductNumberSeqById(seqId);
        if (seq == null) {
            throw new RuntimeException("Sequence not found: " + seqId);
        }
        long nextVal = seq.getCurrentValue() + seq.getIncrementBy();
        mapper.incrementCurrentValue(seqId);
        String formatted = String.valueOf(nextVal);
        if (seq.getPadLength() != null && seq.getPadLength() > 0) {
            formatted = String.format("%0" + seq.getPadLength() + "d", nextVal);
        }
        return seq.getPrefix() + formatted;
    }
}
