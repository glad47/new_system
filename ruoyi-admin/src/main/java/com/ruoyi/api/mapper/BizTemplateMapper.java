package com.ruoyi.api.mapper;

import java.util.List;
import com.ruoyi.api.domain.BizTemplate;

/**
 * Template Mapper Interface
 *
 * @author ruoyi
 */
public interface BizTemplateMapper
{
    public BizTemplate selectBizTemplateById(Long templateId);

    public BizTemplate selectBizTemplateByIdWithItems(Long templateId);

    public List<BizTemplate> selectBizTemplateList(BizTemplate bizTemplate);

    public List<BizTemplate> selectBizTemplateListWithItems(BizTemplate bizTemplate);

    /** Get the current system-default template */
    public BizTemplate selectDefaultTemplate();

    /** Step 1 of set-default: clear is_default on all rows */
    public int clearAllTemplateDefaults();

    /** Step 2 of set-default: mark the chosen template */
    public int setTemplateDefaultById(Long templateId);

    public int insertBizTemplate(BizTemplate bizTemplate);

    public int updateBizTemplate(BizTemplate bizTemplate);

    public int deleteBizTemplateById(Long templateId);

    public int deleteBizTemplateByIds(Long[] templateIds);

    public BizTemplate checkTemplateNameUnique(String templateName);
}