package com.jsh.erp.plugin;

/**
 * 插件之间互相调用的类
 *
 * @author jishenghua
 * @version 1.0
 */
public class CommonParam {

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CommonParam{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
