package com.cqp.cqprpc.controller;

import java.io.Serializable;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName Student.java
 * @Description TODO
 * @createTime 2021年11月20日 13:46:00
 */
public class Student implements Serializable {
    private String name;
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
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
