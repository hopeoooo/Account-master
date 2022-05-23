package com.account.system.service.impl;

import com.account.system.domain.BetUpdate;
import com.account.system.domain.SysBet;
import com.account.system.domain.SysBetInfo;
import com.account.system.domain.SysBetUpdateRecord;
import com.account.system.domain.search.BetSearch;
import com.account.system.mapper.BetMapper;
import com.account.system.mapper.SysBetUpdateRecordMapper;
import com.account.system.service.SysBetUpdateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author hope
 * @since 2022/4/22
 */
@Service
public class SysBetUpdateRecordServiceImpl implements SysBetUpdateRecordService {

    @Autowired
    SysBetUpdateRecordMapper sysBetUpdateRecordMapper;

    @Autowired
    BetMapper betMapper;

    /**
     * 修改赛果
     */
    public void saveResultRecord(SysBet sysBet, String gameResult, Long betId, List<SysBetInfo> sysBetInfos,BigDecimal win) {
        if(!sysBet.getBetId().equals(betId)){
            SysBetUpdateRecord sysBetUpdateRecord = new SysBetUpdateRecord(sysBet);
            sysBetUpdateRecord.setType(changeType(sysBet.getType()));
            StringBuffer option = new StringBuffer("");
            final BigDecimal[] amount = {BigDecimal.ZERO};
            final BigDecimal[] oldWin = {BigDecimal.ZERO};
            sysBetInfos.forEach(sysBetInfo -> {
                if(option.length()>0) option.append("/");
                amount[0] = amount[0].add(sysBetInfo.getBetMoney());
                option.append(changeOption(sysBetInfo.getBetOption()))
                        .append(":").append(sysBetInfo.getBetMoney().setScale(2));
                oldWin[0] = oldWin[0].add(sysBetInfo.getWinLose());
            });
            sysBetUpdateRecord.setWin(oldWin[0].setScale(2).toString());
            if(oldWin[0].compareTo(win)!=0)sysBetUpdateRecord.setWin(sysBetUpdateRecord.getWin()+"->"+win.setScale(2).toString());
            sysBetUpdateRecord.setOption(option.toString());
            sysBetUpdateRecord.setAmount(amount[0].setScale(2).toString());
            sysBetUpdateRecord.setResult(changeRelust(gameResult)+"->"+changeRelust(sysBet.getGameResult()));
            sysBetUpdateRecordMapper.saveUpdateRecord(sysBetUpdateRecord);
        }
    }

    /**
     * 修改注单
     */
    public void saveBetRecord(BetUpdate betUpdate, List<SysBetInfo> oldBetInfos, List<SysBetInfo> newBetInfos,String gameResult) {
        SysBet sysBet = betMapper.getBet(betUpdate.getBetId());
        SysBetUpdateRecord sysBetUpdateRecord = new SysBetUpdateRecord(sysBet);
        //卡号
        sysBetUpdateRecord.setCard(compare(sysBet.getCard(),betUpdate.getCard()));
        //下注类型
        sysBetUpdateRecord.setType(compare(changeType(sysBet.getType()),changeType(betUpdate.getType())));
        //玩法
        StringBuffer oldOption = new StringBuffer("");
        final BigDecimal[] oldAmount = {BigDecimal.ZERO};
        final BigDecimal[] oldWin = {BigDecimal.ZERO};
        oldBetInfos.forEach(sysBetInfo -> {
            if(oldOption.length()>0) oldOption.append("/");
            oldAmount[0] = oldAmount[0].add(sysBetInfo.getBetMoney());
            oldOption.append(changeOption(sysBetInfo.getBetOption()))
                    .append(":").append(sysBetInfo.getBetMoney().setScale(2));
            oldWin[0] = oldWin[0].add(sysBetInfo.getWinLose());
        });
        StringBuffer newOption = new StringBuffer("");
        final BigDecimal[] newAmount = {BigDecimal.ZERO};
        final BigDecimal[] newWin = {BigDecimal.ZERO};
        newBetInfos.forEach(sysBetInfo -> {
            if(newOption.length()>0) newOption.append("/");
            newAmount[0] = newAmount[0].add(sysBetInfo.getBetMoney());
            newOption.append(changeOption(sysBetInfo.getBetOption()))
                    .append(":").append(sysBetInfo.getBetMoney().setScale(2));
            newWin[0] = newWin[0].add(sysBetInfo.getWinLose());
        });

        sysBetUpdateRecord.setOption(compare(oldOption.toString(),newOption.toString()));
        //金额
        sysBetUpdateRecord.setAmount(compare(oldAmount[0].setScale(2),newAmount[0].setScale(2)));
        //输赢
        sysBetUpdateRecord.setWin(compare(oldWin[0].setScale(2),newWin[0].setScale(2)));
        //操作员
        String oldCreateBy = sysBet.getUpdateBy()==null?sysBet.getCreateBy():sysBet.getUpdateBy();
        sysBetUpdateRecord.setCreateBy(compare(oldCreateBy,betUpdate.getUpdateBy()));
        //结果
        sysBetUpdateRecord.setResult(compare(changeRelust(gameResult),changeRelust(betUpdate.getGameResult())));
        sysBetUpdateRecordMapper.saveUpdateRecord(sysBetUpdateRecord);
    }

