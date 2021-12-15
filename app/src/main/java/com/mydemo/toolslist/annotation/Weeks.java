package com.mydemo.toolslist.annotation;

/**
 * @author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist.annotation
 * @ClassName: Weeks
 * @CreateDate: 2020/10/30 11:05
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public enum Weeks {
    MONDAY("星期一", 1), TUESDAY("星期二", 2), WEDNESDAY("星期三", 3),
    THURSDAY("星期四", 4), FRIDAY("星期五", 5), SATURDAY("星期六", 6),
    SUNDAY("星期日", 7);

    // 成员变量
    private String name;
    private int index;

    Weeks(String name, int index) {
        this.name = name;
        this.index = index;
    }
}
