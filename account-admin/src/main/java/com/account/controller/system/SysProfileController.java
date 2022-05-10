package com.account.controller.system;

import com.account.common.config.AccountConfig;
import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.domain.model.LoginUser;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.file.FileUploadUtils;
import com.account.framework.web.service.TokenService;
import com.account.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 个人信息 业务处理
 *
 * @author hope
 */
@RestController
@RequestMapping("/system/user/profile")
@Api(tags = "个人信息")
public class SysProfileController extends BaseController {
    @Autowired
    private SysUserService userService;

    @Autowired
    private TokenService tokenService;

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:profile')")
    @PutMapping("/updatePwd")
    @ApiOperation(value = "修改密码")
    public AjaxResult updatePwd(String oldPassword, String newPassword) {
        LoginUser loginUser = getLoginUser();
        String userName = loginUser.getUsername();
        String password = loginUser.getPassword();
        if (!SecurityUtils.matchesPassword(oldPassword, password)) {
            return AjaxResult.error("修改密码失败，旧密码错误");
        }
        if (SecurityUtils.matchesPassword(newPassword, password)) {
            return AjaxResult.error("新密码不能与旧密码相同");
        }
        if (userService.resetUserPwd(userName, SecurityUtils.encryptPassword(newPassword)) > 0) {
            // 更新缓存用户密码
            loginUser.getUser().setPassword(SecurityUtils.encryptPassword(newPassword));
            tokenService.setLoginUser(loginUser);
            return AjaxResult.success();
        }
        return AjaxResult.error("修改密码异常，请联系管理员");
    }

    /**
     * 首页 个人中心
     */
    @GetMapping("/info")
    @ApiOperation(value = "首页 个人中心")
    public AjaxResult profile() {
        return AjaxResult.success(userService.selectUserByUserName(SecurityUtils.getUsername()));
    }

    /**
     * 头像上传
     */
    @PostMapping("/avatar")
    @ApiOperation(value = "头像上传")
    public AjaxResult avatar(@RequestParam("avatarfile") MultipartFile file) throws IOException {
        if (!file.isEmpty()) {
            LoginUser loginUser = getLoginUser();
            String avatar = FileUploadUtils.upload(AccountConfig.getAvatarPath(), file);
            if (userService.updateUserAvatar(loginUser.getUsername(), avatar)) {
                AjaxResult ajax = AjaxResult.success();
                ajax.put("imgUrl", avatar);
                // 更新缓存用户头像
                tokenService.setLoginUser(loginUser);
                return ajax;
            }
        }
        return AjaxResult.error("上传图片异常，请联系管理员");
    }
}
