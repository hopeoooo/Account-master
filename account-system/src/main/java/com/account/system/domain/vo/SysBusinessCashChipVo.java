package com.account.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 买码换现
 */
@Data
public class SysBusinessCashChipVo {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty("是否换现  0否 1是")
    private Integer isCash;

    @ApiModelProperty(value = "已存筹码余额")
    private BigDecimal chipAmount;

    @ApiModelProperty(value = "备注")
    private String remark;

}
