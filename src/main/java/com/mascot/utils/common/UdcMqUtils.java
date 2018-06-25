package com.mascot.utils.common;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.thrift.common.body.SystemLogMessage;
import com.thrift.common.body.UserInfo;
import com.thrift.common.define.LogType;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeoutException;

@UdcServerComponent(instance = "instance", init = "connectMq")
public class UdcMqUtils {

    private final static String QUEUE_NAME="udc.log.test";
    private static Channel channel = null;
    private static Connection connection = null;

    private UdcMqUtils(){
    }
    private static class SingletonCaseInstance {
        private static UdcMqUtils instance = new UdcMqUtils();
    }
    public static UdcMqUtils instance() {
        return SingletonCaseInstance.instance;
    }


    private static void connectMq() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(System.getProperty("mq.host"));
        factory.setPort(Integer.valueOf(System.getProperty("mq.port")));
        factory.setUsername(System.getProperty("mq.username"));
        factory.setPassword(System.getProperty("mq.password"));
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
    }

    private static void closeMq() throws IOException, TimeoutException {
        if (channel != null)
            channel.close();
        if (connection != null)
            connection.close();
    }

    /**
     * 发送日志信息
     * @param userInfo
     * @param logType
     * @param logJsonContent
     * @param attrs
     * @throws IOException
     */
    public void sendMessage(UserInfo userInfo, Integer logType, String logJsonContent, Map<String, String> attrs) throws IOException {
        SystemLogMessage systemLogMessage = new SystemLogMessage();
        systemLogMessage.setSystemCode(UdcRpcUtils.instance().getCurrentServerCode());
        systemLogMessage.setUserInfo(userInfo);
        systemLogMessage.setOprationTime(new Date().getTime());
        systemLogMessage.setLogType(logType);
        systemLogMessage.setLogContent(logJsonContent);
        systemLogMessage.setAttrs(attrs);
        channel.basicPublish("", QUEUE_NAME, null, UdcMqUtils.serialize(systemLogMessage));
    }

    private static byte[] serialize(SystemLogMessage obj) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        TTransport transport = new TIOStreamTransport(out);
        // TCompactProtocol tp = new TCompactProtocol (transport);
        TBinaryProtocol tp = new TBinaryProtocol(transport);//二进制编码格式进行数据传输

        try {
            obj.write(tp);
        } catch (TException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }



}
