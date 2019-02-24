package cn.liuyw.bengbeng.bean;

/**
 * Created by liuyw on 17/9/1.
 */
public enum OpenNoLabelEnum {

    PK5_BAO(0,"豹"),
    PK5_DUI(1,"对"),
    PK5_SHUN(2,"顺"),
    PK5_BAN(3,"半"),
    PK5_ZA(4,"杂"),

    SGS_0(0,"吕布"),
    SGS_1(1,"刘备"),
    SGS_2(2,"张飞"),
    SGS_3(3,"关羽"),
    SGS_4(4,"曹操"),
    SGS_5(5,"夏侯惇"),
    SGS_6(6,"司马懿"),
    SGS_7(7,"孙权"),
    SGS_8(8,"孙策"),
    SGS_9(9,"周瑜");

    private Integer code;

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    private String desc;

    OpenNoLabelEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static String getLabelByNum(String guessType,String num){
        if(guessType.equals("pk5")){
            if(num.equals("0")){
                return OpenNoLabelEnum.PK5_BAO.getDesc();
            }else if(num.equals("1")){
                return OpenNoLabelEnum.PK5_DUI.getDesc();
            }else if(num.equals("2")){
                return OpenNoLabelEnum.PK5_SHUN.getDesc();
            }else if(num.equals("3")){
                return OpenNoLabelEnum.PK5_BAN.getDesc();
            }else{
                return OpenNoLabelEnum.PK5_ZA.getDesc();
            }
        }else{
            if(num.equals("0")){
                return OpenNoLabelEnum.SGS_0.getDesc();
            }else if(num.equals("1")){
                return OpenNoLabelEnum.SGS_1.getDesc();
            }else if(num.equals("2")){
                return OpenNoLabelEnum.SGS_2.getDesc();
            }else if(num.equals("3")){
                return OpenNoLabelEnum.SGS_3.getDesc();
            }else if(num.equals("4")){
                return OpenNoLabelEnum.SGS_4.getDesc();
            }else if(num.equals("5")){
                return OpenNoLabelEnum.SGS_5.getDesc();
            }else if(num.equals("6")){
                return OpenNoLabelEnum.SGS_6.getDesc();
            }else if(num.equals("7")){
                return OpenNoLabelEnum.SGS_7.getDesc();
            }else if(num.equals("8")){
                return OpenNoLabelEnum.SGS_8.getDesc();
            }else{
                return OpenNoLabelEnum.SGS_9.getDesc();
            }
        }
    }
}
