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
public class SysPorintUpdate extends BaseEntity {

    @ApiModelProperty("id")
    private int id;

    @ApiModelProperty("台号")
    private Long tableId;

    @ApiModelProperty("靴号")
    private Long bootNum;

    @ApiModelProperty("标记号")
    private Long version;

    @ApiModelProperty("系统点码数 现金加筹码")
    private String sysChip;

    @ApiModelProperty("手动点码数  现金加筹码")
    private String personChip;

    @ApiModelProperty("筹码差距")
    private String chipGap;

    @ApiModelProperty("筹码增减")
    private String chipAdd;

    @ApiModelProperty("现金差距")
    private String cashGap;

    @ApiModelProperty("现金增减")
    private String cashAdd;

    @ApiModelProperty("保险系统点码数")
    private String sysInsurance;

    @ApiModelProperty("保险手动点码数")
    private String personInsurance;

    @ApiModelProperty("保险差距")
    private String insuranceGap;

    @ApiModelProperty("保险增减")
    private String insuranceAdd;

    @ApiModelProperty("洗码量")
    private String water;

    @ApiModelProperty("筹码输赢")
    private String chipWin;

    @ApiModelProperty("保险输赢")
    private String insuranceWin;

    public SysPorintUpdate() {

    }

    public SysPorintUpdate(SysPorint oldPorint) {
        this.tableId = oldPorint.getTableId();
        this.version = oldPorint.getVersion();
        this.bootNum = oldPorint.getBootNum();
        this.sysChip = oldPorint.getSysChip().add(oldPorint.getSysCash()).toString();
        this.sysInsurance = oldPorint.getSysInsurance().toString();
        this.water = oldPorint.getWater().toString();
        this.chipWin = oldPorint.getChipWin().toString();
        this.insuranceWin = oldPorint.getInsuranceWin().toString();
    }


}