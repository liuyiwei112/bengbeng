package cn.liuyw.bengbeng.job;

import cn.liuyw.bengbeng.bean.OpenNo;
import cn.liuyw.bengbeng.exception.BusinessException;
import cn.liuyw.bengbeng.service.OpenNoService;
import cn.liuyw.bengbeng.service.SummaryService;
import cn.liuyw.bengbeng.utils.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
public class JobFactory {

    @Autowired
    private OpenNoService openNoService;

    @Autowired
    private SummaryService summaryService;

    @Value("${spring.luck.beishu}")
    private Integer luckBS;

    @Value("${spring.sgs.beishu}")
    private Integer sgsBS;

    @Value("${spring.happy.beishu}")
    private Integer happyBS;

    @Value("${spring.pk5.beishu}")
    private Integer pk5BS;

    @Value("${spring.luck.time}")
    private Integer luckTime;

    @Value("${spring.happy.time}")
    private Integer happyTime;

    @Value("${spring.pk5.time}")
    private Integer pk5Time;

    @Value("${spring.sgs.time}")
    private Integer sgsTime;

    @Value("${spring.web.open}")
    private Boolean webOpen;

    @Value("${spring.luck.open}")
    private Boolean luckOpen;

    @Value("${spring.happy.open}")
    private Boolean happyOpen;

    @Value("${spring.pk5.open}")
    private Boolean pk5Open;

    @Value("${spring.sgs.open}")
    private Boolean sgsOpen;

