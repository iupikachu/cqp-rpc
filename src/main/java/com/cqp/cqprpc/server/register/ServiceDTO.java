package com.cqp.cqprpc.server.register;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ServiceDTO.java
 * @Description zookeeper 传输服务信息的包装类
 * @createTime 2021年11月18日 15:35:00
 */
public class ServiceDTO {
    private String InterfaceName;
    private String RpcServerIP;
    private String RpcServerPort;

    public ServiceDTO() {
    }

    public ServiceDTO(String interfaceName, String rpcServerIP, String rpcServerPort) {
        InterfaceName = interfaceName;
        RpcServerIP = rpcServerIP;
        RpcServerPort = rpcServerPort;
    }

    public String getInterfaceName() {
        return InterfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        InterfaceName = interfaceName;
    }

    public String getRpcServerIP() {
        return RpcServerIP;
    }

    public void setRpcServerIP(String rpcServerIP) {
        RpcServerIP = rpcServerIP;
    }

    public String getRpcServerPort() {
        return RpcServerPort;
    }

    public void setRpcServerPort(String rpcServerPort) {
        RpcServerPort = rpcServerPort;
    }
}
