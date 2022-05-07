package com.account.system.domain.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 存取码 :存码、取码条件
 */
@Data
public class SysAccessCodeAddSearch{


    @ApiModelProperty(value = "(1:存码,2:取码)")
    private Integer mark;

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value ="卡号")
    private  String card;

    @ApiModelProperty( value ="筹码")
    private BigDecimal chipAmount;

    @ApiModelProperty(value ="现金")
    private BigDecimal cashAmount;


    @ApiModelProperty( value ="筹码-泰铢")
    private BigDecimal chipAmountTh;

    @ApiModelProperty(value ="现金-泰铢")
    private BigDecimal cashAmountTh;


    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("更新时间")
    private Date updateTime;

}
