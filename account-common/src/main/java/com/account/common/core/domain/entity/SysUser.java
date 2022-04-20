package com.account.common.core.domain.entity;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户对象 sys_user
 *
 * @author hope
 */
@Data
public class SysUser extends BaseEntity {

    @ApiModelProperty("用户ID")
    private Long userId;

    @ApiModelProperty("工号")
    private String userName;

    @ApiModelProperty("姓名")
    private String nickName;

    @ApiModelProperty("联系方式")
    private String phonenumber;

    @ApiModelProperty("用户性别（0男 1女 2未知）")
    private String sex;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("籍贯")
    private String address;

    @ApiModelProperty(hidden = true)
    private Long roleId;

    @ApiModelProperty("职位")
    private String post;

    @ApiModelProperty("入职日期")
    private Date joinTime;

    @ApiModelProperty("出生年份")
    private String brithday;

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

}
