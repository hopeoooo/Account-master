package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 日报表查询条件
 */
@Data
public class ReportSearch {


    @ApiModelProperty(value = "会员卡号")
    private String card;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;
}
