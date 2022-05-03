package com.account.system.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 员工录入错帐报表
 */
@Data
public class SysInputErrorVo {

    @ApiModelProperty("工号")
    private String userName;

    @ApiModelProperty("姓名")
    private String nickName;

    @ApiModelProperty(value = "录入次数")
    private int input;

    @ApiModelProperty(value = "错误次数")
    private int error;

    @ApiModelProperty(value = "错误率")
    private String errorRate;

}
