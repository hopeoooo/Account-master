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

    //$筹码、$现金、฿筹码、฿现金、$筹码+$现金、฿筹码+฿现金
    @ApiModelProperty("币种(1美金筹码 2美元现金 3泰铢筹码 4泰铢现金 5美金筹码加现金 6泰铢筹码加现金)")
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

    @ApiModelProperty(hidden = true)
    private Long version;

    public BetSearch() {
    }

    public BetSearch(Long tableId, Long gameId, Long bootNum,Long version) {
        this.tableId = tableId;
        this.gameId = gameId;
        this.bootNum = bootNum;
        this.version = version;
    }
}
