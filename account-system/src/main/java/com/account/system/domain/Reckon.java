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

    @ApiModelProperty("现金数")
    private BigDecimal cash;

    @ApiModelProperty("筹码增")
    private BigDecimal chipAdd;

    @ApiModelProperty("筹码减")
    private BigDecimal chipSub;

    @ApiModelProperty("保险筹码数")
    private BigDecimal insurance;

    @ApiModelProperty("保险增")
    private BigDecimal insuranceAdd;

    @ApiModelProperty("保险减")
    private BigDecimal insuranceSub;

    @ApiModelProperty("类型 0 点码 1 收码")
    private int type;

}
