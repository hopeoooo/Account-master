package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 汇款
 */
@Data
public class SysRemittanceDetailed extends BaseEntity {

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value ="会员卡号")
    private String card;

    @ApiModelProperty( value ="类型: 0:筹码,1:现金")
    private Integer operationType;

    @ApiModelProperty( value ="操作类型: 11:汇入 12:汇出")
    private Integer type;

    @ApiModelProperty(value = "变动金额(美金)")
    private BigDecimal amount;

    @ApiModelProperty(value = "变动金额(泰铢)")
    private BigDecimal amountTh;

    @ApiModelProperty(value = "备注")
    private String remark;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private Date operationTime;

}
