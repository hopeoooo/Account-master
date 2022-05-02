package com.account.controller.system;

import com.account.common.constant.CommonConst;
import com.account.common.core.controller.BaseController;
import com.account.common.core.domain.AjaxResult;
import com.account.common.core.page.TableDataInfo;
import com.account.common.utils.SecurityUtils;
import com.account.common.utils.StringUtils;
import com.account.system.domain.SysMembers;
import com.account.system.domain.SysOddsConfigure;
import com.account.system.domain.SysSignedRecord;
import com.account.system.domain.search.SysWaterSearch;
import com.account.system.domain.vo.SysMaterVo;
import com.account.system.service.SysMembersService;
import com.account.system.service.SysMembersWaterService;
import com.account.system.service.SysOddsConfigureService;
import com.account.system.service.SysSignedRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/water")
@Api(tags = "洗码费结算管理")
public class SysWaterController extends BaseController {
    @Autowired
    private SysMembersService membersService;
    @Autowired
    private SysOddsConfigureService oddsConfigureService;
    @Autowired
    private SysMembersWaterService membersWaterService;

    @Autowired
    private SysSignedRecordService signedRecordService;


    @PreAuthorize("@ss.hasPermi('system:water:list')")
    @GetMapping("/list")
    @ApiOperation(value = "查询洗码费结算列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = false),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false),
            @ApiImplicitParam(name = "cardType", value = "包含子卡号(0:未勾选,1:勾选)", required = false)
    })
    public TableDataInfo list(String card, Integer isAdmin, Integer cardType) {
        startPage();
        startOrderBy();
        List<SysMaterVo> list = membersService.selectWaterList(card, isAdmin,cardType);

        SysOddsConfigure OddsConfigureList = oddsConfigureService.selectConfigInfo();
        list.forEach(info ->{
            //洗码佣金取整(0:未勾选 、1:已勾选)
            if (OddsConfigureList.getRollingCommissionRounding() == CommonConst.NUMBER_1){
                BigDecimal actualWaterAmount = info.getWaterAmount().setScale(0, BigDecimal.ROUND_DOWN);
                info.setActualWaterAmount(actualWaterAmount);
            }else {
                info.setActualWaterAmount(info.getWaterAmount());
            }
        });


        return getDataTable(list);
    }
    @PreAuthorize("@ss.hasPermi('system:water:list')")
    @GetMapping("/total")
    @ApiOperation(value = "总计")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "card", value = "会员卡号", required = false),
            @ApiImplicitParam(name = "isAdmin", value = "过滤内部卡号(0:未勾选,1:勾选)", required = false),
            @ApiImplicitParam(name = "cardType", value = "包含子卡号(0:未勾选,1:勾选)", required = false)
    })
    public AjaxResult total(String card, Integer isAdmin, Integer cardType) {
        Map map = membersService.selectWaterTotal(card, isAdmin,cardType);
        return AjaxResult.success(map);
    }



    @PreAuthorize("@ss.hasPermi('system:water:list')")
    @PostMapping("/settlementWater")
    @ApiOperation(value = "结算洗码")
    public AjaxResult settlementWater(@Validated @RequestBody SysWaterSearch waterSearch) {
        if (StringUtils.isNull(waterSearch.getCard())){
            return AjaxResult.error("参数错误,卡号为空!");
        }

        if (StringUtils.isNull(waterSearch.getOperationType())){
            return AjaxResult.error("请选择结算币种!");
        }
        //判断该卡号是否存在
        SysMembers sysMembers = membersService.selectmembersByCard(waterSearch.getCard());
        if (sysMembers==null){
            return AjaxResult.success("当前卡号不存在!");
        }
        if (sysMembers.getStatus()== CommonConst.NUMBER_1){
            return AjaxResult.success("该卡号已停用!");
        }
        if (sysMembers.getIsSettlement()== CommonConst.NUMBER_0){
            return AjaxResult.success("该会员不可结算洗码!");
        }
        //判断会员是否有欠钱
        SysSignedRecord sysSignedRecord = signedRecordService.selectSignedRecordInfo(null, waterSearch.getCard());
        if (sysSignedRecord!=null && sysSignedRecord.getSignedAmount().compareTo(BigDecimal.ZERO)>0){
            return AjaxResult.success("该会员不可结算洗码!");
        }

        Map map = membersWaterService.selectMembersWaterInfo(waterSearch.getCard());
        BigDecimal water = new BigDecimal(map.get("water").toString());
        BigDecimal waterAmount = new BigDecimal(map.get("waterAmount").toString());
        if (water.compareTo(BigDecimal.ZERO)==0 || waterAmount.compareTo(BigDecimal.ZERO)==0){
            return AjaxResult.success("当前会员没有可结算洗码费!");
        }

        if (waterSearch.getWater().compareTo(water)>0  || waterSearch.getWaterAmount().compareTo(waterAmount)>0){
            return AjaxResult.success("结算洗码失败!");
        }
        waterSearch.setUpdateBy(SecurityUtils.getUsername());
        waterSearch.setCreateBy(SecurityUtils.getUsername());
        membersWaterService.updateMembersWater(waterSearch);
        return AjaxResult.success("结算洗码成功!");
    }


    @PreAuthorize("@ss.hasPermi('system:water:list')")
    @PostMapping("/batchSettlementWater")
    @ApiOperation(value = "批量结算洗码")
    public AjaxResult batchSettlementWater(@Validated @RequestBody List<SysWaterSearch> waterSearch) {
        for (int i=0;i< waterSearch.size();i++){
            SysWaterSearch info = waterSearch.get(i);
            //判断该卡号是否存在
            SysMembers sysMembers = membersService.selectmembersByCard(info.getCard());
            if (sysMembers==null){
                return AjaxResult.success("结算失败!");
            }
            if (sysMembers.getStatus()== CommonConst.NUMBER_1){
                return AjaxResult.success("该卡号已停用!");
            }
            if (sysMembers.getIsSettlement()== CommonConst.NUMBER_0){
                return AjaxResult.success("结算失败!");
            }
            //判断会员是否有欠钱
            SysSignedRecord sysSignedRecord = signedRecordService.selectSignedRecordInfo(null, info.getCard());
            if (sysSignedRecord!=null && sysSignedRecord.getSignedAmount().compareTo(BigDecimal.ZERO)>0){
                return AjaxResult.success("结算失败!");
            }

            Map map = membersWaterService.selectMembersWaterInfo(info.getCard());
            BigDecimal water = new BigDecimal(map.get("water").toString());
            BigDecimal waterAmount = new BigDecimal(map.get("waterAmount").toString());
            if (water.compareTo(BigDecimal.ZERO)==0 || waterAmount.compareTo(BigDecimal.ZERO)==0){
                return AjaxResult.success("结算失败!");
            }
            if (info.getWater().compareTo(water)>0  || info.getWaterAmount().compareTo(waterAmount)>0){
                return AjaxResult.success("结算失败!");
            }
            info.setCreateBy(SecurityUtils.getUsername());
            info.setUpdateBy(SecurityUtils.getUsername());
        }
        membersWaterService.updateMembersWaterList(waterSearch);
        return AjaxResult.success("结算洗码成功!");
    }
}
