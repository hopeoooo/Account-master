package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 买码换现条件
 */
@Data
public class SysBusinessCashChipAddSearch {

    @ApiModelProperty(value = "(13:买码,7:换现)")
    private Integer mark;

    @ApiModelProperty(value ="会员卡号")
    private String card;

    @ApiModelProperty(value = "已存筹码余额")
    private BigDecimal chipAmount=BigDecimal.ZERO;

    @ApiModelProperty(value = "已存筹码余额-泰铢")
    private BigDecimal chipAmountTh=BigDecimal.ZERO;

    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
}
