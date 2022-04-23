package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


/**
 * 汇款条件
 */
@Data
public class SysRemittanceSearch {

    @ApiModelProperty(value = "(7:汇入,8:汇出)")
    private Integer operationType;

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value = "余额")
    private BigDecimal amount;

    @ApiModelProperty(value = "(1:筹码,2:现金)")
    private Integer type;

    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;
}
