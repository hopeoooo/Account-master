package com.account.common.enums;

/**
 * 筹码变动 类型
 */
public enum ChipChangeEnum {

    STORE_CHIP(1, "存码"),
    TAKE_CHIP(2, "取码"),
    WIN_CHIP(3, "下注赢"),
    LOSE_CHIP(4, "下注输"),
    BORROW_CHIP(5, "签单"),
    RETURN_CHIP(6, "还单"),
    CASH_OUT_CHIP(7, "换现"),
    BET_EDIT_CHIP(8, "注单修改"),
    BET_RECORD_CHIP(9, "注单补录"),
    SETTLEMENT_CHIP(10, "洗码结算"),
    IMPORT_CHIP(11, "汇入"),
    OUT_EXCHANGE(12, "汇出");

    private final int code;
    private final String info;

    ChipChangeEnum(int code, String info) {
        this.code = code;
        this.info = info;
    }

    public int getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}
