package com.cqp.cqprpc.service;

import com.cqp.cqprpc.annotation.RpcService;
import com.cqp.cqprpc.controller.Student;


/**
 * @author cqp
 * @version 1.0.0
 * @ClassName StudentServiceImpl.java
 * @Description TODO
 * @createTime 2021年11月20日 13:49:00
 */

@RpcService
public class StudentServiceImpl implements StudentService{
    @Override
    public Student getStudent(int id) {
        return new Student("cqp",id);
    }

    @Override
    public Integer getStudentId(String name) {
        if(name.equals("cqp")){
            return 1;
        }else{
            return 0;
        }
    }
}
