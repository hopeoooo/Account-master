package com.account.system.domain.search;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 洗码费结算查询条件
 */
@Data
public class SysWaterListSearch {


    @ApiModelProperty(value = "(0:筹码,1:现金)")
    private Integer operationType;


    @ApiModelProperty(value ="数据")
    private List<SysWaterSearch> list;


}
