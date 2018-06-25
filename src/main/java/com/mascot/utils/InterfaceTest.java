package com.mascot.utils;

import com.interfaces.mascot.AuthorityManager;
import junit.framework.TestCase;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class InterfaceTest extends TestCase {


    public ApplicationContext ctx = null;       // ①Spring容器引用
    private static String[] CONFIG_FILES = {    // ②Spring配置文件
            "classpath:/spring/spring.xml",
    };

    @Override
    protected void setUp() throws Exception {   // ③启动Spring容器
        super.setUp();
        ctx = new FileSystemXmlApplicationContext(CONFIG_FILES);

    }


    public void test_test(){
        AuthorityManager authorityManager = (AuthorityManager) ctx.getBean("authorityManager");

        System.out.println(authorityManager.getTest("test"));

    }


}
