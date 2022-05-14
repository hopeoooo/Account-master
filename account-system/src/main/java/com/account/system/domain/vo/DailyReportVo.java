package com.account.system.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 日报表vo
 */
@Data
public class DailyReportVo {

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty("输赢")
    private BigDecimal winLose=BigDecimal.ZERO;
    @ApiModelProperty("累计输赢")
    private BigDecimal sumWinLose=BigDecimal.ZERO;
    @ApiModelProperty("洗码量")
    private BigDecimal water=BigDecimal.ZERO;
    @ApiModelProperty("累计洗码量")
    private BigDecimal sumWater=BigDecimal.ZERO;


    @ApiModelProperty("输赢-泰铢")
    private BigDecimal winLoseTh=BigDecimal.ZERO;
    @ApiModelProperty("累计输赢-泰铢")
    private BigDecimal sumWinLoseTh=BigDecimal.ZERO;
    @ApiModelProperty("洗码量-泰铢")
    private BigDecimal waterTh=BigDecimal.ZERO;
    @ApiModelProperty("累计洗码量-泰铢")
    private BigDecimal sumWaterTh=BigDecimal.ZERO;

}
