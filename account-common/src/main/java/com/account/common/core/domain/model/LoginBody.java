package com.account.common.core.domain.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户登录对象
 *
 * @author hope
 */
@Data
public class LoginBody {
    /**
     * 用户名
     */
    @ApiModelProperty("用户名")
    private String username;

    /**
     * 用户密码
     */
    @ApiModelProperty("用户密码")
    private String password;

}
