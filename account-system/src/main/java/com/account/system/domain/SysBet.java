package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import lombok.Data;

/**
 * @author hope
 * @since 2022/4/22
 */
@Data
public class SysBet extends BaseEntity {

    private Long betId;

    private String card;

    private Long tableId;

    private Long version;

    private Long bootNum;

    private Long gameNum;

    // 0筹码美元 1现金美元 2筹码泰铢 3现金泰铢 4 保险美元 5保险泰铢
    private Integer type;

    private Long gameId;

    private String gameResult;

    private String dealer;

    public SysBet() {
    }

    public SysBet(Long tableId, Long bootNum, Long gameNum, String gameResult) {
        this.tableId = tableId;
        this.bootNum = bootNum;
        this.gameNum = gameNum;
        this.gameResult = gameResult;
    }

    public SysBet(Long tableId, Long bootNum, Long gameNum, String gameResult,Long version) {
        this.tableId = tableId;
        this.bootNum = bootNum;
        this.gameNum = gameNum;
        this.gameResult = gameResult;
        this.version = version;
    }
}
