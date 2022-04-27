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

    @ApiModelProperty(value ="ip")
    private String ip;

    @ApiModelProperty(value ="桌台Id")
    private Long tableId;

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

    @ApiModelProperty(hidden = true)
    private Long version;

    @ApiModelProperty(hidden = true)
    private Long bootNum;

    @ApiModelProperty(hidden = true)
    private Long gameNum;

    @ApiModelProperty(hidden = true)
    private BigDecimal chip;

    @ApiModelProperty(hidden = true)
    private BigDecimal cash;

    @ApiModelProperty(hidden = true)
    private BigDecimal insurance;

    @ApiModelProperty(hidden = true)
    private BigDecimal chipAdd;

    @ApiModelProperty(hidden = true)
    private BigDecimal insuranceAdd;
}
