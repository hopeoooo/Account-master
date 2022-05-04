package com.account.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hope
 * @since 2022/4/26
 */
@Data
public class PorintUpdate {

    @ApiModelProperty("id")
    private Long id;

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

    @ApiModelProperty("备注")
    private String remark;

}
