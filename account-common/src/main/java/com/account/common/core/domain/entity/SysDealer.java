package com.account.common.core.domain.entity;

import com.account.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 荷官对象 sys_dealer
 *
 */
@Data
public class SysDealer extends BaseEntity {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("工号")
    private String userName;

    @ApiModelProperty("姓名")
    private String nickName;

    @ApiModelProperty("联系方式")
    private String phonenumber;

    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private int sex;


    @ApiModelProperty("籍贯")
    private String address;


    @ApiModelProperty("入职日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date joinTime;

    @ApiModelProperty("出生年份")
    private String brithday;


}
