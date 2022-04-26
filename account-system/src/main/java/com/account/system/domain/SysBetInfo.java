package com.account.system.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author hope
 * @since 2022/4/22
 */
@Data
public class SysBetInfo extends SysBet{

    private String betOption;

    private BigDecimal betMoney;

    private BigDecimal winLose;

    private BigDecimal water;

    private BigDecimal waterAmount;

    private BigDecimal tie;

    private BigDecimal pump;

    public SysBetInfo() {
    }
}
