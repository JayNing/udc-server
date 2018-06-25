package com.mascot.thriftServer.thread;

import com.mascot.utils.common.UdcRpcUtils;
import com.thrift.mascot.server.MascotService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;

import java.util.Observable;

public class NoSecureServer extends Observable implements Runnable{

    private static final Log logger = LogFactory.getLog(NoSecureServer.class);

    private static TServer noSecureServer = null;
    private static Boolean notification = true;

    private MascotService.Processor processor;

    public NoSecureServer(MascotService.Processor processor){
        this.processor = processor;
    }

    @Override
    public void run() {

        TServerTransport serverTransport = null;
        try {
            serverTransport = new TServerSocket(Integer.valueOf(UdcRpcUtils.instance().getAppProperties().getProperty("env.config.me.server.port.thrift.simple")));
            // Use this for a multithreaded server
            noSecureServer = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
            logger.info("Starting the simple server... ººººººººº");
            noSecureServer.serve();
        } catch (TTransportException e) {
            logger.error("simple 启动异常!", e);
        }
        if (notification){
            super.setChanged();
            notifyObservers();
        }
    }

    public static void stop(){
        if (noSecureServer != null){
            noSecureServer.stop();
            noSecureServer = null;
        }
    }

    public static void setNotification(Boolean notification) {
        NoSecureServer.notification = notification;
    }
}
