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
public class SysMembers extends BaseEntity {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("主卡号")
    private String parentCard;

    @ApiModelProperty("卡号")
    private String card;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("电话号码")
    private String phone;

    @ApiModelProperty("状态 0正常 1停用")
    private Integer status;

    @ApiModelProperty("卡类型 0主卡 1字卡")
    private Integer cardType;

    @ApiModelProperty("是否内部账号 0否 1是")
    private Integer isAdmin;

    @ApiModelProperty("性别 0男 1女 2未知")
    private Integer sex;

    @ApiModelProperty("押金")
    private BigDecimal deposit;

    @ApiModelProperty("补卡费")
    private BigDecimal repair;

    @ApiModelProperty("占股比例")
    private Integer shareRatio;

    @ApiModelProperty("返点比例")
    private Integer rebateRatio;

    @ApiModelProperty("是否抽水 0否 1是")
    private Integer isPump;

    @ApiModelProperty("是否换现 0否 1是")
    private Integer isCash;

    @ApiModelProperty("是否可结算洗码 0否 1是")
    private Integer isSettlement;

    @ApiModelProperty("是否可汇出 0否 1是")
    private Integer isOut;

    @ApiModelProperty("是否走账 0否 1是")
    private Integer isBill;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("百家乐洗码比例(筹码)")
    private BigDecimal baccaratRollingRatioChip;

    @ApiModelProperty("百家乐洗码比例(现金)")
    private BigDecimal baccaratRollingRatioCash;

    @ApiModelProperty("龙虎洗码比例(筹码)")
    private BigDecimal dragonTigerRatioChip;

    @ApiModelProperty("龙虎洗码比例(现金)")
    private BigDecimal dragonTigerRatioCash;

    @ApiModelProperty("百家乐洗码比例(筹码) 泰铢")
    private BigDecimal baccaratRollingRatioChipTh;

    @ApiModelProperty("百家乐洗码比例(现金) 泰铢")
    private BigDecimal baccaratRollingRatioCashTh;

    @ApiModelProperty("龙虎洗码比例(筹码) 泰铢")
    private BigDecimal dragonTigerRatioChipTh;

    @ApiModelProperty("龙虎洗码比例(现金) 泰铢")
    private BigDecimal dragonTigerRatioCashTh;

    @ApiModelProperty("用户筹码余额 美元")
    private BigDecimal chip;

    @ApiModelProperty("用户筹码余额 泰铢")
    private BigDecimal chipTh;
}
