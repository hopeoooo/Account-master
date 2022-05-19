package com.account.common.enums;


import lombok.Getter;
import lombok.Setter;

public enum ResultEnum {


    // 1：闲 4：庄 7：和
    // 5：闲对 8：庄对
    // 9：大 6：小

    //闲
    RESULT_BJL_16("16", "闲 小"),
    RESULT_BJL_19("19", "闲 大"),

    RESULT_BJL_1586("1586", "闲 庄对闲对 小"),
    RESULT_BJL_1589("1589", "闲 庄对闲对 大"),

    RESULT_BJL_1856("1856", "闲 闲对庄对 小"),
    RESULT_BJL_1859("1859", "闲 闲对庄对 大"),

    RESULT_BJL_156("156", "闲 闲对 小"),
    RESULT_BJL_159("159", "闲 闲对 大"),

    RESULT_BJL_186("186", "闲 庄对 小"),
    RESULT_BJL_189("189", "闲 庄对 大"),

    //庄
    RESULT_BJL_46("46", "庄 小"),
    RESULT_BJL_49("49", "庄 大"),

    RESULT_BJL_4586("4586", "庄 庄对闲对 小"),
    RESULT_BJL_4589("4589", "庄 庄对闲对 大"),

    RESULT_BJL_4856("4856", "庄 闲对庄对 小"),
    RESULT_BJL_4859("4859", "庄 闲对庄对 大"),

    RESULT_BJL_456("456", "庄 闲对 小"),
    RESULT_BJL_459("459", "庄 闲对 大"),

    RESULT_BJL_486("486", "庄 庄对 小"),
    RESULT_BJL_489("489", "庄 庄对 大"),

    //和
    RESULT_BJL_76("76", "和 小"),
    RESULT_BJL_79("79", "和 大"),

    RESULT_BJL_7586("7586", "和 庄对闲对 小"),
    RESULT_BJL_7589("7589", "和 庄对闲对 大"),

    RESULT_BJL_7856("7856", "和 闲对庄对 小"),
    RESULT_BJL_7859("7859", "和 闲对庄对 大"),

    RESULT_BJL_756("756", "和 闲对 小"),
    RESULT_BJL_759("759", "和 闲对 大"),

    RESULT_BJL_786("786", "和 庄对 小"),
    RESULT_BJL_789("789", "和 庄对 大"),

    //闲
    RESULT_BJL_1("1", "闲"),

    RESULT_BJL_158("158", "闲 庄对闲对"),

    RESULT_BJL_185("185", "闲 闲对庄对"),

    RESULT_BJL_15("15", "闲 闲对"),

    RESULT_BJL_18("18", "闲 庄对"),

    //庄
    RESULT_BJL_4("4", "庄"),

    RESULT_BJL_458("458", "庄 庄对闲对"),

    RESULT_BJL_485("485", "庄 闲对庄对"),

    RESULT_BJL_45("45", "庄 闲对"),

    RESULT_BJL_48("48", "庄 庄对"),

    //和
    RESULT_BJL_7("7", "和"),

    RESULT_BJL_758("758", "和 庄对闲对"),

    RESULT_BJL_785("785", "和 闲对庄对"),

    RESULT_BJL_75("75", "和 闲对"),

    RESULT_BJL_78("78", "和 庄对");





    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String info;

    // 普通方法
    public static String getResultEnum(String code) {
        for (ResultEnum r : ResultEnum.values()) {
            if (r.getCode().equals(code.replaceAll("a","").replaceAll("b",""))) {
                return r.info;
            }
        }
        return null;
    }

    ResultEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }


}
