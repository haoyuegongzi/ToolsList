package com.mydemo.toolslist.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.*;

public class StringTools {

    public static boolean isEmpty(final String s) {
        return (null == s || s.trim().length() == 0);
    }

    /**
     * 判断两字符串忽略大小写是否相等
     * @Name equalsIgnoreCase
     * @Description TODO
     * @param a
     * @param b
     * @return
     * @return boolean
     * @Author chen1
     * @Date 2019年8月1日 下午2:21:53
     *
     */
    public static boolean equalsIgnoreCase(final String a, final String b) {
        return a == null ? b == null : a.equalsIgnoreCase(b);
    }

    /**
     * 首字母转为大写
     * @Name upperFirstLetter
     * @Description TODO
     * @param s
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:22:42
     *
     */
    public static String upperFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     *  首字母转为小写
     * @Name lowerFirstLetter
     * @Description TODO
     * @param s
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:23:48
     *
     */
    public static String lowerFirstLetter(final String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) {
            return s;
        }
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     * @Name reverse
     * @Description TODO
     * @param s
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:24:17
     *
     */
    public static String reverse(final String s) {
        int len = s == null ? 0 : s.length();
        if (len <= 1) {
            return s;
        }
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 返回字符串长度
     * @Name length
     * @Description TODO
     * @param s
     * @return
     * @return int
     * @Author chen1
     * @Date 2019年8月1日 下午2:24:40
     *
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 判断是否是身份证号
     * @Name isCardId
     * @Description TODO
     * @param cardId
     * @return
     * @return boolean
     * @Author chen1
     * @Date 2019年8月1日 下午2:25:51
     *
     */
    public static boolean isCardId(String cardId) {
        //定义判别用户身份证号的正则表达式（要么是15位，要么是18位，最后一位可以为字母）
        Pattern idNumPattern = compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");
        //通过Pattern获得Matcher
        Matcher idNumMatcher = idNumPattern.matcher(cardId);
        //判断用户输入是否为身份证号
        return idNumMatcher.matches();
    }

    /**
     * 截取手机号前3位和后4位(其余显示*号)
     * @Name showMobileCiphertext
     * @Description TODO
     * @param mobile
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:26:18
     *
     */
    public static String showMobileCiphertext(String mobile) {
        String newNumber = "****";
        if (!isEmpty(mobile)) {
            return mobile.substring(0, 3) + newNumber + mobile.substring(mobile.length() - 4);
        }
        return newNumber;
    }

    /**
     * 银行卡卡号脱敏
     * @Name showCardNumb
     * @Description TODO
     * @param cardNo
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:27:07
     */
    public static String showCardNumb(String cardNo) {
        String newNumber = "****";
        if (!isEmpty(cardNo)) {
            newNumber = cardNo.substring(0, 4) + "****" + cardNo.substring(cardNo.length() - 4);
            return newNumber;
        }
        return newNumber;
    }

    /**
     * 返回银行卡号最后四位
     * @Name cardLastFourNumb
     * @Description TODO
     * @param cardNo
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:27:37
     *
     */
    public static String cardLastFourNumb(String cardNo) {
        String newNumber = "****";
        if (!isEmpty(cardNo)) {
            newNumber = cardNo.substring(cardNo.length() - 4);
            return newNumber;
        }
        return newNumber;
    }

    /**
     * 去掉字符串中包含的 “，”中文逗号、“,”英文逗号、“ ”空格号
     * @Name replaceString
     * @Description TODO
     * @param data
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:28:39
     */
    public static String replaceString(String data) {
        if (isEmpty(data)) {
            return "0.00";
        }
        if (data.contains("，")) {
            data = data.replace("，", "");
        }

        if (data.contains(" ")) {
            data = data.replace(" ", "");
        }

        if (data.contains(",")) {
            data = data.replace(",", "");
        }

        if (data.contains("￥")) {
            data = data.replace("￥", "");
        }

        if (data.contains("¥")) {
            data = data.replace("¥", "");
        }

        return data;
    }

    /**
     * 去掉字符串中包含的 “，”中文逗号、“,”英文逗号、“ ”空格号、“￥”-“¥”人民币符号、“%”百分号
     * @Name clearFormatFlag
     * @Description TODO
     * @param money
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:37:43
     *
     */
    public static String clearFormatFlag(String money) {
        if (isEmpty(money)) {
            return "0.00";
        } else {
            return money.replace("￥", "").replace("¥", "").replace(",", "").replace(" ", "").replace("%", "").replace("，%", "");
        }
    }

    /**
     * 字符串每4位加空格 隔开
     * @Name addSpace
     * @Description TODO
     * @param str
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:33:11
     *
     */
    public static String addSpace(String str) {
        if (isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(str);
        for (int i = 4; i < sb.length(); i += 5) {
            sb.insert(i, ' ');
        }
        return sb.toString();
    }

    /**
     * 替换字符串中的特殊符号
     * @Name getUnitMoney
     * @Description TODO
     * @param money
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:34:12
     *
     */
    public static String getUnitMoney(String money) {
        if (isEmpty(money)) {
            return "";
        } else {
            return "¥ " + money.replace("￥", "").replace("¥", "");
        }
    }

    /**
     * 字符串是否是标准的手机号码
     * @Name isPhnoeNumber
     * @Description TODO
     * @param s
     * @return
     * @return boolean
     * @Author chen1
     * @Date 2019年8月1日 下午2:40:32
     *
     */
    public static boolean isPhnoeNumber(String s) {
        boolean isPhnoeNumber = false;
        if (null != s && s.trim().length() == 11 && s.startsWith("1") && !s.startsWith("12")) {
            isPhnoeNumber = true;
        }
        return isPhnoeNumber;
    }

    /**
     * 在数字型字符串千分位加逗号
     * @Name addComma
     * @Description TODO
     * @param str
     * @return
     * @return String
     * @Author chen1
     * @Date 2019年8月1日 下午2:41:36
     *
     */
    public static String addComma(String str) {
        boolean neg = false;
        if (str.startsWith("-")) {//处理负数
            str = str.substring(1);
            neg = true;
        }
        String tail = null;
        if (str.indexOf('.') != -1) {//处理小数点
            tail = str.substring(str.indexOf('.'));
            str = str.substring(0, str.indexOf('.'));
        }
        StringBuilder sb = new StringBuilder(str);
        sb.reverse();
        for (int i = 3; i < sb.length(); i += 4) {
            sb.insert(i, ',');
        }
        sb.reverse();
        if (neg) {
            sb.insert(0, '-');
        }
        if (tail != null) {
            sb.append(tail);
        }
        return sb.toString();
    }
}
