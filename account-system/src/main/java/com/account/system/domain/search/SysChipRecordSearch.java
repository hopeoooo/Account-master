package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 筹码变动明细 查询条件
 */
@Data
public class SysChipRecordSearch {

    @ApiModelProperty(value = "会员卡号")
    private String card;

    @ApiModelProperty(value = "过滤内部卡号(0:未勾选,1:勾选)")
    private Integer isAdmin;

    @ApiModelProperty( value ="操作类型: 1:存码,2:取码,3:下注赢,4:下注输,5:签单,6:还单,7:换现,13:买码,8:注单修改,9:注单补录,10:洗码结算,11:汇入,12:汇出")
    private int type;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;

}
