package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 存取码
 */
@Data
public class SysAccessCode extends BaseEntity {

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value ="会员卡号")
    private String card;

    @ApiModelProperty( value ="已存筹码余额")
    private BigDecimal chipBalance;

    @ApiModelProperty(value ="已存现金余额")
    private BigDecimal cashBalance;

    @ApiModelProperty(value = "总余额")
    private BigDecimal totalBalance;
}
