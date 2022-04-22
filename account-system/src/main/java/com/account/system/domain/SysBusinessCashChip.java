package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 买码换现
 */
@Data
public class SysBusinessCashChip extends BaseEntity {

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value ="用户Id")
    private Long userId;

    @ApiModelProperty( value ="已存筹码余额")
    private BigDecimal chipAmount;

    @ApiModelProperty(value = "备注")
    private String remark;

}
