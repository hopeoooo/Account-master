package com.account.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.math.BigDecimal;
import java.util.Date;

/**
 * 存取码明细
 */
@Data
public class SysAccessCodeDetailedVo {

    @ApiModelProperty(value ="id")
    private Long id;
    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty( value ="操作类型: 1:存码 2:取码")
    private int type;

    @ApiModelProperty(value ="筹码变动前金额")
    private BigDecimal chipAmountBefore;

    @ApiModelProperty(value = "筹码变动金额")
    private BigDecimal chipAmount;

    @ApiModelProperty(value = "筹码变动后金额")
    private BigDecimal chipAmountAfter;


    @ApiModelProperty(value ="现金变动前金额")
    private BigDecimal cashAmountBefore;

    @ApiModelProperty(value = "现金变动金额")
    private BigDecimal cashAmount;

    @ApiModelProperty(value = "现金变动后金额")
    private BigDecimal cashAmountAfter;


    @ApiModelProperty(value = "备注")
    private String remark;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private Date operationTime;

}
