package com.cqp.cqprpc.server.register;

import com.cqp.cqprpc.common.constant.RpcConstant;
import com.cqp.cqprpc.common.protocol.Serializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.bcel.internal.generic.NEW;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author cqp
 * @version 1.0.0
 * @ClassName ZooKeeperRegister.java
 * @Description Zookeeper服务注册器，提供服务注册、服务暴露的能力
 * @createTime 2021年11月18日 14:46:00
 */
@Slf4j
public class ZooKeeperRegister extends DefaultServiceRegister implements ServiceRegister{
    private String RpcServerIP;
    private String RpcServerPort;
    CuratorFramework zookeeper;

    public ZooKeeperRegister(String zkAddress,String rpcServerIP,String rpcServerPort) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(3000, 10);
         zookeeper = CuratorFrameworkFactory.builder()
                .connectString(zkAddress)
                .sessionTimeoutMs(60 * 1000)
                .connectionTimeoutMs(15 * 1000)
                .retryPolicy(retryPolicy)
                .namespace("cqp-rpc")
                .build();
        zookeeper.start();

        this.RpcServerIP = rpcServerIP;
        this.RpcServerPort = rpcServerPort;
    }

    /**
     * 服务注册
     * @param serviceObject 服务持有者
     */
    @Override
    public void register(ServiceObject serviceObject)  {
        super.register(serviceObject);    // 注册到服务端本地缓存中
        ServiceDTO serviceDTO = new ServiceDTO();
        serviceDTO.setInterfaceName(serviceObject.getName());
        serviceDTO.setRpcServerIP(RpcServerPort);
        serviceDTO.setRpcServerPort(RpcServerIP);
        this.exportService(serviceDTO);
    }

    /**
     * 服务暴露
     * @param serviceDTO 装需要暴露服务信息的类，发送给 zookeeper
     */
    public void exportService(ServiceDTO serviceDTO)  {
        String serviceName = serviceDTO.getInterfaceName();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(serviceName);
            String encode = URLEncoder.encode(json, "UTF-8");

            String servicePath = RpcConstant.PATH_DELIMITER + serviceName + "/service";   // /com.cqp.cqprpc.service.HelloService/service

            if(zookeeper.checkExists().forPath(servicePath) == null){
                // 默认创建持久化节点
                String path = zookeeper.create().creatingParentsIfNeeded().forPath(servicePath);
                log.info(path + " 永久节点创建了！");
            }
            String uri = servicePath + RpcConstant.PATH_DELIMITER + encode ;
            if(zookeeper.checkExists().forPath(uri) != null){
                zookeeper.delete().forPath(uri);
            }
            // 作为临时节点注册上去
            String path = zookeeper.create().withMode(CreateMode.EPHEMERAL).forPath(uri);
            log.info(path + "临时节点创建了！");
        }  catch (Exception e) {
            e.printStackTrace();
        }


    }

}
