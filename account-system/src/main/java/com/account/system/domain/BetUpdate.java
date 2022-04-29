package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 注单修改
 */
@Data
public class BetUpdate extends BaseEntity {

    @ApiModelProperty("游戏类型")
    private Long gameId;

    @ApiModelProperty("注单id")
    private Long betId;

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("币种(0 筹码 1现金)")
    private int type;

    @ApiModelProperty("下注金额 eq:{\"1\":200,\"5\":500}")
    private String option;

    @ApiModelProperty("开牌结果")
    private String gameResult;

}
