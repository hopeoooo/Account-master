package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class SysDealerSearch {

    @ApiModelProperty("工号")
    private String userName;

    /**
     * 开始时间
     */
    @ApiModelProperty("开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty("结束时间")
    private String endTime;

}
