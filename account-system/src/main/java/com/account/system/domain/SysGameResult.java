package com.account.system.domain;

import com.account.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 游戏赛果
 */
@Data
public class SysGameResult extends BaseEntity {

    @ApiModelProperty(value ="id")
    private Long id;

    @ApiModelProperty(value ="桌台Id")
    private Long tableId;

    @ApiModelProperty(value ="游戏Id")
    private Long gameId;

    @ApiModelProperty(value ="靴号")
    private Long bootNum;

    @ApiModelProperty(value ="局数")
    private Long gameNum;

    @ApiModelProperty(value ="游戏结果")
    private String gameResult;

    public SysGameResult() {
    }

    public SysGameResult(SysTableManagement sysTableManagement) {
        this.gameId = sysTableManagement.getGameId();
        this.tableId = sysTableManagement.getTableId();
        this.bootNum = sysTableManagement.getBootNum();
        this.gameNum = sysTableManagement.getGameNum()+1;
    }
}
