package com.cqp.cqprpc.service;

import com.cqp.cqprpc.controller.Student;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName StudentService.java
 * @Description TODO
 * @createTime 2021年11月20日 13:46:00
 */

public interface StudentService {
    public Student getStudent(int id);

    Integer getStudentId(String name);
}
