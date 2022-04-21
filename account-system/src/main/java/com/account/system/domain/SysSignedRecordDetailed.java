package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 签单明细
 */
@Data
public class SysSignedRecordDetailed extends BaseEntity {

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value ="用户Id")
    private Long userId;

    @ApiModelProperty( value ="操作类型: 1:还单 2:签单")
    private int operationType;

    @ApiModelProperty( value ="金额类型: 1:筹码")
    private int amountType;

    @ApiModelProperty(value ="变动前金额")
    private BigDecimal amountBefore;

    @ApiModelProperty(value = "变动金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "变动后金额")
    private BigDecimal amountAfter;


    @ApiModelProperty(value = "备注")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private Date operationTime;
}
