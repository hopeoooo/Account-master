package com.account.system.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 注单补录
 */
@Data
public class BetRepair {

    @ApiModelProperty("游戏类型")
    private Long gameId;

    @ApiModelProperty("标记号")
    private Long version;

    @ApiModelProperty("台号")
    private Long tableId;

    @ApiModelProperty("靴号")
    private Long bootNum;

    @ApiModelProperty("局号")
    private Long gameNum;

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("币种(0 筹码 1现金)")
    private int type;

    @ApiModelProperty("下注金额 eq:{\"1\":200,\"5\":500}")
    private String option;

    @ApiModelProperty("开牌结果")
    private String gameResult;

}
