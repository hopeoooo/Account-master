package com.account.common.enums;


public enum AccessType
{
    STORAGE_CODE(1, "存码"),
    CODE_FETCHING(2, "取码"),
    SIGNED(3, "签单"),
    RETURN_ORDER(4, "还单"),
    BUY_CODE(5, "买码"),
    CASH_EXCHANGE(6, "换现"),
    IMPORT(7 ,"汇入"),
    REMIT(8, "汇出"),
    ;

    private final int code;
    private final String info;

    AccessType(int code, String info)
    {
        this.code = code;
        this.info = info;
    }

    public int getCode()
    {
        return code;
    }

    public String getInfo()
    {
        return info;
    }
}
