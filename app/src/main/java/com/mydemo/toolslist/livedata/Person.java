package com.mydemo.toolslist.livedata;

/**
 * @author: Nixon
 * @ProjectName: ToolsList
 * @Package: com.mydemo.toolslist.livedata
 * @ClassName: Person
 * @CreateDate: 2020/12/29 20:47
 * @Description: 本类作用描述：
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
