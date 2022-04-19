package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class SysOddsConfigure extends BaseEntity {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value ="百家乐-庄赢抽水率")
    private BigDecimal baccaratPump;

    @ApiModelProperty(value = "百家乐-庄赢")
    private BigDecimal baccaratBankerWin;

    @ApiModelProperty( value ="百家乐-闲赢")
    private BigDecimal baccaratPlayerWin;

    @ApiModelProperty(value ="百家乐-和赢")
    private BigDecimal baccaratTieWin;

    @ApiModelProperty(value = "百家乐-庄对")
    private BigDecimal baccaratBankerPair;

    @ApiModelProperty(value ="百家乐-闲对")
    private BigDecimal baccaratPlayerPair;

    @ApiModelProperty(value = "百家乐-大")
    private BigDecimal baccaratLarge;

    @ApiModelProperty(value = "百家乐-小")
    private BigDecimal baccaratSmall;

    @ApiModelProperty(value = "龙虎-龙赢")
    private BigDecimal dragonWin;

    @ApiModelProperty(value ="龙虎-虎赢")
    private BigDecimal tigerWin;

    @ApiModelProperty( value ="龙虎-和赢")
    private BigDecimal tieWin;

    @ApiModelProperty(value = "百家乐洗码比例(筹码)")
    private BigDecimal baccaratRollingRatioChip;
    @ApiModelProperty(value = "百家乐洗码比例(现金)")
    private BigDecimal baccaratRollingRatioCash;

    @ApiModelProperty( value ="龙虎洗码比例(筹码)")
    private BigDecimal dragonTigerRatioChip;
    @ApiModelProperty( value ="龙虎洗码比例(现金)")
    private BigDecimal dragonTigerRatioCash;
    @ApiModelProperty(value = "洗码佣金取整(0:未勾选 、1:已勾选)")
    private BigDecimal rollingCommissionRounding;
    @ApiModelProperty( value ="庄赢抽水取整(0:未勾选 、1:已勾选)")
    private BigDecimal bankerWinPumpRounding;
}