    @Override
    public List selectBetUpdateList(BetSearch betSearch,String language) {
        List list = sysBetUpdateRecordMapper.selectBetUpdateList(betSearch);
        if("en_us".equals(language)){
            list.forEach(m->{
                Map map = (Map) m;
                String option = (String) map.get("option");
                map.put("option", option.replaceAll("庄对", "B P")
                        .replaceAll("闲对", "P P")
                        .replaceAll("庄保险", "B Ins")
                        .replaceAll("闲保险", "P Ins")
                        .replaceAll("和保险", "T Ins")
                        .replaceAll("幸运6", "Lucky 6")
                        .replaceAll("大", "Big")
                        .replaceAll("小", "Small")
                        .replaceAll("庄", "B")
                        .replaceAll("闲", "P")
                        .replaceAll("和", "T")
                        .replaceAll("龙", "Dragon")
                        .replaceAll("虎", "Tiger")
                        .replaceAll("输", "Loss")
                        .replaceAll("赢", "Win")
                );

                String type = (String) map.get("type");
                map.put("type",type.replaceAll("筹码","Chip")
                        .replaceAll("现金","Cash"));

                String result = (String) map.get("result");
                map.put("result",result.replaceAll("庄对", "B P")
                        .replaceAll("闲对", "P P")
                        .replaceAll("庄保险", "B Ins")
                        .replaceAll("闲保险", "P Ins")
                        .replaceAll("和保险", "T Ins")
                        .replaceAll("幸运6（2张牌）", "Lucky 6(2 Cards)")
                        .replaceAll("幸运6（3张牌）", "Lucky 6(3 Cards)")
                        .replaceAll("大", "Big")
                        .replaceAll("小", "Small")
                        .replaceAll("庄", "B")
                        .replaceAll("闲", "P")
                        .replaceAll("和", "T")
                        .replaceAll("龙", "Dragon")
                        .replaceAll("虎", "Tiger")
                        .replaceAll("输", "Loss")
                        .replaceAll("赢", "Win"));
            });
        }
        return list;
    }

    private String changeRelust(String s){
        s =  s.replaceAll("1","闲/")
                .replaceAll("4","庄/")
                .replaceAll("7","和/")
                .replaceAll("5","闲对/")
                .replaceAll("8","庄对/")
                .replaceAll("9","大/")
                .replaceAll("6","小/")
                .replaceAll("0","闲保险/")
                .replaceAll("3","庄保险/")
                .replaceAll("2","和保险/")
                .replaceAll("a","幸运6（2张牌）/")
                .replaceAll("b","幸运6（3张牌）/");
        if(s.length()>1){
            s = s.substring(0,s.length()-1);
        }
        return s;
    }

    private String changeOption(String s){
        return s.replaceAll("1","闲")
                .replaceAll("4","庄")
                .replaceAll("7","和")
                .replaceAll("5","闲对")
                .replaceAll("8","庄对")
                .replaceAll("9","大")
                .replaceAll("6","小")
                .replaceAll("0","闲保险")
                .replaceAll("3","庄保险")
                .replaceAll("2","和保险")
                .replaceAll("a","幸运6");
    }

    private String compare(Object a,Object b){
        if(a.equals(b)){
            return a.toString();
        }else {
            return  a+"->"+b;
        }
    }

    private String changeType(int type){
        switch (type){
            case 0:
                return "$筹码";
            case 1:
                return "$现金";
            case 2:
                return "฿筹码";
            case 3:
                return "฿现金";
            default:
                return "未知";
        }
    }
}
