package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 员工录入错账报表报表查询条件
 */
@Data
public class InputErrorSearch {


    @ApiModelProperty(value ="员工工号")
    private String userName;

    @ApiModelProperty(value = "开始时间", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", required = false)
    private String endTime;
}
