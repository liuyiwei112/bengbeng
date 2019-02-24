package cn.liuyw.bengbeng.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyw on 17/8/31.
 */
public class OpenNo implements Serializable {

    private Integer id;
    private String issue;
    private String guessType;
    private String openTime;
    private String openNo;
    private String openNoLabel;
    private Float returnRate;
    private String allReturnRate;
    private String allNo;
    private Float baseRate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getGuessType() {
        return guessType;
    }

    public void setGuessType(String guessType) {
        this.guessType = guessType;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getOpenNo() {
        return openNo;
    }

    public void setOpenNo(String openNo) {
        this.openNo = openNo;
    }

    public Float getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(Float returnRate) {
        this.returnRate = returnRate;
    }

    public String getOpenNoLabel() {
        return openNoLabel;
    }

    public void setOpenNoLabel(String openNoLabel) {
        this.openNoLabel = openNoLabel;
    }

    public Float getBaseRate() {
        return baseRate;
    }

    public void setBaseRate(Float baseRate) {
        this.baseRate = baseRate;
    }

    public String getAllReturnRate() {
        return allReturnRate;
    }

    public void setAllReturnRate(String allReturnRate) {
        this.allReturnRate = allReturnRate;
    }

    public String getAllNo() {
        return allNo;
    }

    public void setAllNo(String allNo) {
        this.allNo = allNo;
    }
}
