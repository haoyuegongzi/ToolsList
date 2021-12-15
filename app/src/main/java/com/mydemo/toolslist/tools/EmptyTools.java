package com.mydemo.toolslist.tools;

import java.util.List;
import java.util.Map;

/**
 * 作者：Created by chen1 on 2020/3/20.
 * 邮箱：chen126jie@163.com
 * TODO：
 */
public class EmptyTools {
    public static boolean strIsNull(String s) {
        return (null == s || s.isEmpty() || s.trim().isEmpty());
    }

    public static <T> boolean listIsNull(List<T> list) {
        return (null == list || list.isEmpty());
    }

    public static <T> boolean mapIsNull(Map<T, T> map) {
        return (null == map || map.isEmpty());
    }

    public static <T> boolean arrayIsNull(T[] t) {
        return (null == t || t.length == 0);
    }

    public static <T> boolean objectIsNull(T[] t) {
        return null == t || t.length == 0;
    }


}
