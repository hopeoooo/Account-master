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

    @ApiModelProperty("卡号")
    private BigDecimal before;

    @ApiModelProperty("卡号")
    private BigDecimal change;

    @ApiModelProperty("卡号")
    private BigDecimal after;

    public SysChipRecord() {
    }

    public SysChipRecord(String card, int type, BigDecimal before, BigDecimal change, BigDecimal after) {
        this.card = card;
        this.type = type;
        this.before = before;
        this.change = change;
        this.after = after;
    }
}
