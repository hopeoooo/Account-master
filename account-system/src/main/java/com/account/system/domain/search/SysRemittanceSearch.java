package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 汇款条件
 */
@Data
public class SysRemittanceSearch {

    @ApiModelProperty(value = "(11:汇入,12:汇出)")
    private Integer type;

    @ApiModelProperty(value = "会员账卡号")
    private String card;

    @ApiModelProperty(value = "余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "(0:筹码,1:现金)")
    private Integer operationType;

    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
}
