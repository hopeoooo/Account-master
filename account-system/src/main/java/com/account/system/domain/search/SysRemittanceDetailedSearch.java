package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 汇款明细 查询条件
 */
@Data
public class SysRemittanceDetailedSearch {

    @ApiModelProperty(value = "会员账卡号")
    private String card;

    @ApiModelProperty(value = "过滤内部卡号(0:未勾选,1:勾选)")
    private Integer isAdmin;


    @ApiModelProperty(value = "汇款类型(11:汇入,12:汇出)")
    private Integer operationType;


    @ApiModelProperty(value = "货币类型(1:筹码,2:现金)")
    private Integer type;


    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;

}
