package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 收码报表查询条件
 */
@Data
public class PorintUpdateSearch {


    @ApiModelProperty(value ="台号")
    private Long tableId;

    @ApiModelProperty(value ="靴号")
    private Long bootNum;

    @ApiModelProperty(value ="操作人")
    private String createBy;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;
}
