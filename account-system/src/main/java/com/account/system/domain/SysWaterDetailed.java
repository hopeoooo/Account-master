package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
public class SysWaterDetailed extends BaseEntity {

    @ApiModelProperty("id")
    private Long id;
;
    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty(value = "类型: 0:筹码,1:现金")
    private Integer operationType;

    @ApiModelProperty(value = "结算洗码量")
    private BigDecimal water;


    @ApiModelProperty(value = "应结算洗码费")
    private BigDecimal waterAmount;


    @ApiModelProperty(value = "实际结算洗码费")
    private BigDecimal actualWaterAmount;


    @ApiModelProperty(value = "结算洗码量-泰铢")
    private BigDecimal waterTh;


    @ApiModelProperty(value = "应结算洗码费-泰铢")
    private BigDecimal waterAmountTh;


    @ApiModelProperty(value = "实际结算洗码费-泰铢")
    private BigDecimal actualWaterAmountTh;

    @ApiModelProperty(value = "备注")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "截至日期")
    private Date deadline;
}
