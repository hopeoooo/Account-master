package com.account.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 汇款明细
 */
@Data
public class SysRemittanceDetailedVo {


    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String userName;

    @ApiModelProperty(value = "(11:汇入,12:汇出)")
    private Integer type;

    @ApiModelProperty(value = "(汇入/汇出)")
    private String typeDisplay;

    @ApiModelProperty(value = "(0:筹码,1:现金)")
    private Integer operationType;

    @ApiModelProperty(value = "(筹码/现金)")
    private String operationTypeDisplay;

    @ApiModelProperty(value = "变动金额(美金)")
    private BigDecimal amount;

    @ApiModelProperty(value = "变动金额(泰铢)")
    private BigDecimal amountTh;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "操作时间")
    private Date operationTime;


    @ApiModelProperty(value = "备注")
    private String remark;

    private String createBy;


}
