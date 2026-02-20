package com.ruoyi.api.service;

import java.util.List;
import com.ruoyi.api.domain.BizTemplate;

/**
 * Template Service Interface
 *
 * @author ruoyi
 */
public interface IBizTemplateService
{
    public BizTemplate selectBizTemplateById(Long templateId);

    public BizTemplate selectBizTemplateByIdWithItems(Long templateId);

    public List<BizTemplate> selectBizTemplateList(BizTemplate bizTemplate);

    public List<BizTemplate> selectBizTemplateListWithItems(BizTemplate bizTemplate);

    /** Get the current system-default template */
    public BizTemplate selectDefaultTemplate();

    /**
     * Set a template as the system default.
     * Atomically clears the old default and sets the new one.
     */
    public void setDefaultTemplate(Long templateId);

    public int insertBizTemplate(BizTemplate bizTemplate);

    public int updateBizTemplate(BizTemplate bizTemplate);

    public int deleteBizTemplateById(Long templateId);

    public int deleteBizTemplateByIds(Long[] templateIds);

    public boolean checkTemplateNameUnique(BizTemplate bizTemplate);
}