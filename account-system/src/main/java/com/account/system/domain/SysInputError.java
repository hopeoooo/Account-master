package com.account.system.domain;

/**
 * @author hope
 * @since 2022/4/30
 */
public class SysInputError {

    private String userName;

    private Long input;

    private Long error;

    public SysInputError() {
    }

    public SysInputError(String userName, Long input, Long error) {
        this.userName = userName;
        this.input = input;
        this.error = error;
    }
}
