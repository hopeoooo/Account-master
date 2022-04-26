package com.account.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 注单记录
 */
@Data
public class BetInfoOptionVo {

    @ApiModelProperty("注单号id")
    private Long betId;

    @ApiModelProperty("玩法id")
    private String betOption;
    @ApiModelProperty("下注金额")
    private BigDecimal betMoney;
}
