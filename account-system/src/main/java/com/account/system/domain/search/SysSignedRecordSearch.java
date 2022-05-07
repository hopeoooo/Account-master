package com.account.system.domain.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 签单查询条件
 */
@Data
public class SysSignedRecordSearch {

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value = "金额")
    private BigDecimal amount=BigDecimal.ZERO;
    @ApiModelProperty(value = "金额-泰铢")
    private BigDecimal amountTh=BigDecimal.ZERO;

    @ApiModelProperty(value ="会员卡号")
    private String card;


    @ApiModelProperty(value = "(5:签单,6:还单)")
    private Integer mark;

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
