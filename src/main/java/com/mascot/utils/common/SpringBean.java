package com.mascot.utils.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

/**
 * spring 对象管理类
 * Created by jerry on 2017/3/2.
 */
public class SpringBean implements ApplicationContextAware, BeanDefinitionRegistryPostProcessor, ApplicationListener, DisposableBean {

    private static final Log logger = LogFactory.getLog(SpringBean.class);
    private static BeanDefinitionRegistry registry;
    private static ConfigurableListableBeanFactory beanFactory;
    /**
     * 当前IOC
     */
    private static ApplicationContext applicationContext;


    @Override
    public void setApplicationContext(ApplicationContext arg0) throws BeansException {
        applicationContext = arg0;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }

    /**
     * 获取spring
     *
     * @param id
     * @return
     */
    public static Object getObject(String id) {
        Object object = null;
        object = applicationContext.getBean(id);
        return object;
    }


    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        SpringBean.registry = registry;

        // connect config server
//        UdcRpcUtils udcRpcUtils = UdcRpcUtils.instance();
        //

    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        SpringBean.beanFactory = beanFactory;

    }



    @Override
    public void onApplicationEvent(ApplicationEvent event) {

    }

    @Override
    public void destroy() throws Exception {
        logger.info("------->>>> destroy <<<<<------");
//        UdcRpcUtils.instance().destroy();

    }
}

