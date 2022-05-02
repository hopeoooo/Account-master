package com.account.system.domain;

import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author hope
 * @since 2022/5/2
 */
@Data
public class SysBetUpdateRecord {

    private Integer id;

    private Long gameId;

    private String card;

    private String tableId;

    private String boot;

    private String game;

    private String option;

    private String type;

    private String amount;

    private String result;

    private String win;

    private String createBy;

    private String betTime;

    private Date updateTime;

    public SysBetUpdateRecord() {
    }

    public SysBetUpdateRecord(Long gameId, String card, String tableId, String boot, String game,
                              String option, String type, String amount, String result, String win,
                              String createBy, String betTime) {
        this.gameId = gameId;
        this.card = card;
        this.tableId = tableId;
        this.boot = boot;
        this.game = game;
        this.option = option;
        this.type = type;
        this.amount = amount;
        this.result = result;
        this.win = win;
        this.createBy = createBy;
        this.betTime = betTime;
    }

    public SysBetUpdateRecord(SysBet sysBet) {
        this.gameId = sysBet.getGameId();
        this.card = sysBet.getCard();
        this.tableId = String.valueOf(sysBet.getTableId());
        this.boot = String.valueOf(sysBet.getBootNum());
        this.game = String.valueOf(sysBet.getGameNum());
        this.type = String.valueOf(sysBet.getType());
        this.betTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(sysBet.getCreateTime());
        this.createBy = sysBet.getCreateBy();
    }
}
