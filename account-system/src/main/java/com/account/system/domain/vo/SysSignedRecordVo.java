package com.account.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 签单
 */
@Data
public class SysSignedRecordVo {

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("状态 0正常 1停用")
    private Integer status;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty( value ="签单额")
    private BigDecimal signedAmount;

    @ApiModelProperty( value ="签单额-泰铢")
    private BigDecimal signedAmountTh;

    @ApiModelProperty(value = "备注")
    private String remark;

}
