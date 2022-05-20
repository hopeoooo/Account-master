package com.account.common.enums;

/**
 * @author hope
 * @since 2022/5/20
 */
public enum LanguageMagEnum {

    SUCCESS("操作成功", "success"),
    SEARCH_SUCCESS("查询成功", "success");

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
        return "";
    }

    public String getZh() {
        return zh;
    }

    public String getEn() {
        return en;
    }
}
