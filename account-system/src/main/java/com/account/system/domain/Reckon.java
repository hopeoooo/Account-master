package com.account.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hope
 * @since 2022/4/26
 */
@Data
public class Reckon {

    @ApiModelProperty("筹码数")
    private BigDecimal chip;

    @ApiModelProperty("筹码增")
    private BigDecimal chipAdd;

    @ApiModelProperty("筹码减")
    private BigDecimal chipSub;

    @ApiModelProperty("现金数")
    private BigDecimal cash;

    @ApiModelProperty("现金增")
    private BigDecimal cashAdd;

    @ApiModelProperty("现金减")
    private BigDecimal cashSub;

    @ApiModelProperty("保险筹码数")
    private BigDecimal insurance;

    @ApiModelProperty("保险增")
    private BigDecimal insuranceAdd;

    @ApiModelProperty("保险减")
    private BigDecimal insuranceSub;

    @ApiModelProperty("筹码数 泰铢")
    private BigDecimal chipTh;

    @ApiModelProperty("筹码增 泰铢")
    private BigDecimal chipAddTh;

    @ApiModelProperty("筹码减 泰铢")
    private BigDecimal chipSubTh;

    @ApiModelProperty("现金数 泰铢")
    private BigDecimal cashTh;

    @ApiModelProperty("现金增 泰铢")
    private BigDecimal cashAddTh;

    @ApiModelProperty("现金减 泰铢")
    private BigDecimal cashSubTh;

    @ApiModelProperty("保险筹码数 泰铢")
    private BigDecimal insuranceTh;

    @ApiModelProperty("保险增 泰铢")
    private BigDecimal insuranceAddTh;

    @ApiModelProperty("保险减 泰铢")
    private BigDecimal insuranceSubTh;

    @ApiModelProperty("类型 0 点码 1 收码")
    private int type;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("明细")
    private String info;

    @ApiModelProperty("明细 泰铢")
    private String infoTh;
}
