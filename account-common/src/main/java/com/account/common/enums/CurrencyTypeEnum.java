package com.account.common.enums;

/**
 * 货币类型美剧
 */
public enum CurrencyTypeEnum {

    CHIP(0, "筹码"),
    CASH(1, "现金");

    private int value;
    private String display;

    private CurrencyTypeEnum(int value,String display){
        this.value = value;
        this.display = display;
    }

    public static CurrencyTypeEnum getByValue(int value){
        for(CurrencyTypeEnum type : CurrencyTypeEnum.values()){
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
    }}
