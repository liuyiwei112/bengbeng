package cn.liuyw.bengbeng.utils;

import cn.liuyw.bengbeng.constants.OpenNoConstant;

/**
 * Created by liuyw on 19/2/27.
 */
public class OpenNoUtils {

    public static void calSgsRate(Double[] rateArr){
        int maxTenNum = 0,calNum = 0;
        for (int i = 0; i < rateArr.length; i++) {
            if(rateArr[i]<10){
                rateArr[i] = 100.0;
            }
        }
    }

    /**
     * 替换SGS中赔率最小的2个数字
     *
     * @param rateArr
     */
    public static void replaceSgsMinRate(Double[] rateArr) {
        Integer minIndex = 0;
        Double minValue = 100.0;
        for (int i = 0; i < rateArr.length; i++) {
            if (rateArr[i] < minValue) {
                minIndex = i;
                minValue = rateArr[i];
            }
        }
        rateArr[minIndex] = 100.0;
    }

    /**
     * 替换PK5(杂,对)中赔率最小的1个
     *
     * @param rateArr
     */
    public static void replacePk5MinRate(Double[] rateArr) {
        Integer minIndex = 0;
        Double minValue = 1000.0;
        for (int i = 0; i < rateArr.length; i++) {
//            if (i !=0  && i != 2 && i != 3) {
                Double percents = (rateArr[i] - OpenNoConstant.pk5ReturnRate[i]) / OpenNoConstant.pk5ReturnRate[i];
                if (percents < minValue) {
                    minIndex = i;
                    minValue = percents;
                }
//            }
        }
        rateArr[minIndex] = 1000.0;
    }

    public static int calPk5LeftNoCost(Double[] rateArr){
        int totalCost = 0;
        for (int i = 0; i < rateArr.length; i++) {
            if(rateArr[i]!=1000){
                totalCost += OpenNoConstant.pk5FillNum[i];
            }
        }
        return totalCost;
    }

    /**
     * 替换Happy8-13中赔率最小的2个数字
     *
     * @param rateArr
     */
    public static void replaceHappyMinRate(Double[] rateArr) {
        Integer minIndex = 0;
        Double minValue = 1000.0;
        for (int i = 0; i < rateArr.length; i++) {
//            if (i > 4 && i < 11) {
                Double percents = (rateArr[i] - OpenNoConstant.happyReturnRate[i]) / OpenNoConstant.happyReturnRate[i];
                if (percents < minValue) {
                    minIndex = i;
                    minValue = percents;
                }
//            }
        }
        rateArr[minIndex] = 1000.0;
    }

    public static int calHappyLeftNoCost(Double[] rateArr){
        int totalCost = 0;
        for (int i = 0; i < rateArr.length; i++) {
            if(rateArr[i]!=1000){
                totalCost += OpenNoConstant.happyFillNum[i];
            }
        }
        return totalCost;
    }

    /**
     * 替换Happy6-22中赔率最大的2个数字
     *
     * @param rateArr
     */
    public static void replaceLuckMinRate(Double[] rateArr) {
        Integer minIndex = 0;
        Double minValue = 10000.0;
        for (int i = 0; i < rateArr.length; i++) {
            Double percents = (rateArr[i] - OpenNoConstant.luckReturnRate[i]) / OpenNoConstant.luckReturnRate[i];
            if (percents < minValue) {
                minIndex = i;
                minValue = percents;
            }
        }
        rateArr[minIndex] = 10000.0;
    }

    public static int calLuckLeftNoCost(Double[] rateArr){
        int totalCost = 0;
        for (int i = 0; i < rateArr.length; i++) {
            if(rateArr[i]!=10000){
                totalCost += OpenNoConstant.luckFillNum[i];
            }
        }
        return totalCost;
    }
}
