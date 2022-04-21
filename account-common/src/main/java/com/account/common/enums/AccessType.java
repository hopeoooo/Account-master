package com.account.common.enums;


public enum AccessType
{
    STORAGE_CODE(1, "存码"),
    CODE_FETCHING(2, "取码");

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
