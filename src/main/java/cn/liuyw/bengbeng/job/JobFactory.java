package cn.liuyw.bengbeng.job;

import cn.liuyw.bengbeng.bean.OpenNo;
import cn.liuyw.bengbeng.exception.BusinessException;
import cn.liuyw.bengbeng.service.OpenNoService;
import cn.liuyw.bengbeng.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobFactory {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private OpenNoService openNoService;

    boolean happyTz = false;
    int happyFlag = 0;

    /**
     */
    @Scheduled(cron = "0 */1 0,7-23 * * ?")
    public void autoPullPk5() {
        try{
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始更新PK5>>>>>>>>>>>>>>>>>>>>>>>");
            List<OpenNo> list = new ArrayList<>();
            try {
                list = HttpUtil.getPK();
            } catch (Exception e) {
                throw new BusinessException("Url.Connect.Error");
            }
            openNoService.addOpenNo(list);
        }catch(BusinessException e){
            if(!e.getCode().equals("OpenNo.Exist")){
                e.printStackTrace();
            }
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<PK5更新完成<<<<<<<<<<<<<<<<<<<<<");
    }

    @Scheduled(cron = "0 */1 0,7-23 * * ?")
    public void autoPullLuck() {
        try{
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始更新Luck>>>>>>>>>>>>>>>>>>>>>>");
            List<OpenNo> list = new ArrayList<>();
            try {
                list = HttpUtil.getLuckOrHappy("luck");
            } catch (Exception e) {
                throw new BusinessException("Url.Connect.Error");
            }
            openNoService.addOpenNo(list);
        }catch(BusinessException e){
            if(!e.getCode().equals("OpenNo.Exist")){
                e.printStackTrace();
            }
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<Luck更新完成<<<<<<<<<<<<<<<<<<<<<");
    }

    @Scheduled(cron = "0 */1 0,7-23 * * ?")
    public void autoPullHappy() {
        try{
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始更新Happy>>>>>>>>>>>>>>>>>>>>>>");
            List<OpenNo> list = new ArrayList<>();
            try {
                list = HttpUtil.getLuckOrHappy("happy");
            } catch (Exception e) {
                throw new BusinessException("Url.Connect.Error");
            }
            openNoService.addOpenNo(list);
        }catch(BusinessException e){
            if(!e.getCode().equals("OpenNo.Exist")){
                e.printStackTrace();
            }
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<Happy更新完成<<<<<<<<<<<<<<<<<<<<<");
    }

    @Scheduled(cron = "0 */1 0,7-23 * * ?")
    public void autoPullSgs() {
        try{
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始更新Sgs>>>>>>>>>>>>>>>>>>>>>>");
            List<OpenNo> list = new ArrayList<>();
            try {
                list = HttpUtil.getSgs();
            } catch (Exception e) {
                throw new BusinessException("Url.Connect.Error");
            }
            openNoService.addOpenNo(list);
        }catch(BusinessException e){
            if(!e.getCode().equals("OpenNo.Exist")){
                e.printStackTrace();
            }
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<Sgs更新完成<<<<<<<<<<<<<<<<<<<<<");
    }

//    @Scheduled(cron = "30 */2 0,7-23 * * ?")
//    public void autoPushLuck() {
//        try{
//            if(!happyTz){
//                System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始投注Happy>>>>>>>>>>>>>>>>>>>>>>");
//                String[] now = HttpUtil.getHappyPhp();
//                int second = Integer.parseInt(now[0]);
//                while(second>=0){
//                    happyTz = true;
//                    if(happyFlag%4==0){
//                        System.out.println(">>>"+now[1]+"期开心16剩余时间:"+second);
//                        second--;
//                        if(second==4){
//                            System.out.println(">>>"+now[1]+"期开心16开始投注,second="+second+" and happyFlag="+happyFlag);
//                            HttpUtil.touzhuSgs(now[1]);
//                        }
//                    }
//                    happyFlag++;
//                    Thread.sleep(250);
//                }
//            }
//        }catch(BusinessException e){
//            if(!e.getCode().equals("OpenNo.Exist")){
//                e.printStackTrace();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            happyTz = false;
//            happyFlag = 0;
//        }
//        System.out.println("<<<<<<<<<<<<<<<<<<<<结束投注Happy<<<<<<<<<<<<<<<<<<<<<");
//    }

    @Scheduled(cron = "30 */1 0,7-23 * * ?")
    public void autoPushSgs() {
        try{
            if(!happyTz){
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始投注三国杀>>>>>>>>>>>>>>>>>>>>>>");
                String[] now = HttpUtil.getSgsPhp();
                int second = Integer.parseInt(now[0])-1;
                while(second>=0){
                    happyTz = true;
                    second--;
                    if(second==4){
                        System.out.println(">>>"+now[1]+"期三国杀开始投注,second="+second+" and happyFlag="+happyFlag);
                        HttpUtil.touzhuSgs(now[1]);
                    }
                    System.out.println(">>>"+now[1]+"期三国杀剩余时间:"+second);
                    Thread.sleep(1000);
                }
            }
        }catch(BusinessException e){
            if(!e.getCode().equals("OpenNo.Exist")){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            happyTz = false;
            happyFlag = 0;
        }
        System.out.println("<<<<<<<<<<<<<<<<<<<<结束投注三国杀<<<<<<<<<<<<<<<<<<<<<");
    }

    @Scheduled(cron = "10 */1 0,7-23 * * ?")
    public void autoRefreshPL() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始更新返奖率>>>>>>>>>>>>>>>>>>>>>>");
        openNoService.updateOpenNoPl();
        System.out.println("<<<<<<<<<<<<<<<<<<<<返奖率更新完成<<<<<<<<<<<<<<<<<<<<<");
    }

}
