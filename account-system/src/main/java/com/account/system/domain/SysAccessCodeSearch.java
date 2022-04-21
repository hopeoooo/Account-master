package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 存取码查询条件
 */
@Data
public class SysAccessCodeSearch{


    @ApiModelProperty(value = "会员账号")
    private String userName;

    @ApiModelProperty(value = "过滤内部卡号(0:未勾选,1:勾选)")
    private Integer isAdmin;

}
