package com.account.system.domain.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * 存取码查询条件
 */
@Data
public class SysAccessCodeSearch{


    @ApiModelProperty(value = "会员卡号")
    private String card;

    @ApiModelProperty(value = "过滤内部卡号(0:未勾选,1:勾选)")
    private Integer isAdmin;

}
