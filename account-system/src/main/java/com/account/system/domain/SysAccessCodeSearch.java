package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 存取码
 */
@Data
public class SysAccessCodeSearch extends BaseEntity{


    @ApiModelProperty(value = "会员账号")
    private String userName;

    @ApiModelProperty(value = "过滤内部卡号(0:未勾选,1:勾选)")
    private Integer isAdmin;



    @ApiModelProperty(value = "(1:存码,2:取码)")
    private Integer mark;

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value ="用户Id")
    private Long userId;

    @ApiModelProperty( value ="已存筹码余额")
    private BigDecimal chipBalance;

    @ApiModelProperty(value ="已存现金余额")
    private BigDecimal cashBalance;

    @ApiModelProperty(value = "总余额")
    private BigDecimal totalBalance;

    @ApiModelProperty(value = "备注")
    private String remark;

}
