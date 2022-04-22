package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 签单明细 查询条件
 */
@Data
public class SysSignedRecordDetailedSearch {

    @ApiModelProperty(value = "会员账卡号")
    private String card;

    @ApiModelProperty(value = "过滤内部卡号(0:未勾选,1:勾选)")
    private Integer isAdmin;

    @ApiModelProperty( value ="操作类型: 1:签单 2:还单")
    private int operationType;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;

}