package com.account.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户筹码明细
 */
@Data
public class SysChipRecordVo {


    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty( value ="操作类型")
    private int type;

    @ApiModelProperty(value ="变动前金额-美金")
    private BigDecimal before;

    @ApiModelProperty(value = "变动金额-美金")
    private BigDecimal change;

    @ApiModelProperty(value = "变动后金额-美金")
    private BigDecimal after;


    @ApiModelProperty(value ="变动前金额-泰铢")
    private BigDecimal beforeTh;

    @ApiModelProperty(value = "变动金额-泰铢")
    private BigDecimal changeTh;

    @ApiModelProperty(value = "变动后金额-泰铢")
    private BigDecimal afterTh;


    @ApiModelProperty(value = "备注")
    private String remark;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private Date createTime;

}
