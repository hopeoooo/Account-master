package com.account.system.domain.vo;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 存取码
 */
@Data
public class SysAccessCodeVo  {

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String userName;
    @ApiModelProperty("状态 0正常 1停用")
    private Integer status;

    @ApiModelProperty( value ="已存筹码余额-美金")
    private BigDecimal chipBalance;

    @ApiModelProperty(value ="已存现金余额-美金")
    private BigDecimal cashBalance;

    @ApiModelProperty( value ="已存筹码余额-泰铢")
    private BigDecimal chipBalanceTh;

    @ApiModelProperty(value ="已存现金余额-泰铢")
    private BigDecimal cashBalanceTh;


    @ApiModelProperty( value ="签单额")
    private BigDecimal signedAmount;

    @ApiModelProperty(value = "备注")
    private String remark;

}
