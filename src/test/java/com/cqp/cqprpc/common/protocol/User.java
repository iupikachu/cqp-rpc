package com.cqp.cqprpc.common.protocol;

import java.io.Serializable;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName User.java
 * @Description TODO
 * @createTime 2021年11月11日 21:28:00
 */
public class User implements Serializable {
    String name;
    Integer age;

    public User() {
    }

    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

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
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
