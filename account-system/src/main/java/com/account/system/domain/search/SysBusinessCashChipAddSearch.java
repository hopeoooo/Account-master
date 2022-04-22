package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 买码换现条件
 */
@Data
public class SysBusinessCashChipAddSearch {

    @ApiModelProperty(value = "(5:买码,6:换现)")
    private Integer mark;

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value = "是否换现  0否 1是", required = true)
    private Integer isCash;

    @ApiModelProperty(value ="用户Id")
    private Long userId;

    @ApiModelProperty(value = "已存筹码余额")
    private BigDecimal chipAmount;

    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;


    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;
}
