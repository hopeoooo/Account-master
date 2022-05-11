package com.account.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 注单记录
 */
@Data
public class BetInfoVo {

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("注单号id")
    private Long betId;

    @ApiModelProperty("台号")
    private Long tableId;

    @ApiModelProperty("标记号")
    private Long version;

    @ApiModelProperty("靴号")
    private Long bootNum;

    @ApiModelProperty("局号")
    private Long gameNum;

    @ApiModelProperty("游戏类型")
    private Long gameId;

    @ApiModelProperty("下注玩法")
    private List<BetInfoOptionVo> option;

    @ApiModelProperty("币种(0美元筹码 1美元现金 2泰铢筹码 3泰铢现金)")
    private int type;

    @ApiModelProperty(value = "下注金额")
    private BigDecimal betMoney;

    @ApiModelProperty("开牌结果")
    private String gameResult;

    @ApiModelProperty(value = "输赢")
    private BigDecimal winLose;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "下注时间")
    private Date createTime;

    @ApiModelProperty(value = "操作员")
    private String createBy;
}
