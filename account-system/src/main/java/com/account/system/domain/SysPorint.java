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
public class SysPorint extends BaseEntity implements Cloneable {

    @ApiModelProperty("id")
    private int id;

    @ApiModelProperty("游戏id")
    private Long gameId;

    @ApiModelProperty("台号")
    private Long tableId;

    @ApiModelProperty("靴号")
    private Long bootNum;

    @ApiModelProperty("标记号")
    private Long version;

    @ApiModelProperty("系统点码数")
    private BigDecimal sysChip;

    @ApiModelProperty("手动点码数")
    private BigDecimal personChip;

    @ApiModelProperty("筹码差距")
    private BigDecimal chipGap;

    @ApiModelProperty("筹码增减")
    private BigDecimal chipAdd;

    @ApiModelProperty("系统现金点码数")
    private BigDecimal sysCash;

    @ApiModelProperty("手动现金点码数")
    private BigDecimal personCash;

    @ApiModelProperty("现金差距")
    private BigDecimal cashGap;

    @ApiModelProperty("现金增减")
    private BigDecimal cashAdd;

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

    @ApiModelProperty("系统点码数 泰铢")
    private BigDecimal sysChipTh;

    @ApiModelProperty("手动点码数 泰铢")
    private BigDecimal personChipTh;

    @ApiModelProperty("筹码差距 泰铢")
    private BigDecimal chipGapTh;

    @ApiModelProperty("筹码增减 泰铢")
    private BigDecimal chipAddTh;

    @ApiModelProperty("系统现金点码数 泰铢")
    private BigDecimal sysCashTh;

    @ApiModelProperty("手动现金点码数 泰铢")
    private BigDecimal personCashTh;

    @ApiModelProperty("现金差距 泰铢")
    private BigDecimal cashGapTh;

    @ApiModelProperty("现金增减 泰铢")
    private BigDecimal cashAddTh;

    @ApiModelProperty("保险系统点码数 泰铢")
    private BigDecimal sysInsuranceTh;

    @ApiModelProperty("保险手动点码数 泰铢")
    private BigDecimal personInsuranceTh;

    @ApiModelProperty("保险差距 泰铢")
    private BigDecimal insuranceGapTh;

    @ApiModelProperty("保险增减 泰铢")
    private BigDecimal insuranceAddTh;

    @ApiModelProperty("洗码量 泰铢")
    private BigDecimal waterTh;

    @ApiModelProperty("筹码输赢 泰铢")
    private BigDecimal chipWinTh;

    @ApiModelProperty("保险输赢 泰铢")
    private BigDecimal insuranceWinTh;

    @ApiModelProperty("明细")
    private String info;

    @ApiModelProperty("明细 泰铢")
    private String infoTh;

    public SysPorint() {

    }

    public SysPorint clone(){
        SysPorint sysPorint = null;
        try {
            sysPorint= (SysPorint) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return sysPorint;
    }
}
