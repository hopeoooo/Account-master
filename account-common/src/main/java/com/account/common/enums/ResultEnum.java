package com.account.common.enums;


import lombok.Getter;
import lombok.Setter;

public enum ResultEnum {


    // 1：闲 4：庄 7：和
    // 2：庄闲对 5：闲对 8：庄对
    // 9：大 6：小

    //闲
    RESULT_BJL_16("16", "闲小"),
    RESULT_BJL_19("19", "闲大"),

    RESULT_BJL_126("126", "闲庄闲对小"),
    RESULT_BJL_129("129", "闲庄闲对大"),

    RESULT_BJL_156("156", "闲闲对小"),
    RESULT_BJL_159("159", "闲闲对大"),

    RESULT_BJL_186("186", "闲庄对小"),
    RESULT_BJL_189("189", "闲庄对大"),

    //庄
    RESULT_BJL_46("46", "庄小"),
    RESULT_BJL_49("49", "庄大"),

    RESULT_BJL_426("426", "庄庄闲对小"),
    RESULT_BJL_429("429", "庄庄闲对大"),

    RESULT_BJL_456("456", "庄闲对小"),
    RESULT_BJL_459("459", "庄闲对大"),

    RESULT_BJL_486("486", "庄庄对小"),
    RESULT_BJL_489("489", "庄庄对大"),

    //和
    RESULT_BJL_76("76", "和小"),
    RESULT_BJL_79("79", "和大"),

    RESULT_BJL_726("726", "和庄闲对小"),
    RESULT_BJL_729("729", "和庄闲对大"),

    RESULT_BJL_756("756", "和闲对小"),
    RESULT_BJL_759("759", "和闲对大"),

    RESULT_BJL_786("786", "和庄对小"),
    RESULT_BJL_789("789", "和庄对大"),

    //闲
    RESULT_BJL_1("1", "闲"),

    RESULT_BJL_12("12", "闲庄闲对"),

    RESULT_BJL_15("15", "闲闲对"),

    RESULT_BJL_18("18", "闲庄对"),

    //庄
    RESULT_BJL_4("4", "庄"),

    RESULT_BJL_42("42", "庄庄闲对"),

    RESULT_BJL_45("45", "庄闲对"),

    RESULT_BJL_48("48", "庄庄对"),

    //和
    RESULT_BJL_7("7", "和"),

    RESULT_BJL_72("72", "和庄闲对"),

    RESULT_BJL_75("75", "和闲对"),

    RESULT_BJL_78("78", "和庄对");





    @Getter
    @Setter
    private String code;
    @Getter
    @Setter
    private String info;

    // 普通方法
    public static String getResultEnum(String code) {
        for (ResultEnum r : ResultEnum.values()) {
            if (r.getCode().equals(code)) {
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
