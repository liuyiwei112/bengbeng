package cn.liuyw.bengbeng.service;

import cn.liuyw.bengbeng.bean.OpenNo;
import cn.liuyw.bengbeng.bean.OpenNoSummary;
import cn.liuyw.bengbeng.bean.Pager;
import cn.liuyw.bengbeng.constants.OpenNoConstant;
import cn.liuyw.bengbeng.mapper.SummaryMapper;
import cn.liuyw.bengbeng.utils.OpenNoUtils;
import cn.liuyw.bengbeng.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by liuyw on 17/8/31.
 */
@Service
public class SummaryService {

    @Autowired
    SummaryMapper summaryMapper;

    @Autowired
    OpenNoService openNoService;


    /**
     * 统计指定某日所有期数的全包盈亏率
     * @param guessType
     * @param openTime
     * @return
     */
    public OpenNoSummary queryAllInSum(String guessType,String openTime){
        return summaryMapper.queryAllInSum(guessType,openTime);
    }

    /**
     * 查询全种类汇总统计数据
     * @param summaryType
     * @return
     */
    public Map querySummaryAll(int summaryType){
        Map map = new LinkedHashMap();
        OpenNoSummary luckSum = summaryMapper.querySummaryAll("luck",summaryType);
        OpenNoSummary happySum = summaryMapper.querySummaryAll("happy",summaryType);
        OpenNoSummary pk5Sum = summaryMapper.querySummaryAll("pk5",summaryType);
        OpenNoSummary sgsSum = summaryMapper.querySummaryAll("sgs",summaryType);
        //汇总统计的需二次计算比例
        if(summaryType==1){
            DecimalFormat df = new DecimalFormat("#.##");
            luckSum.setSummaryValue(Float.parseFloat(df.format(luckSum.getSummaryValue()/10000.0)));
            happySum.setSummaryValue(Float.parseFloat(df.format(happySum.getSummaryValue()/10800.0)));
            pk5Sum.setSummaryValue(Float.parseFloat(df.format(pk5Sum.getSummaryValue()/10000.0)));
            sgsSum.setSummaryValue(Float.parseFloat(df.format(sgsSum.getSummaryValue()/10000.0)));
        }

        map.put("luckSum",luckSum);
        map.put("happySum",happySum);
        map.put("pk5Sum",pk5Sum);
        map.put("sgsSum",sgsSum);
        return map;
    }


    /*****************************去20%号码*****************************/

    /**
     * 查询去掉最大的2个数字的走势图
     * 基数10倍,即中奖基准得1W
     * @return
     */
    public List<OpenNoSummary> queryLuckSumByDay2(String openTime) {
        Pager<OpenNo> pager = openNoService.getOpenNoByType("luck",openTime, 1, 4000);
        List<OpenNo> lists = pager.getList();
        List<Map> secondCalList = new ArrayList<Map>();

        //将原始数据进行二次计算
        for (OpenNo openNo : lists) {
            if (StringUtil.isEmpty(openNo.getAllReturnRate())) {
                continue;
            }
            Map calResult = new HashMap();
            calResult.put("openDay", openNo.getOpenTime().substring(0, 10).trim());
            Double[] rateDbl = StringUtil.replaceStrArrToDoubleArr(openNo.getAllReturnRate().split(","));
            Integer[] pushValue = new Integer[28];
            //查询返奖最高的3个数
            int calNum = 3, totalCost = 0;
            while(OpenNoUtils.calLuckLeftNoCost(rateDbl)>550){
                OpenNoUtils.replaceLuckMinRate(rateDbl);
            }
            //生成推送方案
            for (int j = 0; j < rateDbl.length; j++) {
                if (rateDbl[j] == 10000) {
                    pushValue[j] = 1;
                    totalCost += 1;
                } else {
                    pushValue[j] = OpenNoConstant.luckFillNum[j]*10;
                    totalCost += OpenNoConstant.luckFillNum[j]*10;
                }
            }
            //执行成本、奖金计算
            Integer getTotal = (int) (pushValue[Integer.parseInt(openNo.getOpenNo())] * openNo.getReturnRate());
            calResult.put("cost", totalCost);
            calResult.put("getTotal", getTotal);
            calResult.put("get", getTotal - totalCost);
            secondCalList.add(calResult);
        }
        return returnOpenSummaryList(secondCalList);
    }

