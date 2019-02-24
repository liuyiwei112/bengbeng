package cn.liuyw.bengbeng.service;

import cn.liuyw.bengbeng.bean.OpenNo;
import cn.liuyw.bengbeng.bean.OpenNoLabelEnum;
import cn.liuyw.bengbeng.bean.OpenNoSummary;
import cn.liuyw.bengbeng.bean.Pager;
import cn.liuyw.bengbeng.exception.BusinessException;
import cn.liuyw.bengbeng.mapper.OpenNoMapper;
import cn.liuyw.bengbeng.mapper.SummaryMapper;
import cn.liuyw.bengbeng.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by liuyw on 17/8/31.
 */
@Service
public class SummaryService {

    @Autowired
    SummaryMapper summaryMapper;


    public OpenNoSummary queryLuckSum(Integer beishu){
        Integer touzhu = 10*beishu;
        return summaryMapper.queryLuckSum(touzhu);
    }


    public OpenNoSummary queryHappySum(Integer beishu){
        Integer touzhu = 50*beishu;
        return summaryMapper.queryHappySum(touzhu);
    }

    public OpenNoSummary queryPk5Sum(Integer beishu){
        Integer touzhu = 100*beishu;
        return summaryMapper.queryPk5Sum(touzhu);
    }

    public OpenNoSummary querySgsSum(Integer beishu){
        return summaryMapper.querySgsSum(beishu);
    }

    public List<OpenNoSummary> queryLuckSumByDay(String guessType){
        if(guessType.equals("luck")){
            return summaryMapper.queryLuckSumByDay();
        }else if(guessType.equals("happy")){
            return summaryMapper.queryHappySumByDay();
        }else if(guessType.equals("pk5")){
            return summaryMapper.queryPk5SumByDay();
        }else if(guessType.equals("sgs")){
            return summaryMapper.querySgsSumByDay();
        }
        return null;
    }

}
