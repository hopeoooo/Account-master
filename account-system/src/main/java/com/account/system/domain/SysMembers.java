package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员信息 sys_members
 *
 * @author hope
 */
@Data
public class SysMembers extends BaseEntity {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("状态 0正常 1停用")
    private int status;

    @ApiModelProperty("卡类型 0主卡 1字卡")
    private int cardType;

    @ApiModelProperty("是否内部账号 0否 1是")
    private int isAdmin;

    @ApiModelProperty("性别 0男 1女 2未知")
    private int sex;
}
