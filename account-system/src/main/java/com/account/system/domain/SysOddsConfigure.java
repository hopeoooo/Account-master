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
    private String baccaratBankerWin;

    @ApiModelProperty( value ="百家乐-闲赢")
    private String baccaratPlayerWin;

    @ApiModelProperty(value ="百家乐-和赢")
    private String baccaratTieWin;

    @ApiModelProperty(value = "百家乐-庄对")
    private String baccaratBankerPair;

    @ApiModelProperty(value ="百家乐-闲对")
    private String baccaratPlayerPair;

    @ApiModelProperty(value = "百家乐-大")
    private String baccaratLarge;

    @ApiModelProperty(value = "百家乐-小")
    private String baccaratSmall;

    @ApiModelProperty(value = "龙虎-龙赢")
    private String dragonWin;

    @ApiModelProperty(value ="龙虎-虎赢")
    private String tigerWin;

    @ApiModelProperty( value ="龙虎-和赢")
    private String tieWin;

    @ApiModelProperty(value = "百家乐洗码比例(筹码)-美金")
    private BigDecimal baccaratRollingRatioChip;
    @ApiModelProperty(value = "百家乐洗码比例(现金)-美金")
    private BigDecimal baccaratRollingRatioCash;

    @ApiModelProperty( value ="龙虎洗码比例(筹码)-美金")
    private BigDecimal dragonTigerRatioChip;
    @ApiModelProperty( value ="龙虎洗码比例(现金)-美金")
    private BigDecimal dragonTigerRatioCash;

    @ApiModelProperty(value = "洗码佣金取整(0:未勾选 、1:已勾选)")
    private int rollingCommissionRounding;
    @ApiModelProperty( value ="庄赢抽水取整(0:未勾选 、1:已勾选)")
    private int bankerWinPumpRounding;


    @ApiModelProperty(value = "百家乐洗码比例(筹码)-泰铢")
    private BigDecimal baccaratRollingRatioChipTh;
    @ApiModelProperty(value = "百家乐洗码比例(现金)-泰铢")
    private BigDecimal baccaratRollingRatioCashTh;

    @ApiModelProperty( value ="龙虎洗码比例(筹码)-泰铢")
    private BigDecimal dragonTigerRatioChipTh;
    @ApiModelProperty( value ="龙虎洗码比例(现金)-泰铢")
    private BigDecimal dragonTigerRatioCashTh;

    @ApiModelProperty(value = "百家乐-幸运6（2张牌）")
    private String baccaratTwo;

    @ApiModelProperty(value = "百家乐-幸运6（2张牌）")
    private String baccaratThere;

}
