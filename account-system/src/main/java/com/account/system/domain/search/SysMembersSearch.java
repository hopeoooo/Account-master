package com.account.system.domain.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会员信息 sys_members
 *
 * @author hope
 */
@Data
public class SysMembersSearch{

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("状态 0正常 1停用")
    private Integer status;

    @ApiModelProperty("是否包含子卡 0不包含 1包含")
    private Integer isChild;

    /**
     * 开始时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("开始时间")
    private Date beginTime;

    /**
     * 结束时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("结束时间")
    private Date endTime;
}
