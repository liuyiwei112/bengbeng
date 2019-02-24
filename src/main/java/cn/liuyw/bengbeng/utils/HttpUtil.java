package cn.liuyw.bengbeng.utils;

import cn.liuyw.bengbeng.bean.OpenNo;
import cn.liuyw.bengbeng.constants.OpenNoConstant;
import com.alibaba.fastjson.JSONArray;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by liuyw on 17/9/11.
 */
public class HttpUtil {

    static String cookieStr = "";

    static{
        try {
            doLogin();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<OpenNo> getLuckOrHappy(String gameType) throws Exception {
        String url = "http://www.bengbeng.com/happyDirection.php?num=1000";
        if (gameType.equals("luck")) {
            url = "http://www.bengbeng.com/luckDirection.php?num=1000";
        }
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("Proxy-Connection", "keep-alive");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        HttpResponse res = httpClient.execute(httpget);
        HttpEntity entity = res.getEntity();
        List<OpenNo> list = new ArrayList<>();
        if (entity != null) {
            String tempbf;
            StringBuffer stringBuffer = new StringBuffer();
            InputStream instream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GB2312"));
            while ((tempbf = br.readLine()) != null) {
                stringBuffer.append(tempbf);
            }
            String preHtml = stringBuffer.toString();
            String[] openContentArray = new String[2000];
            if (gameType.equals("luck")) {
                openContentArray = preHtml.substring(preHtml.indexOf("<table cellspacing=\"1\""), preHtml.lastIndexOf("<div id=\"game-foot\"></div>")).split("</tr>");
            } else if (gameType.equals("happy")) {
                openContentArray = preHtml.substring(preHtml.indexOf("<table cellspacing=\"1\""), preHtml.lastIndexOf("<div class=\"gamePage\"></div>")).split("</tr>");
            }
            for (int i = 0; i < openContentArray.length - 2; i++) {
                if (i > 5) {
                    OpenNo openNoBean = new OpenNo();
                    openNoBean.setGuessType(gameType);
                    String[] trContent = openContentArray[i].split("</td>");
                    for (int j = 0; j < trContent.length; j++) {
                        //期号
                        if (j == 0) {
                            String no = trContent[j].substring(trContent[j].lastIndexOf(">") + 1, trContent[j].length());
                            openNoBean.setIssue(no);
                            // luck 日期在title上
                            if (gameType.equals("luck")) {
                                String date = trContent[j].substring(trContent[j].lastIndexOf("title=\"") + 7, trContent[j].lastIndexOf("\" bgcolor"));
                                openNoBean.setOpenTime(date);
                            }
                        }
                        if (j == 1 && gameType.equals("happy")) {
                            String date = trContent[j].substring(trContent[j].lastIndexOf(">") + 1, trContent[j].length());
                            openNoBean.setOpenTime(date);
                        }
                        if (trContent[j].indexOf("#FF0000") > -1 || trContent[j].indexOf("#0000FF") > -1) {
                            String openNo = trContent[j].substring(trContent[j].lastIndexOf(">") + 1, trContent[j].length());
                            openNoBean.setOpenNo(openNo);
                            list.add(openNoBean);
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static List<OpenNo> getPK() throws Exception {
        String url = "http://www.bengbeng.com/pk5zst.php?num=1000";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse res = httpClient.execute(httpget);
        HttpEntity entity = res.getEntity();
        List<OpenNo> list = new ArrayList<>();
        if (entity != null) {
            String tempbf;
            StringBuffer stringBuffer = new StringBuffer();
            InputStream instream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GB2312"));
            while ((tempbf = br.readLine()) != null) {
                stringBuffer.append(tempbf);
            }
            String preHtml = stringBuffer.toString();
            String[] openContentArray = new String[2000];
            openContentArray = preHtml.substring(preHtml.indexOf("<table bgcolor=\"#DAE7F6\""), preHtml.lastIndexOf("<div class=\"clear\" style=\"height:20px;\"></div>")).split("</tr>");
            for (int i = 0; i < openContentArray.length - 2; i++) {
                if (i > 4) {
                    OpenNo openNoBean = new OpenNo();
                    openNoBean.setGuessType("pk5");
                    String[] trContent = openContentArray[i].split("</td>");
                    for (int j = 0; j < trContent.length; j++) {
                        //期号
                        if (j == 0) {
                            String no = trContent[j].substring(trContent[j].lastIndexOf(">") + 1, trContent[j].length());
                            openNoBean.setIssue(no);
                        }
                        if (j == 1) {
                            String date = trContent[j].substring(trContent[j].lastIndexOf(">") + 1, trContent[j].length());
                            openNoBean.setOpenTime(date);
                        }
                        if (j >= 2) {
                            if (trContent[j].indexOf("img") > -1) {
                                openNoBean.setOpenNo((j - 2) + "");
                                list.add(openNoBean);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    public static List<OpenNo> getSgs() throws Exception {
        String url = "http://www.bengbeng.com/sgszst.php?last=1000";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        HttpResponse res = httpClient.execute(httpget);
        HttpEntity entity = res.getEntity();
        List<OpenNo> list = new ArrayList<>();
        if (entity != null) {
            String tempbf;
            StringBuffer stringBuffer = new StringBuffer();
            InputStream instream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GB2312"));
            while ((tempbf = br.readLine()) != null) {
                stringBuffer.append(tempbf);
            }
            String preHtml = stringBuffer.toString();
//            System.out.println(preHtml);
            String[] openContentArray = new String[2000];
            openContentArray = preHtml.substring(preHtml.indexOf("<ul class=\"zst\">"), preHtml.lastIndexOf("<div class=\"clear\" style=\"height:20px;\"></div>")).split("<li class=\"zst1\">");
            for (int i = 0; i < openContentArray.length; i++) {
                if (i > 1) {
                    OpenNo openNoBean = new OpenNo();
                    openNoBean.setGuessType("sgs");
                    String[] trContent = openContentArray[i].split("</li>");
                    for (int j = 0; j < trContent.length; j++) {
                        //期号
                        if (j == 0) {
                            String no = trContent[j].trim();
                            openNoBean.setIssue(no);
                        }
                        if(trContent[j].indexOf("s_gr.png")>-1){
                            openNoBean.setOpenNo((j-1)+"");
                            openNoBean.setOpenTime(calSgsOpenTime(Integer.parseInt(openNoBean.getIssue())));
                            list.add(openNoBean);
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    public static Map refreshPL(String gameType, String issue, String openNo) throws Exception {
        String url = "http://www.bengbeng.com/ajax.php?act=reload&game=" + gameType + "&gameNO=" + issue + "&key=" + Math.random();
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("cookie", cookieStr);
        httpget.setHeader("Referer","http://www.bengbeng.com/happyInsert.php?happyNO="+issue);
        httpget.setHeader("X-Requested-With", "XMLHttpRequest");
        httpget.setHeader("Proxy-Connection", "keep-alive");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        HttpResponse res = httpClient.execute(httpget);
        HttpEntity entity = res.getEntity();
        Map resultMap = new HashMap();
        if (entity != null) {
            String tempbf;
            StringBuffer stringBuffer = new StringBuffer();
            InputStream instream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GB2312"));
            while ((tempbf = br.readLine()) != null) {
                stringBuffer.append(tempbf);
            }
            String preHtml = stringBuffer.toString();
            System.out.println(preHtml);
            String[] pl = preHtml.substring(1, preHtml.length() - 1).split(",");
            resultMap.put("allReturnRate",preHtml.substring(1, preHtml.length() - 1));
            Integer openNoInt = Integer.parseInt(openNo);
            if (gameType.equals("luck") || gameType.equals("pk5") || gameType.equals("sgs")) {
                resultMap.put("returnRate",Float.parseFloat(pl[openNoInt]));
            } else {
                resultMap.put("returnRate",Float.parseFloat(pl[openNoInt - 3]));
            }
            return resultMap;
        }
        return null;
    }

    public static void doLogin() throws Exception {
        if(StringUtil.isEmpty(cookieStr)){
            String url = "http://www.bengbeng.com/login.php?act=login&tbUserAccount=8972670&tbUserPwd=nightmare1987&tbCode=&nextAutoLogin=&key=" + Math.random();
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept", "application/json, text/javascript, */*; q=0.01");
            httpPost.setHeader("Proxy-Connection", "keep-alive");
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");

            HttpResponse res = httpClient.execute(httpPost);
            HttpEntity entity = res.getEntity();
            if (entity != null) {
                String tempbf;
                StringBuffer stringBuffer = new StringBuffer();
                InputStream instream = entity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GB2312"));
                while ((tempbf = br.readLine()) != null) {
                    stringBuffer.append(tempbf);
                }
                String preHtml = stringBuffer.toString();
    //            System.out.println(preHtml);
                Header[] resHeader = res.getAllHeaders();
                int i = 0;
                StringBuffer sb = new StringBuffer();
                for (Header header : resHeader) {
                    if (header.getName().equals("Set-Cookie")) {
                        sb.append(header.getValue());
                    }
                    cookieStr = sb.toString();
    //                cookieStr+= "bengLoginID=8972670_ff3645de2fa752fb654550196b3cff1d;" +
    //                        "UM_distinctid=168fe1bb4b91da-0f703fd51ff095-346a780b-13c680-168fe1bb4bc118;" +
    //                        "alertUserGzero_today=0;" +
    //                        "userHit=true; rr_div_open=1;" +
    //                        "CNZZDATA4974798=cnzz_eid%3D553195495-1550447051-%26ntime%3D1550808943;" +
    //                        "Hm_lpvt_d101690dd100bae0da0710df8ddb94b5=1550813610;" +
    //                        "Hm_lvt_d101690dd100bae0da0710df8ddb94b5=1550451456;";
                }
//                System.out.println(cookieStr);
    //                setCookieStore(res);
    //                setContext();
            }
        }
    }

    public static String decodeUnicode(final String dataStr) {
        int start = 0;
        int end = 0;
        final StringBuffer buffer = new StringBuffer();
        while (start > -1) {
            end = dataStr.indexOf("\\u", start + 2);
            String charStr = "";
            if (end == -1) {
                charStr = dataStr.substring(start + 2, dataStr.length());
            } else {
                charStr = dataStr.substring(start + 2, end);
            }
            char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
            start = end;
        }
        return buffer.toString();
    }

    public static String calSgsOpenTime(int targetIssue){
        int startIssue =582549;
        int pos = StringUtil.indexOf(OpenNoConstant.sgsTimeArr,"16:29");
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR,2019);
        calendar.set(Calendar.MONTH,1);
        calendar.set(Calendar.DAY_OF_MONTH,21);
        //计算2个期之前的差距
        int gap = 0;
        if(targetIssue>=startIssue){
            gap = targetIssue - startIssue;
            int gapDay = (pos+gap)/360;
            calendar.add(Calendar.DAY_OF_MONTH,gapDay);
            int finalPos = (pos+gap)%360;
//            System.out.println(sdf.format(calendar.getTime())+"~~~"+finalPos);
            return sdf.format(calendar.getTime())+" "+OpenNoConstant.sgsTimeArr[finalPos];
        }else{
            gap = startIssue - targetIssue;
            //如果还在当天范围内
            if(pos - gap >=0){
                int finalPos = pos - gap;
//                System.out.println(sdf.format(calendar.getTime())+"~~~"+finalPos);
                return sdf.format(calendar.getTime())+" "+OpenNoConstant.sgsTimeArr[finalPos];
            }
            //如果已经不再当天
            else{
                int gapDay = (pos-gap)/360-1;
                calendar.add(Calendar.DAY_OF_MONTH,gapDay);
                int finalPos = 360 + (pos - gap)%360;
//                System.out.println(sdf.format(calendar.getTime())+"~~~"+finalPos);
                return sdf.format(calendar.getTime())+" "+OpenNoConstant.sgsTimeArr[finalPos];
            }
        }
    }

    public static String[] getSgsPhp() throws Exception{
        doLogin();
        long startDate = System.currentTimeMillis();
        String url = "http://www.bengbeng.com/sgs.php";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("cookie", cookieStr);
        httpget.setHeader("X-Requested-With", "XMLHttpRequest");
        httpget.setHeader("Proxy-Connection", "keep-alive");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        HttpResponse res = httpClient.execute(httpget);
        HttpEntity entity = res.getEntity();
        Map resultMap = new HashMap();
        if (entity != null) {
            String tempbf;
            StringBuffer stringBuffer = new StringBuffer();
            InputStream instream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GB2312"));
            while ((tempbf = br.readLine()) != null) {
                stringBuffer.append(tempbf);
            }
            String preHtml = stringBuffer.toString();
            long endDate = System.currentTimeMillis();

            System.out.println(">>>>>获取开奖时间和期数耗时:"+(endDate-startDate));
            Integer leftTime = Integer.parseInt(preHtml.substring(preHtml.indexOf("var cDate =")+13,preHtml.indexOf(";function ShowSecond()")-1));
            String nowIssue = preHtml.substring(preHtml.indexOf("第<span>")+7,preHtml.indexOf("</span>回合"));
            return new String[]{(leftTime+Math.round((endDate-startDate)/1000))+"",nowIssue};
        }
        return null;

    }

    public static String[] getHappyPhp() throws Exception{
        doLogin();
        long startDate = System.currentTimeMillis();
        String url = "http://www.bengbeng.com/happy.php";
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        httpget.setHeader("cookie", cookieStr);
        httpget.setHeader("X-Requested-With", "XMLHttpRequest");
        httpget.setHeader("Proxy-Connection", "keep-alive");
        httpget.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
        HttpResponse res = httpClient.execute(httpget);
        HttpEntity entity = res.getEntity();
        Map resultMap = new HashMap();
        if (entity != null) {
            String tempbf;
            StringBuffer stringBuffer = new StringBuffer();
            InputStream instream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GB2312"));
            while ((tempbf = br.readLine()) != null) {
                stringBuffer.append(tempbf);
            }
            String preHtml = stringBuffer.toString();
            long endDate = System.currentTimeMillis();

            System.out.println(">>>>>获取开奖时间和期数耗时:"+(endDate-startDate));
            Integer leftTime = Integer.parseInt(preHtml.substring(preHtml.indexOf("var cDate =")+13,preHtml.indexOf(";function ShowSecond()")-1));
            String nowIssue = preHtml.substring(preHtml.indexOf("var happyNO =")+15,preHtml.indexOf(";if(readCookie('cSoundHappy')")-1);
            return new String[]{(leftTime+Math.round((endDate-startDate)/1000))+"",nowIssue};
        }
        return null;

    }

    public static void touzhuSgs(String issue) throws Exception{
        String url = "http://www.bengbeng.com/sgsInsert.php?no="+issue;
        doLogin();
        long startDate = System.currentTimeMillis();
        Map plMap = refreshPL("sgs", issue, "3");
        String rateStr = plMap.get("allReturnRate").toString();
        System.out.println(">>>>>投注前返奖率为:["+rateStr+"]>>>>>>>>>>>>");
        String[] rates = rateStr.split(",");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("hearchen","0c0a73eb37a3175c89d65fc78a7a94ee"));
        nvps.add(new BasicNameValuePair("payType","1"));
        nvps.add(new BasicNameValuePair("herets","12"));
        nvps.add(new BasicNameValuePair("kkl","3"));
        Integer[] touzhu = new Integer[10];
        for(int i=0;i<rates.length;i++){
            String rate = rates[i];
            Double rateDb = Double.parseDouble(rate);
            if(rateDb>=9.95){
                nvps.add(new BasicNameValuePair("tbNum[]", "250"));
                touzhu[i] = 50*5;
            }else{
                nvps.add(new BasicNameValuePair("tbNum[]", "1"));
                touzhu[i] = 1;
            }
        }
        long endDate = System.currentTimeMillis();
        System.out.println(">>>>>刷新赔率耗时:"+(endDate-startDate));
        System.out.println(">>>>>投注方案为:["+JSONArray.toJSONString(touzhu)+"]>>>>>>>>>>>>");

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("cookie", cookieStr);
        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.setHeader("Proxy-Connection", "keep-alive");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        HttpResponse res = httpClient.execute(httpPost);
        HttpEntity entity = res.getEntity();

        if (entity != null) {
            String tempbf;
            StringBuffer stringBuffer = new StringBuffer();
            InputStream instream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GB2312"));
            while ((tempbf = br.readLine()) != null) {
                stringBuffer.append(tempbf);
            }
            String preHtml = stringBuffer.toString();
//            System.out.println(preHtml);
            if(preHtml.indexOf("history.back")>-1){
                System.out.println(preHtml.substring(preHtml.indexOf("alert")+7,preHtml.indexOf(";history.back")-2));
            }else{
                System.out.println(preHtml.substring(preHtml.indexOf("alert")+7,preHtml.indexOf(";location.href")-2));
            }
        }

    }


    public static void touzhuHappy(String issue) throws Exception{
        String url = "http://www.bengbeng.com/happyInsert.php?happyNO="+issue;
        doLogin();
        long startDate = System.currentTimeMillis();
        Map plMap = refreshPL("happy", issue, "3");
        String rateStr = plMap.get("allReturnRate").toString();
        System.out.println(">>>>>投注前返奖率为:["+rateStr+"]>>>>>>>>>>>>");
        String[] rates = rateStr.split(",");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("hearchen","0c0a73eb37a3175c89d65fc78a7a94ee"));
        nvps.add(new BasicNameValuePair("payType","1"));
        nvps.add(new BasicNameValuePair("hearnew","old"));
        nvps.add(new BasicNameValuePair("hearyes","bbb"));
        nvps.add(new BasicNameValuePair("herets","12"));
        nvps.add(new BasicNameValuePair("select2","1"));
        nvps.add(new BasicNameValuePair("kkl","4"));
        Integer[] touzhu = new Integer[16];
        for(int i=0;i<rates.length;i++){
            String rate = rates[i];
            Double rateDb = Double.parseDouble(rate);
            if(rateDb*1.01>OpenNoConstant.happyReturnRate[i]){
                nvps.add(new BasicNameValuePair("tbChk["+(i+3)+"]", "on"));
                nvps.add(new BasicNameValuePair("tbNum["+(i+3)+"]", OpenNoConstant.happyFillNum[i].toString()));
                touzhu[i] = OpenNoConstant.happyFillNum[i];
            }else{
                nvps.add(new BasicNameValuePair("tbChk["+(i+3)+"]", ""));
                nvps.add(new BasicNameValuePair("tbNum["+(i+3)+"]", "0"));
                touzhu[i] = 0;
            }
        }
        long endDate = System.currentTimeMillis();
        System.out.println(">>>>>刷新赔率耗时:"+(endDate-startDate));
        System.out.println(">>>>>投注方案为:["+JSONArray.toJSONString(touzhu)+"]>>>>>>>>>>>>");

        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("cookie", cookieStr);
        httpPost.setHeader("X-Requested-With", "XMLHttpRequest");
        httpPost.setHeader("Proxy-Connection", "keep-alive");
        httpPost.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));

        HttpResponse res = httpClient.execute(httpPost);
        HttpEntity entity = res.getEntity();

        if (entity != null) {
            String tempbf;
            StringBuffer stringBuffer = new StringBuffer();
            InputStream instream = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(instream, "GB2312"));
            while ((tempbf = br.readLine()) != null) {
                stringBuffer.append(tempbf);
            }
            String preHtml = stringBuffer.toString();
//            System.out.println(preHtml);
            if(preHtml.indexOf("history.back")>-1){
                System.out.println(preHtml.substring(preHtml.indexOf("alert")+7,preHtml.indexOf(";history.back")-2));
            }else{
                System.out.println(preHtml.substring(preHtml.indexOf("alert")+7,preHtml.indexOf(";location.href")-2));
            }
        }

    }


    public static void main(String[] args) {
        try {
//            HttpUtil.getSgs();
//            List<OpenNo> list = HttpUtil.getPK();
//            System.out.println(list.size());
//            int i=0;
//            for (OpenNo openNo:list) {
//                System.out.println("序号:"+(i++)+"------" + openNo.getIssue()+"------"+openNo.getOpenTime()+"------"+openNo.getOpenNo()+"-------"+openNo.getReturnRate());
//            }
            String[] now = getSgsPhp();
            touzhuSgs("582980");
//            int second = Integer.parseInt(now[0]);
//            while(second>=0){
//                System.out.println(">>>"+now[1]+"期开心16剩余时间:"+second);
//                if(second==2){
//                    touzhu(now[1]);
//                }
//                second--;
//                Thread.sleep(1000);
//            }
//            System.out.println(decodeUnicode("\\u767b\\u5f55\\u6210\\u529f"));
//            System.out.println(refreshPL("sgs", "582043", "3"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