    /**
     * 定时更新Luck号码
     */
    @Scheduled(cron = "0 */3 0,7-23 * * ?")
    public void autoPullLuck() {
        if(webOpen){
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
    }

    /**
     * 定时更新Happy号码
     */
    @Scheduled(cron = "10 */3 0,7-23 * * ?")
    public void autoPullHappy() {
        if(webOpen){
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
    }

    /**
     * 定时更新Pk5号码
     */
    @Scheduled(cron = "20 */3 0,7-23 * * ?")
    public void autoPullPk5() {
        if(webOpen){
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
    }

    /**
     * 定时更新Sgs号码
     */
    @Scheduled(cron = "30 */3 0,7-23 * * ?")
    public void autoPullSgs() {
        if(webOpen){
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
    }

    /**
     * 自动刷新返奖率信息
     */
    @Scheduled(cron = "40 */3 0,7-23 * * ?")
    public void autoRefreshPL() {
        if(webOpen){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始更新返奖率>>>>>>>>>>>>>>>>>>>>>>");
            openNoService.updateOpenNoPl();
            System.out.println("<<<<<<<<<<<<<<<<<<<<返奖率更新完成<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    /**
     * 自动推送Happy
     */
    @Scheduled(cron = "30 */1 0,7-23 * * ?")
    public void autoPushLuck() {
        if(luckOpen){
            try {
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始投注Luck>>>>>>>>>>>>>>>>>>>>>>");
                String[] now = HttpUtil.getLuckPhp();
                int second = Integer.parseInt(now[0]) - 1;
                while (second >= 0) {
                    System.out.println(">>>" + now[1] + "期幸运28剩余时间:" + second);
                    if (second == luckTime) {
                        System.out.println(">>>" + now[1] + "期幸运28开始投注");
                        HttpUtil.touzhuLuck(now[1], luckBS);
                        break;
                    }
                    second--;
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("<<<<<<<<<<<<<<<<<<<<结束投注Luck<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    /**
     * 自动推送Happy
     */
    @Scheduled(cron = "30 */1 0,7-23 * * ?")
    public void autoPushHappy() {
        if(happyOpen){
            try{
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始投注Happy>>>>>>>>>>>>>>>>>>>>>>");
                String[] now = HttpUtil.getHappyPhp();
                int second = Integer.parseInt(now[0])-1;
                while(second>=0){
                    System.out.println(">>>"+now[1]+"期开心16剩余时间:"+second);
                    if(second== happyTime){
                        System.out.println(">>>"+now[1]+"期开心16开始投注");
                        HttpUtil.touzhuHappy(now[1],happyBS);
                        break;
                    }
                    second--;
                    Thread.sleep(1000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("<<<<<<<<<<<<<<<<<<<<结束投注Happy<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    /**
     * 自动推送Pk5,PK全包,赔率变化太大
     */
    @Scheduled(cron = "30 */1 0,7-23 * * ?")
    public void autoPushPk5() {
        if(pk5Open){
            try{
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始投注Pk5>>>>>>>>>>>>>>>>>>>>>>");
                String[] now = HttpUtil.getPk5Php();
                int second = Integer.parseInt(now[0])-1;
                while(second>=0){
                    System.out.println(">>>"+now[1]+"期Pk5剩余时间:"+second);
                    if(second==pk5Time){
                        System.out.println(">>>"+now[1]+"期Pk5开始投注");
                        HttpUtil.touzhuPk5(now[1],pk5BS);
                        break;
                    }
                    second--;
                    Thread.sleep(1000);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("<<<<<<<<<<<<<<<<<<<<结束投注Pk5<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    /**
     * 自动推送Sgs
     */
    @Scheduled(cron = "30 */1 0,7-23 * * ?")
    public void autoPushSgs() {
        if(sgsOpen){
            try{
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始投注三国杀>>>>>>>>>>>>>>>>>>>>>>");
                String[] now = HttpUtil.getSgsPhp();
                int second = Integer.parseInt(now[0])-1;
                while(second>=0) {
                    System.out.println(">>>" + now[1] + "期三国杀剩余时间:" + second);
                    if (second == sgsTime) {
                        System.out.println(">>>" + now[1] + "期三国杀开始投注");
                        HttpUtil.touzhuSgs(now[1],sgsBS);
                        break;
                    }
                    second--;
                    Thread.sleep(1000);
                }
            }catch(BusinessException e){
                if(!e.getCode().equals("OpenNo.Exist")){
                    e.printStackTrace();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            System.out.println("<<<<<<<<<<<<<<<<<<<<结束投注三国杀<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    /**
      * 定时日汇总实时
      */
    @Scheduled(cron = "50 */3 0,7-23 * * ?")
    public void autoSummary() {
        if(webOpen){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始更新汇总数据>>>>>>>>>>>>>>>>>>>>>>");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String openTime = sdf.format(new Date());
            summaryService.insertSomeDaySummary("luck",0,openTime);
            summaryService.insertSomeDaySummary("happy",0,openTime);
            summaryService.insertSomeDaySummary("pk5",0,openTime);
            summaryService.insertSomeDaySummary("sgs",0,openTime);

            summaryService.insertSomeDaySummary("luck",1,openTime);
            summaryService.insertSomeDaySummary("happy",1,openTime);
            summaryService.insertSomeDaySummary("pk5",1,openTime);
            summaryService.insertSomeDaySummary("sgs",1,openTime);

            System.out.println("<<<<<<<<<<<<<<<<<<<<汇总数据更新完成<<<<<<<<<<<<<<<<<<<<<");
        }
    }

    /**
     * 定时日汇总每日
     */
    @Scheduled(cron = "0 5 0 * * ?")
    public void autoSummaryPreDay() {
        if(webOpen){
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>开始更新汇总数据>>>>>>>>>>>>>>>>>>>>>>");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.set(Calendar.DAY_OF_YEAR,-1);
            String openTime = sdf.format(calendar.getTime());
            summaryService.insertSomeDaySummary("luck",0,openTime);
            summaryService.insertSomeDaySummary("happy",0,openTime);
            summaryService.insertSomeDaySummary("pk5",0,openTime);
            summaryService.insertSomeDaySummary("sgs",0,openTime);

            summaryService.insertSomeDaySummary("luck",1,openTime);
            summaryService.insertSomeDaySummary("happy",1,openTime);
            summaryService.insertSomeDaySummary("pk5",1,openTime);
            summaryService.insertSomeDaySummary("sgs",1,openTime);

            System.out.println("<<<<<<<<<<<<<<<<<<<<汇总数据更新完成<<<<<<<<<<<<<<<<<<<<<");
        }
    }

}
