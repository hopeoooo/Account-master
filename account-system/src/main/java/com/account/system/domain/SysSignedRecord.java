package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 签单
 */
@Data
public class SysSignedRecord extends BaseEntity {

    @ApiModelProperty(value ="id")
    private Long id;


    @ApiModelProperty(value ="会员卡号")
    private String card;

    @ApiModelProperty( value ="签单额-美金")
    private BigDecimal signedAmount;

    @ApiModelProperty( value ="签单额-泰铢")
    private BigDecimal signedAmountTh;
}
