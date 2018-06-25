package com.mascot.thriftServer;

import com.mascot.thriftServer.listener.NoSecureServerListener;
import com.mascot.thriftServer.thread.NoSecureServer;
import com.thrift.mascot.server.MascotService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * thrift
 * Created by Jerry on 2018/1/23.
 */
public class ServerEngine {

    private static final Log logger = LogFactory.getLog(ServerEngine.class);

    public static void start(MascotServiceHandler yjtServiceHandler) throws Exception {



        MascotService.Processor processor = new MascotService.Processor<MascotService.Iface>(yjtServiceHandler);

        // 非安全
        // 非安全
        NoSecureServer noSecureServer = new NoSecureServer(processor);
        NoSecureServerListener noSecureServerListener = new NoSecureServerListener();
        noSecureServer.addObserver(noSecureServerListener);

        Thread thread = new Thread(noSecureServer);
        thread.start();

        // 加密模式
//        Runnable secure = () -> {
//            try {
//                secure(processor);
//            } catch (TTransportException e) {
//                logger.error("启动SSL服务失败", e);
//            }
//        };
//        new Thread(secure).start();

//        SecureServer secureServer = new SecureServer(processor);
//        SecureServerListener secureServerListener = new SecureServerListener();
//        secureServer.addObserver(secureServerListener);
//
//        ExecutorService executors = Executors.newFixedThreadPool(Constant.secureServiceThreadCount);
//        executors.execute(secureServer);
    }


    public static void stop() {

        NoSecureServer.setNotification(false);
        NoSecureServer.stop();


    }



}
