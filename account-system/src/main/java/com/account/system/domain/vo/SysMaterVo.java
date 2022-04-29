package com.account.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 洗码
 */
@Data
public class SysMaterVo {

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("是否可结算洗码 0否 1是")
    private Integer isSettlement;

    @ApiModelProperty("未结算洗码量")
    private BigDecimal water;

    @ApiModelProperty("未结算洗码费")
    private BigDecimal waterAmount;

    @ApiModelProperty("实际结算洗码费")
    private BigDecimal actualWaterAmount;

    @ApiModelProperty("签单金额")
    private BigDecimal signedAmount;

    @ApiModelProperty(value = "备注")
    private String remark;

}
