package com.account.common.enums;

/**
 * @author hope
 * @since 2022/5/20
 */
public enum LanguageMagEnum {

    SUCCESS("操作成功", "success"),
    SEARCH_SUCCESS("查询成功", "success"),

    CARD_NUMBER("参数错误,卡号为空!", "Parameter error, card number is empty!"),
    CARD_NUMBER_DOES("当前卡号不存在!", "Current card number does not exist!"),
    DEPOSIT_SUCCESSFUL("存码成功!", "Chip saved successfully!"),
    WITHDRAWAL_SUCCESSFUL("取码成功!", "Chip withdrawal successful!"),
    FAILED_RETRIEVE("取码失败!", "Failed to retrieve chip!"),
    INSUFFICIENT_BALANCE("余额不足!", "Insufficient balance"),
    BUY_CHIP_SUCCESSFUL("买码成功!", "Chip purchased successful!"),
    DEACTIVATED("该卡号已停用!", "The card number has been deactivated!"),
    EXCHANGE("当前用户不可换现!", "Current user cannot exchange cash!"),
    CASH_OUT_SUCCESSFUL("换现成功!", "Cash transfer successful!"),
    CONFIGURATION("请输入正确得配置数据", "Please enter the correct configuration data"),
    REMITTANCE_FAILED("汇入失败!", "Remittance failed!"),
    REMITTANCE_SUCCESSFUL("汇入成功!", "Remittance Successful"),
    REMIT("当前用户不可汇出!", "Current user cannot remit!"),
    OUTWARD_REMITTANCE_SUCCESSFUL("汇出成功!", "Successful remittance!"),
    SIGNING_SUCCESSFUL("签单成功!", "Signing Successful"),
    RETURN_FAILED("还单失败!", "Return order failed!"),
    PLEASE_ENTER_THE_CORRECT_AMOUNT("请输入正确的金额!", "Please enter the correct amount"),
    PAYBACK_SUCCESSFUL("还单成功!", "Payback Successful"),
    TABLE_IP_EXISTS("桌台ip已存在!", "Table ip already exists!"),
    TABLE_NUMBER_EXISTS("桌台编号已存在!", "Table number exists!"),
    PLEASE_SELECT_CURRENCY("请选择结算币种!", "Please select the currency!"),
    MEMBER_SETTLE("该会员不可结算洗码!", "This member is not allowed to settle the rolling!"),
    ROLLING_SETTLEMENT_FAILED("结算洗码失败!", "Rolling settlement failed!"),
    ROLLING_SETTLEMENT_SUCCESSFUL("结算洗码成功!", "Rolling Settlement successful"),
    SETTLEMENT_FAILED("结算失败!", "Settlement failed!"),
    PASSWORD_FAILED("修改密码失败，旧密码错误!", "Password change failed, old password is wrong"),
    PASSWORD_FAILED_NEW("新密码不能与旧密码相同", "The new password cannot be the same as the old password"),
    UPDATE_PASSWORD_ERROR("修改密码异常，请联系管理员", "Password change error, please contact administrator"),
    IMAGES_ERROR("上传图片异常，请联系管理员", "Please contact the administrator if you have any problems uploading images"),
    DELETED_ROLE("角色已分配,不能删除", "Character has been assigned, cannot be deleted"),
    WRONG_IP_ADDRESS("ip地址错误", "Wrong ip address"),
    RESULTS("赛果格式错误", "Wrong format of results"),
    EXIT_SUCCESSFUL("退出成功", "Exit successful"),
    AUTHORIZATION("没有权限，请联系管理员授权", "No permission, please contact administrator for authorization")

    ;


    private final String zh;
    private final String en;

    LanguageMagEnum(String zh, String en) {
        this.zh = zh;
        this.en = en;
    }

    public static String getEnByZh(String zh) {
        for (LanguageMagEnum r : LanguageMagEnum.values()) {
            if (r.getZh().equals(zh)) {
                return r.getEn();
            }
        }
        return zh;
    }

    public String getZh() {
        return zh;
    }

    public String getEn() {
        return en;
    }
}