    /**
     * 查询去掉最小的2个数字的走势图
     * 倍数50,即中奖后得10800
     * @return
     */
    public List<OpenNoSummary> queryHappySumByDay2(String openTime) {
        Pager<OpenNo> pager = openNoService.getOpenNoByType("happy",openTime, 1, 4000);
        List<OpenNo> lists = pager.getList();
        List<Map> secondCalList = new ArrayList<Map>();

        //将原始数据进行二次计算
        for (OpenNo openNo : lists) {
            if (StringUtil.isEmpty(openNo.getAllReturnRate())) {
                continue;
            }
            Map calResult = new HashMap();
            calResult.put("openDay", openNo.getOpenTime().substring(0, 10).trim());
            Double[] rateDbl = StringUtil.replaceStrArrToDoubleArr(openNo.getAllReturnRate().split(","));
            Integer[] pushValue = new Integer[16];
//            //查询返奖最低的2个数
            int calNum = 2, totalCost = 0;
//            while (calNum > 0) {
//                OpenNoUtils.replaceHappyMinRate(rateDbl);
//                calNum--;
//            }
            while(OpenNoUtils.calHappyLeftNoCost(rateDbl)>120){
                OpenNoUtils.replaceHappyMinRate(rateDbl);
            }
            //生成推送方案
            for (int j = 0; j < rateDbl.length; j++) {
                if (rateDbl[j] == 1000) {
                    pushValue[j] = 1;
                    totalCost += 1;
                } else {
                    pushValue[j] = OpenNoConstant.happyFillNum[j]*50;
                    totalCost += OpenNoConstant.happyFillNum[j]*50;
                }
            }
            //执行成本、奖金计算
            Integer getTotal = (int) (pushValue[Integer.parseInt(openNo.getOpenNo())-3] * openNo.getReturnRate());
            calResult.put("cost", totalCost);
            calResult.put("getTotal", getTotal);
            calResult.put("get", getTotal - totalCost);
            secondCalList.add(calResult);
        }
        return returnOpenSummaryList(secondCalList);
    }

    /**
     * 查询去掉最小的2个数字的走势图
     * 100倍,即中奖后得1W
     * @return
     */
    public List<OpenNoSummary> queryPk5SumByDay2(String openTime) {
        Pager<OpenNo> pager = openNoService.getOpenNoByType("pk5",openTime, 1, 4000);
        List<OpenNo> lists = pager.getList();
        List<Map> secondCalList = new ArrayList<Map>();

        //将原始数据进行二次计算
        for (OpenNo openNo : lists) {
            if (StringUtil.isEmpty(openNo.getAllReturnRate())) {
                continue;
            }
            Map calResult = new HashMap();
            calResult.put("openDay", openNo.getOpenTime().substring(0, 10).trim());
            Double[] rateDbl = StringUtil.replaceStrArrToDoubleArr(openNo.getAllReturnRate().split(","));
            Integer[] pushValue = new Integer[5];
            //查询返奖最低的2个数
            int calNum = 1, totalCost = 0;
//            while (calNum > 0) {
//                OpenNoUtils.replacePk5MinRate(rateDbl);
//                calNum--;
//            }
            while(OpenNoUtils.calPk5LeftNoCost(rateDbl)>50){
                OpenNoUtils.replacePk5MinRate(rateDbl);
            }
            //生成推送方案
            for (int j = 0; j < rateDbl.length; j++) {
                if (rateDbl[j] == 1000) {
                    pushValue[j] = 1;
                    totalCost += 1;
                } else {
                    pushValue[j] = OpenNoConstant.pk5FillNum[j]*100;
                    totalCost += OpenNoConstant.pk5FillNum[j]*100;
                }
            }
            //执行成本、奖金计算
            Integer getTotal = (int) (pushValue[Integer.parseInt(openNo.getOpenNo())] * openNo.getReturnRate());
            calResult.put("cost", totalCost);
            calResult.put("getTotal", getTotal);
            calResult.put("get", getTotal - totalCost);
            secondCalList.add(calResult);
        }
        return returnOpenSummaryList(secondCalList);
    }

