package com.account.system.domain.search;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 洗码费结算明细查询条件
 */
@Data
public class SysWaterDetailedSearch {


    @ApiModelProperty(value ="会员卡号")
    private String card;

    @ApiModelProperty(value = "过滤内部卡号(0:未勾选,1:勾选)")
    private Integer isAdmin;

    @ApiModelProperty(value = "包含子卡号(0:未勾选,1:勾选)")
    private Integer cardType;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;

}
