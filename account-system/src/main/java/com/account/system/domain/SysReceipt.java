package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hope
 * @since 2022/4/22
 */
@Data
public class SysReceipt extends BaseEntity {

    @ApiModelProperty("id")
    private int id;

    @ApiModelProperty("台号")
    private Long tableId;

    @ApiModelProperty("标记号")
    private Long version;

    @ApiModelProperty("筹码-收")
    private BigDecimal chip;

    @ApiModelProperty("现金-收")
    private BigDecimal cash;

    @ApiModelProperty("保险筹码-收")
    private BigDecimal insurance;

    @ApiModelProperty("筹码增减")
    private BigDecimal chipAdd;

    @ApiModelProperty("现金增减")
    private BigDecimal cashAdd;

    @ApiModelProperty("保险增减")
    private BigDecimal insuranceAdd;

    @ApiModelProperty("洗码量")
    private BigDecimal water;

    @ApiModelProperty("输赢")
    private BigDecimal win;

    @ApiModelProperty("保险输赢")
    private BigDecimal insuranceWin;

    @ApiModelProperty("筹码-收 泰铢")
    private BigDecimal chipTh;

    @ApiModelProperty("现金-收 泰铢")
    private BigDecimal cashTh;

    @ApiModelProperty("保险筹码-收 泰铢")
    private BigDecimal insuranceTh;

    @ApiModelProperty("筹码增减 泰铢")
    private BigDecimal chipAddTh;

    @ApiModelProperty("现金增减 泰铢")
    private BigDecimal cashAddTh;

    @ApiModelProperty("保险增减 泰铢")
    private BigDecimal insuranceAddTh;

    @ApiModelProperty("洗码量 泰铢")
    private BigDecimal waterTh;

    @ApiModelProperty("输赢 泰铢")
    private BigDecimal winTh;

    @ApiModelProperty("保险输赢 泰铢")
    private BigDecimal insuranceWinTh;
}
