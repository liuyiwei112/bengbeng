package cn.liuyw.bengbeng.bean;

/**
 * Created by liuyw on 19/2/19.
 */
public class OpenNoSummary {

    private String openDay;
    private Integer totalNum;
    private float summaryValue;
    private String guessType;
    private Integer summaryType;
    private Integer summaryCount;

    public String getOpenDay() {
        return openDay;
    }

    public void setOpenDay(String openDay) {
        this.openDay = openDay;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getSummaryType() {
        return summaryType;
    }

    public void setSummaryType(Integer summaryType) {
        this.summaryType = summaryType;
    }

    public String getGuessType() {
        return guessType;
    }

    public void setGuessType(String guessType) {
        this.guessType = guessType;
    }

    public Integer getSummaryCount() {
        return summaryCount;
    }

    public void setSummaryCount(Integer summaryCount) {
        this.summaryCount = summaryCount;
    }

    public float getSummaryValue() {
        return summaryValue;
    }

    public void setSummaryValue(float summaryValue) {
        this.summaryValue = summaryValue;
    }
}
