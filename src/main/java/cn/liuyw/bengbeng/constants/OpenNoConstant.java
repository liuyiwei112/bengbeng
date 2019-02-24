package cn.liuyw.bengbeng.constants;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by liuyw on 19/2/21.
 */
public class OpenNoConstant {

    public static String luckAllNo = "0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27";
    public static String happyAllNo = "3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18";
    public static String pk5AllNo = "豹,对,顺,半,杂";
    public static String sgsAllNo = "吕布,刘备,张飞,关羽,曹操,夏侯惇,司马懿,孙权,孙策,周瑜";
    public static String[] sgsTimeArr = new String[]{"07:05", "07:08", "07:11", "07:14", "07:17", "07:20", "07:23", "07:26", "07:29", "07:32", "07:35", "07:38", "07:41", "07:44", "07:47", "07:50", "07:53", "07:56", "07:59", "08:02", "08:05", "08:08", "08:11", "08:14", "08:17", "08:20", "08:23", "08:26", "08:29", "08:32", "08:35", "08:38", "08:41", "08:44", "08:47", "08:50", "08:53", "08:56", "08:59", "09:02", "09:05", "09:08", "09:11", "09:14", "09:17", "09:20", "09:23", "09:26", "09:29", "09:32", "09:35", "09:38", "09:41", "09:44", "09:47", "09:50", "09:53", "09:56", "09:59", "10:02", "10:05", "10:08", "10:11", "10:14", "10:17", "10:20", "10:23", "10:26", "10:29", "10:32", "10:35", "10:38", "10:41", "10:44", "10:47", "10:50", "10:53", "10:56", "10:59", "11:02", "11:05", "11:08", "11:11", "11:14", "11:17", "11:20", "11:23", "11:26", "11:29", "11:32", "11:35", "11:38", "11:41", "11:44", "11:47", "11:50", "11:53", "11:56", "11:59", "12:02", "12:05", "12:08", "12:11", "12:14", "12:17", "12:20", "12:23", "12:26", "12:29", "12:32", "12:35", "12:38", "12:41", "12:44", "12:47", "12:50", "12:53", "12:56", "12:59", "13:02", "13:05", "13:08", "13:11", "13:14", "13:17", "13:20", "13:23", "13:26", "13:29", "13:32", "13:35", "13:38", "13:41", "13:44", "13:47", "13:50", "13:53", "13:56", "13:59", "14:02", "14:05", "14:08", "14:11", "14:14", "14:17", "14:20", "14:23", "14:26", "14:29", "14:32", "14:35", "14:38", "14:41", "14:44", "14:47", "14:50", "14:53", "14:56", "14:59", "15:02", "15:05", "15:08", "15:11", "15:14", "15:17", "15:20", "15:23", "15:26", "15:29", "15:32", "15:35", "15:38", "15:41", "15:44", "15:47", "15:50", "15:53", "15:56", "15:59", "16:02", "16:05", "16:08", "16:11", "16:14", "16:17", "16:20", "16:23", "16:26", "16:29", "16:32", "16:35", "16:38", "16:41", "16:44", "16:47", "16:50", "16:53", "16:56", "16:59", "17:02", "17:05", "17:08", "17:11", "17:14", "17:17", "17:20", "17:23", "17:26", "17:29", "17:32", "17:35", "17:38", "17:41", "17:44", "17:47", "17:50", "17:53", "17:56", "17:59", "18:02", "18:05", "18:08", "18:11", "18:14", "18:17", "18:20", "18:23", "18:26", "18:29", "18:32", "18:35", "18:38", "18:41", "18:44", "18:47", "18:50", "18:53", "18:56", "18:59", "19:02", "19:05", "19:08", "19:11", "19:14", "19:17", "19:20", "19:23", "19:26", "19:29", "19:32", "19:35", "19:38", "19:41", "19:44", "19:47", "19:50", "19:53", "19:56", "19:59", "20:02", "20:05", "20:08", "20:11", "20:14", "20:17", "20:20", "20:23", "20:26", "20:29", "20:32", "20:35", "20:38", "20:41", "20:44", "20:47", "20:50", "20:53", "20:56", "20:59", "21:02", "21:05", "21:08", "21:11", "21:14", "21:17", "21:20", "21:23", "21:26", "21:29", "21:32", "21:35", "21:38", "21:41", "21:44", "21:47", "21:50", "21:53", "21:56", "21:59", "22:02", "22:05", "22:08", "22:11", "22:14", "22:17", "22:20", "22:23", "22:26", "22:29", "22:32", "22:35", "22:38", "22:41", "22:44", "22:47", "22:50", "22:53", "22:56", "22:59", "23:02", "23:05", "23:08", "23:11", "23:14", "23:17", "23:20", "23:23", "23:26", "23:29", "23:32", "23:35", "23:38", "23:41", "23:44", "23:47", "23:50", "23:53", "23:56", "23:59", "00:02", "00:05", "00:08", "00:11", "00:14", "00:17", "00:20", "00:23", "00:26", "00:29", "00:32", "00:35", "00:38", "00:41", "00:44", "00:47", "00:50", "00:53", "00:56", "00:59", "01:02"};

    public static Double[] happyReturnRate = new Double[]{216.0,72.0,36.0,21.6,14.4,10.29,8.64,8.0,8.0,8.64,10.29,14.4,21.6,36.0,72.0,216.0};
    public static Integer[] happyFillNum = new Integer[]{1,3,6,10,15,21,25,27,27,25,21,15,10,6,3,1};



}
