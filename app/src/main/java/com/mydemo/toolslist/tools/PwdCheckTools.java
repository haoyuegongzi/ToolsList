package com.mydemo.toolslist.tools;

public class PwdCheckTools {

    /**
     *字符串至少包含大小写字母及数字中的一种
     * @param str
     * @return
     */
    public static boolean isLetterOrDigit(String str) {
        //定义一个boolean值，用来表示是否包含字母或数字
        boolean isLetterOrDigit = false;
        for (int i = 0; i < str.length(); i++) {
            //用char包装类中的判断数字的方法判断每一个字符
            if (Character.isLetterOrDigit(str.charAt(i))) {
                isLetterOrDigit = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isLetterOrDigit && str.matches(regex);
        return isRight;
    }

    /**
     * 字符串至少包含大小写字母及数字中的两种
     * @param str
     * @return
     */
    public static boolean isLetterDigit(String str) {
        //定义一个boolean值，用来表示是否包含数字
        boolean isDigit = false;
        //定义一个boolean值，用来表示是否包含字母
        boolean isLetter = false;
        for (int i = 0; i < str.length(); i++) {
            //用char包装类中的判断数字的方法判断每一个字符
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
            //用char包装类中的判断字母的方法判断每一个字符
            } else if (Character.isLetter(str.charAt(i))) {
                isLetter = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLetter && str.matches(regex);
        return isRight;
    }

    /**
     *字符串必须同时包含大小写字母及数字
     * @param str
     * @return
     */
    public static boolean isContainAll(String str) {
        //定义一个boolean值，用来表示是否包含数字
        boolean isDigit = false;
        //定义一个boolean值，用来表示是否包含字母
        boolean isLowerCase = false;
        boolean isUpperCase = false;
        for (int i = 0; i < str.length(); i++) {
            //用char包装类中的判断数字的方法判断每一个字符
            if (Character.isDigit(str.charAt(i))) {
                isDigit = true;
            //用char包装类中的判断字母的方法判断每一个字符
            } else if (Character.isLowerCase(str.charAt(i))) {
                isLowerCase = true;
            } else if (Character.isUpperCase(str.charAt(i))) {
                isUpperCase = true;
            }
        }
        String regex = "^[a-zA-Z0-9]+$";
        boolean isRight = isDigit && isLowerCase && isUpperCase && str.matches(regex);
        return isRight;
    }
}
