package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 会员信息 sys_members
 *
 * @author hope
 */
@Data
public class SysChipRecord extends BaseEntity {

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("变动类型")
    private int type;

    @ApiModelProperty("注单id")
    private Long betId;

    @ApiModelProperty("帐变前")
    private BigDecimal before;

    @ApiModelProperty("帐变金额")
    private BigDecimal change;

    @ApiModelProperty("帐变后")
    private BigDecimal after;

    @ApiModelProperty("帐变前 泰铢")
    private BigDecimal beforeTh;

    @ApiModelProperty("帐变金额 泰铢")
    private BigDecimal changeTh;

    @ApiModelProperty("帐变后 泰铢")
    private BigDecimal afterTh;

    public SysChipRecord() {
    }

    public SysChipRecord(String card, BigDecimal before, BigDecimal change, BigDecimal after
            , BigDecimal beforeTh, BigDecimal changeTh, BigDecimal afterTh, Long betId) {
        this.card = card;
        this.before = before;
        this.change = change;
        this.after = after;
        this.beforeTh = beforeTh;
        this.changeTh = changeTh;
        this.afterTh = afterTh;
        this.betId = betId;
    }
}
