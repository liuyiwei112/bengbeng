package cn.liuyw.bengbeng.utils;

import java.util.ArrayList;

/**
 * @author steven
 * @version 2005-2-2 modified by chengyl at 20060913 增加sql字符串的单引号转换方法
 */
public class StringUtil {

    public static final String CHARSET_UTF8 = "UTF-8";

    public static String valueOf(Object src) {
        String res = null;
        if (src instanceof byte[]) {
            res = new String((byte[]) src);
        } else {
            res = String.valueOf(src);
        }
        return res;
    }

    /**
     * 将一个数组中的内容连接成一个字符串，各个数组中的元素使用参数delim分隔
     *
     * @param arr
     * @param delim
     * @return
     */
    public static String arrayToString(String[] arr, String delim) {
        if (arr == null)
            return "null";
        else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < arr.length; i++) {
                if (i > 0)
                    sb.append(delim);
                sb.append(arr[i]);
            }
            return sb.toString();
        }
    }

    /**
     * 本地字符集转换成unicode
     *
     * @param s
     * @return java.lang.String
     */
    public static String native2unicode(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        byte[] buffer = new byte[s.length()];

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 0x100) {
                return s;
            }
            buffer[i] = (byte) s.charAt(i);
        }
        return new String(buffer);
    }

    /**
     * unicode转为本地字符集 @ param String Unicode编码的字符串 @ return String
     */
    public static String unicode2native(String s) {
        if (s == null || s.length() == 0) {
            return null;
        }
        char[] buffer = new char[s.length() * 2];
        char c;
        int j = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) >= 0x100) {
                c = s.charAt(i);
                byte[] buf = ("" + c).getBytes();
                buffer[j++] = (char) buf[0];
                buffer[j++] = (char) buf[1];
            } else {
                buffer[j++] = s.charAt(i);
            }
        }
        return new String(buffer, 0, j);
    }

    /**
     * 对字符串中制定的字符进行替换
     *
     * @param inString
     * @param oldPattern
     * @param newPattern
     * @return
     */
    public static String replace(String inString, String oldPattern, String newPattern) {
        if (inString == null) {
            return null;
        }
        if (oldPattern == null || newPattern == null) {
            return inString;
        }

        StringBuffer sbuf = new StringBuffer();
        // output StringBuffer we'll build up
        int pos = 0; // Our position in the old string
        int index = inString.indexOf(oldPattern);
        // the index of an occurrence we've found, or -1
        int patLen = oldPattern.length();
        while (index >= 0) {
            sbuf.append(inString.substring(pos, index));
            sbuf.append(newPattern);
            pos = index + patLen;
            index = inString.indexOf(oldPattern, pos);
        }
        sbuf.append(inString.substring(pos));

        // remember to append any characters to the right of a match
        return sbuf.toString();
    }

    /**
     * 替换输入字符串中的HTML字符（如：< > ' \ \r \n）
     *
     * @param old
     * @return
     */
    public static String replaceHtml(String old) {
        String rt = replace(old, "&", "&amp;");
        rt = replace(rt, "<", "&lt;");
        rt = replace(rt, ">", "&gt;");
        rt = replace(rt, "'", "&#39;");
        rt = replace(rt, "\"", "&quot;");
        rt = replace(rt, "\r", "&#xd;");
        rt = replace(rt, "\n", "&#xa;");
        return rt;
    }

    /**
     * sql操作时，单引号(')是一个关键字。如果插入或查询时的字段值里有单引号， 会出错。必须将字段值里的单引号转换成两个单引号。
     *
     * @param old 源字符串
     * @return 转化后的字符串
     */
    public static String replaceSql(String old) {
        String rt = replace(old, "'", "''");
        return rt;
    }

    /**
     * 将list转换成数组String[]
     *
     * @param list
     * @return
     */
    public static String[] list2StrArray(ArrayList list) {

        String strArray[] = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            strArray[i] = (String) list.get(i);
        }
        return strArray;
    }


    /**
     * 检查对象是否为数字型字符串。
     * 只能检查正整数
     */
    public static boolean isNumeric(Object obj) {
        if (obj == null) {
            return false;
        }
        String str = obj.toString();
        int sz = str.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 通过正则表达式,可检查小数、负数
     *
     * @param obj
     * @return
     */
    public static boolean isNumber(Object obj) {
        if (obj == null) {
            return false;
        }
        String str = obj.toString();
        Boolean strResult = str.matches("-?[0-9]+.*[0-9]*");
        if (strResult == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 检查指定的字符串列表是否不为空。
     */
    public static boolean isNotEmpty(String... values) {
        boolean result = true;
        if (values == null || values.length == 0) {
            result = false;
        } else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;
    }

    /**
     * 检查指定的字符串是否为空。
     * <ul>
     * <li>SysUtils.isEmpty(null) = true</li>
     * <li>SysUtils.isEmpty("") = true</li>
     * <li>SysUtils.isEmpty("   ") = true</li>
     * <li>SysUtils.isEmpty("abc") = false</li>
     * </ul>
     *
     * @param value 待检查的字符串
     * @return true/false
     */
    public static boolean isEmpty(String value) {
        int strLen;
        if (value == null || (strLen = value.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(value.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    public static int indexOf(String[] arr,String target){
        for(int i=0;i<arr.length; i++){
            if(arr[i].equals(target)){
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
    }

}
