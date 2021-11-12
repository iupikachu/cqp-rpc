package com.cqp.cqprpc.server;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName Server.java
 * @Description Rpc 服务端抽象类
 * @createTime 2021年11月12日 09:22:00
 */
public abstract class Server {

    // 服务端口
    protected int port;

    // 服务端启动
    public abstract void start();

    // 服务端关闭
    public abstract void stop();

    public Server(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
