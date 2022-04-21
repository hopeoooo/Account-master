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

    @ApiModelProperty( value ="已存筹码余额")
    private BigDecimal chipBalance;

    @ApiModelProperty(value ="已存现金余额")
    private BigDecimal cashBalance;

    @ApiModelProperty(value = "总余额")
    private BigDecimal totalBalance;

    @ApiModelProperty(value = "备注")
    private String remark;

}
