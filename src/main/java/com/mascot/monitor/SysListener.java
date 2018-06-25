package com.mascot.monitor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * Created by liu on 2017/8/9.
 */
@WebListener
public class SysListener implements ServletContextListener{

    private static final Log logger = LogFactory.getLog(SysListener.class);

    private static Timer timer;

    private final static Integer threadCount = 5;

    private static ExecutorService executor;

    // 企业属性标签代码
    private static Map<String, String> enterprisePropertyCodeMap;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("======== SysListener ==========");

        // 获取Spring 上下问
//        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        // 定时器
        timer = new Timer(true);

        // 启用线程, 用于执行异步任务
        executor = Executors.newFixedThreadPool(threadCount);

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("======== contextDestroyed ==========");
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        if (executor != null){
            executor.shutdown();
        }
    }

    /**
     * 获取异步任务线程
     * @return
     */
    public static ExecutorService getExecutor() {
        return executor;
    }

}
