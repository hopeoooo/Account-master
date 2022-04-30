package com.account.system.domain.search;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 洗码费结算查询条件
 */
@Data
public class SysWaterSearch {


    @ApiModelProperty(value ="会员卡号")
    private String card;

    @ApiModelProperty("未结算洗码量")
    private BigDecimal water;

    @ApiModelProperty("未结算洗码费")
    private BigDecimal waterAmount;

    @ApiModelProperty("实际结算洗码费")
    private BigDecimal actualWaterAmount;

    @ApiModelProperty(value = "(0:筹码,1:现金)")
    private Integer operationType;

    @ApiModelProperty(value = "备注")
    private String remark;


    /**
     * 创建者
     */
    @ApiModelProperty("创建者")
    private String createBy;

    /**
     * 更新者
     */
    @ApiModelProperty("更新者")
    private String updateBy;

}
