package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 注单记录查询条件
 */
@Data
public class BetSearch {


    @ApiModelProperty(value = "会员卡号")
    private String card;

    @ApiModelProperty("台号")
    private Long tableId;

    @ApiModelProperty("游戏类型")
    private Long gameId;

    @ApiModelProperty("币种(0 筹码 1现金)")
    private int type;

    @ApiModelProperty("靴号")
    private Long bootNum;

    @ApiModelProperty("局号")
    private Long gameNum;

    @ApiModelProperty(value = "操作员")
    private String createBy;


    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;
}
