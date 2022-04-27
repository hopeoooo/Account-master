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
public class SysPorint extends BaseEntity {

    @ApiModelProperty("id")
    private int id;

    @ApiModelProperty("台号")
    private Long tableId;

    @ApiModelProperty("靴号")
    private Long bootNum;

    @ApiModelProperty("系统点码数")
    private BigDecimal sysChip;

    @ApiModelProperty("手动点码数")
    private BigDecimal personChip;

    @ApiModelProperty("筹码差距")
    private BigDecimal chipGap;

    @ApiModelProperty("筹码增减")
    private BigDecimal chipAdd;

    @ApiModelProperty("保险系统点码数")
    private BigDecimal sysInsurance;

    @ApiModelProperty("保险手动点码数")
    private BigDecimal personInsurance;

    @ApiModelProperty("保险差距")
    private BigDecimal insuranceGap;

    @ApiModelProperty("保险增减")
    private BigDecimal insuranceAdd;

    @ApiModelProperty("洗码量")
    private BigDecimal water;

    @ApiModelProperty("筹码输赢")
    private BigDecimal chipWin;

    @ApiModelProperty("保险输赢")
    private BigDecimal insuranceWin;
}
