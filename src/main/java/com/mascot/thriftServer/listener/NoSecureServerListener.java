package com.mascot.thriftServer.listener;

import com.mascot.thriftServer.MascotServiceHandler;
import com.mascot.thriftServer.thread.NoSecureServer;
import com.mascot.utils.common.SpringBean;
import com.thrift.mascot.server.MascotService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Observable;
import java.util.Observer;

public class NoSecureServerListener implements Observer {

    private static final Log logger = LogFactory.getLog(NoSecureServerListener.class);

    @Override
    public void update(Observable o, Object arg) {
        logger.info("NoSecureServer 服务关闭, 重启中...");
        MascotServiceHandler enterpriseServiceHandler = (MascotServiceHandler) SpringBean.getObject("yjtServiceHandler");
        MascotService.Processor processor = new MascotService.Processor<>(enterpriseServiceHandler);

        NoSecureServer noSecureServer = new NoSecureServer(processor);
        noSecureServer.addObserver(this);
        new Thread(noSecureServer).start();
        logger.info("重启成功....");
    }

}
