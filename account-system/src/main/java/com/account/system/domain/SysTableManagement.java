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

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "桌台Id")
    private Long tableId;

    @ApiModelProperty(value = "游戏Id")
    private Long gameId;

    @ApiModelProperty(value = "游戏名称")
    private String gameName;

    @ApiModelProperty(value = "筹码点码基数-美金")
    private BigDecimal chipPointBase;

    @ApiModelProperty(value = "现金点码基数-美金")
    private BigDecimal cashPointBase;

    @ApiModelProperty(value = "保险筹码点码基数-美金")
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
    private BigDecimal cashAdd;

    @ApiModelProperty(hidden = true)
    private BigDecimal insuranceAdd;

    @ApiModelProperty(hidden = true)
    private BigDecimal isDelete;



    @ApiModelProperty(value = "筹码点码基数-泰铢")
    private BigDecimal chipPointBaseTh;

    @ApiModelProperty(value = "现金点码基数-泰铢")
    private BigDecimal cashPointBaseTh;

    @ApiModelProperty(value = "保险筹码点码基数-泰铢")
    private BigDecimal insurancePointBaseTh;

    @ApiModelProperty(hidden = true)
    private BigDecimal chipTh;

    @ApiModelProperty(hidden = true)
    private BigDecimal cashTh;

    @ApiModelProperty(hidden = true)
    private BigDecimal insuranceTh;

    @ApiModelProperty(hidden = true)
    private BigDecimal chipAddTh;

    @ApiModelProperty(hidden = true)
    private BigDecimal cashAddTh;

    @ApiModelProperty(hidden = true)
    private BigDecimal insuranceAddTh;



    public SysTableManagement() {
    }

    public SysTableManagement(Long tableId, BigDecimal chip, BigDecimal cash, BigDecimal insurance) {
        this.tableId = tableId;
        this.chip = chip;
        this.cash = cash;
        this.insurance = insurance;
    }

    public SysTableManagement(Long tableId, BigDecimal chip, BigDecimal cash, BigDecimal insurance
            , BigDecimal chipTh, BigDecimal cashTh, BigDecimal insuranceTh) {
        this.tableId = tableId;
        this.chip = chip;
        this.cash = cash;
        this.insurance = insurance;
        this.chipTh = chipTh;
        this.cashTh = cashTh;
        this.insuranceTh = insuranceTh;
    }

    public SysTableManagement(Long tableId, Long bootNum, BigDecimal chipAdd, BigDecimal cashAdd, BigDecimal insuranceAdd
            , BigDecimal chipAddTh, BigDecimal cashAddTh, BigDecimal insuranceAddTh) {
        this.tableId = tableId;
        this.bootNum = bootNum;
        this.chipAdd = chipAdd;
        this.cashAdd = cashAdd;
        this.insuranceAdd = insuranceAdd;
        this.chipAddTh = chipAddTh;
        this.cashAddTh = cashAddTh;
        this.insuranceAddTh = insuranceAddTh;
    }
}
