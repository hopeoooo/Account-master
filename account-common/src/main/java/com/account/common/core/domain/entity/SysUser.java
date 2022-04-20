package com.account.common.core.domain.entity;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 用户对象 sys_user
 *
 * @author hope
 */
@Data
public class SysUser extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long userId;

    /**
     * 部门ID
     */
    @ApiModelProperty("部门ID")
    private Long deptId;

    /**
     * 用户账号
     */
    @ApiModelProperty("用户账号")
    private String userName;

    /**
     * 用户昵称
     */
    @ApiModelProperty("用户昵称")
    private String nickName;

    /**
     * 用户邮箱
     */
    @ApiModelProperty("用户邮箱")
    private String email;

    /**
     * 手机号码
     */
    @ApiModelProperty("手机号码")
    private String phonenumber;

    /**
     * 用户性别
     */
    @ApiModelProperty("用户性别")
    private String sex;

    /**
     * 用户头像
     */
    @ApiModelProperty(hidden = true)
    private String avatar;

    /**
     * 密码
     */
    @ApiModelProperty(hidden = true)
    private String password;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty("帐号状态（0正常 1停用）")
    private String status;

    /**
     * 删除标志（0代表存在 2代表删除）
     */
    @ApiModelProperty("删除标志（0代表存在 2代表删除）")
    private String delFlag;

    /**
     * 最后登录IP
     */
    @ApiModelProperty(hidden = true)
    private String loginIp;

    /**
     * 最后登录时间
     */
    @ApiModelProperty(hidden = true)
    private Date loginDate;

    /**
     * 部门对象
     */
    @ApiModelProperty(hidden = true)
    private SysDept dept;

    /**
     * 角色对象
     */
    @ApiModelProperty(hidden = true)
    private List<SysRole> roles;

    /**
     * 角色组
     */
    @ApiModelProperty(hidden = true)
    private Long[] roleIds;

    /**
     * 岗位组
     */
    @ApiModelProperty(hidden = true)
    private Long[] postIds;

    /**
     * 角色ID
     */
    @ApiModelProperty(hidden = true)
    private Long roleId;

    public SysUser() {

    }

    public SysUser(Long userId) {
        this.userId = userId;
    }

    public boolean isAdmin() {
        return isAdmin(this.userId);
    }

    public static boolean isAdmin(Long userId) {
        return userId != null && 1L == userId;
    }

}
