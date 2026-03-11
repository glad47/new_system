package com.ruoyi.api.mapper;

import java.util.List;
import org.apache.ibatis.annotations.*;
import com.ruoyi.api.domain.BizTradeAgreementConfig;

/**
 * Trade Agreement Configuration Mapper
 */
@Mapper
public interface BizTradeAgreementConfigMapper {

    @Select("SELECT config_id, config_key, config_value, config_label, config_group, status FROM biz_trade_agreement_config WHERE status = '0' ORDER BY config_group, config_id")
    @Results({
        @Result(property = "configId", column = "config_id"),
        @Result(property = "configKey", column = "config_key"),
        @Result(property = "configValue", column = "config_value"),
        @Result(property = "configLabel", column = "config_label"),
        @Result(property = "configGroup", column = "config_group"),
        @Result(property = "status", column = "status"),
    })
    List<BizTradeAgreementConfig> selectAll();

    @Update("UPDATE biz_trade_agreement_config SET config_value = #{configValue}, update_time = NOW() WHERE config_key = #{configKey}")
    int updateByKey(BizTradeAgreementConfig config);
}
