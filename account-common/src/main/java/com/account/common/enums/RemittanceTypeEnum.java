package com.account.common.enums;

/**
 * 汇款类型枚举
 */
public enum RemittanceTypeEnum {

    TRANSFER_IN(11, "汇入"),
    TRANSFER_OUT(12, "汇出");

    private int value;
    private String display;


    RemittanceTypeEnum(int value, String display) {
        this.value = value;
        this.display = display;
    }

    public static RemittanceTypeEnum getByValue(int value){
        for(RemittanceTypeEnum type : RemittanceTypeEnum.values()){
            if(value == type.getValue()){
                return type;
            }
        }

        return null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }
}
