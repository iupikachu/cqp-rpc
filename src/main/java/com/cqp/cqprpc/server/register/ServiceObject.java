package com.cqp.cqprpc.server.register;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ServiceObject.java
 * @Description 服务持有对象，保存具体的服务信息
 * @createTime 2021年11月18日 10:53:00
 */
public class ServiceObject {
    /**
     * 服务名称   ("cqp.rpc.service.HelloService")
     */
    private String name;

    /**
     * 服务Class （interface: HelloService）
     */
    private Class<?> clazz;

    /**
     * 具体服务 (class: HelloServiceImpl)
     */
    private Object obj;

    public ServiceObject(String name, Class<?> clazz, Object obj) {
        this.name = name;
        this.clazz = clazz;
        this.obj = obj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