    /**
     * 查询去掉最小的2个数字的走势图
     * 倍数1000,即中奖得1W
     * @return
     */
    public List<OpenNoSummary> querySgsSumByDay2(String openTime) {
        Pager<OpenNo> pager = openNoService.getOpenNoByType("sgs",openTime, 1, 4000);
        List<Map> secondCalList = new ArrayList<Map>();
        //将原始数据进行二次计算
        for (OpenNo openNo : pager.getList()) {
            if (StringUtil.isEmpty(openNo.getAllReturnRate())) {
                continue;
            }
            Map calResult = new HashMap();
            calResult.put("openDay", openNo.getOpenTime().substring(0, 10).trim());
            Double[] rateDbl = StringUtil.replaceStrArrToDoubleArr(openNo.getAllReturnRate().split(","));
            Integer[] pushValue = new Integer[10];
            //查询返奖最低的2个数
            int calNum = 6, totalCost = 0;
            while (calNum > 0) {
                OpenNoUtils.replaceSgsMinRate(rateDbl);
                calNum--;
            }
//            OpenNoUtils.calSgsRate(rateDbl);
            //生成推送方案
            for (int j = 0; j < rateDbl.length; j++) {
                if (rateDbl[j] == 100) {
                    pushValue[j] = 1;
                    totalCost += 1;
                } else {
                    pushValue[j] = 1 * 1000;
                    totalCost += 1 * 1000;
                }
            }
            //执行成本、奖金计算
            Integer getTotal = (int) (pushValue[Integer.parseInt(openNo.getOpenNo())] * openNo.getReturnRate());
            calResult.put("cost", totalCost);
            calResult.put("getTotal", getTotal);
            calResult.put("get", getTotal - totalCost);
            secondCalList.add(calResult);
        }
        return returnOpenSummaryList(secondCalList);
    }

    private List<OpenNoSummary> returnOpenSummaryList(List<Map> secondCalList) {
        Map<String,OpenNoSummary> summaryMap = new LinkedHashMap();
        List<OpenNoSummary> resultList = new ArrayList<OpenNoSummary>();
        String nowDay = "";
        Integer totalGet = 0;
        //开始汇总
        for (Map map : secondCalList) {
            if (nowDay.equals("") || !nowDay.equals(map.get("openDay").toString())) {
                nowDay = map.get("openDay").toString();
                OpenNoSummary openNoSummary = new OpenNoSummary();
                openNoSummary.setOpenDay(nowDay);
                openNoSummary.setSummaryValue(0);
                openNoSummary.setSummaryCount(secondCalList.size());
                summaryMap.put(nowDay,openNoSummary);
                totalGet = 0;
            }
            OpenNoSummary summary = (OpenNoSummary)summaryMap.get(nowDay);
            totalGet += (int) map.get("get");
            summary.setSummaryValue(totalGet);
        }
        for(String key:summaryMap.keySet()){
            resultList.add((OpenNoSummary) summaryMap.get(key));
        }
        return resultList;

    }

    public OpenNoSummary insertSomeDaySummary(String guessType,int summaryType,String openTime){
        List<OpenNoSummary> resultList = new ArrayList<OpenNoSummary>();
        //取赔率较大的汇总
        if(summaryType==1){
            if(guessType.equals("luck")){
                resultList = queryLuckSumByDay2(openTime);
            }else if(guessType.equals("happy")){
                resultList = queryHappySumByDay2(openTime);
            }else if(guessType.equals("pk5")){
                resultList = queryPk5SumByDay2(openTime);
            }else if(guessType.equals("sgs")){
                resultList = querySgsSumByDay2(openTime);
            }
        }
        //全包汇总
        else if(summaryType==0){
            resultList.add(queryAllInSum(guessType,openTime));
        }
        for(OpenNoSummary summary:resultList){
            if(StringUtil.isNotEmpty(summary.getOpenDay())){
                summary.setGuessType(guessType);
                summary.setSummaryType(summaryType);
                if(summaryMapper.querySummaryList(guessType,summaryType,openTime).size()==0){
                    summaryMapper.insertSummary(summary);
                }else{
                    summaryMapper.updateSummary(summary);
                }
                return summary;
            }
        }
        return null;
    }

    public List<OpenNoSummary> querySummary(String guessType,int summaryType){
        return summaryMapper.querySummaryList(guessType,summaryType,"");
    }

}
