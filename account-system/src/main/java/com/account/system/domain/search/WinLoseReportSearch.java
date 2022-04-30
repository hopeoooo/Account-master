package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 输赢报表查询条件
 */
@Data
public class WinLoseReportSearch {


    @ApiModelProperty(value ="会员卡号")
    private String card;

    @ApiModelProperty(value = "过滤内部卡号(0:未勾选,1:勾选)")
    private Integer isAdmin;

    @ApiModelProperty(value ="台号")
    private Long tableId;

    @ApiModelProperty(value ="游戏类型")
    private Long gameId;

    @ApiModelProperty(value = "币种类型(0:筹码,1:现金)")
    private Integer operationType;

    @ApiModelProperty(value = "包含子卡号(0:未勾选,1:勾选)")
    private Integer cardType;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;
}
