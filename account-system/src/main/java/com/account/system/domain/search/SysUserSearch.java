package com.account.system.domain.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户对象 sys_user
 *
 * @author hope
 */
@Data
public class SysUserSearch {

    @ApiModelProperty("工号")
    private String userName;

    @ApiModelProperty("姓名")
    private String nickName;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("开始时间")
    private String beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("结束时间")
    private String endTime;

}
