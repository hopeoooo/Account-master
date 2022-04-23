package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 签单明细 查询条件
 */
@Data
public class SysSignedRecordDetailedSearch {

    @ApiModelProperty(value = "会员卡号")
    private String card;

    @ApiModelProperty(value = "过滤内部卡号(0:未勾选,1:勾选)")
    private Integer isAdmin;

    @ApiModelProperty( value ="操作类型: 5:签单 6:还单")
    private int type;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;

}
