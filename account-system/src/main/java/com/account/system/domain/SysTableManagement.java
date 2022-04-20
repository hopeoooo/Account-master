package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 桌台
 */
@Data
public class SysTableManagement extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value ="游戏Id")
    private Long gameId;

    @ApiModelProperty(value = "游戏名称")
    private String gameName;

    @ApiModelProperty( value ="筹码点码基数")
    private BigDecimal chipPointBase;

    @ApiModelProperty(value ="现金点码基数")
    private BigDecimal cashPointBase;

    @ApiModelProperty(value = "保险筹码点码基数")
    private BigDecimal insurancePointBase;

}
