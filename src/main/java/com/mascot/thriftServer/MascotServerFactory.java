package com.mascot.thriftServer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 　Thrift 服务
 *
 * Created by Jerry on 2018/1/23.
 */
//@Component
public class MascotServerFactory implements InitializingBean, DisposableBean {

    private final static Log logger = LogFactory.getLog(MascotServerFactory.class);

    @Autowired
    private MascotServiceHandler mascotServiceHandler;

    @Override
    public void afterPropertiesSet() throws Exception {

        try {

            logger.info("start thrift service ....");
            ServerEngine.start(mascotServiceHandler);
            logger.info("start thrift service ok");
        }catch (Exception e){
            logger.error("thrift service start failed", e);
        }

    }


    @Override
    public void destroy() throws Exception {

        logger.info("destroy thrift service");
        try {
            ServerEngine.stop();
        }catch (Exception e){
            logger.error("停止服务失败！", e);
        }
    }
}
