package com.mascot.utils;


/**
 * 常量类
 * Created by liu on 2017/8/9.
 */
public interface Constant {

    // 其他服务信息
    String FILE_SYSTEM_HEAD = "http://180.168.166.119:8080/filesystem/download";
    String FILE_HEAD_TAG = "<FILE_SYSTEM_HEAD/>";

    // 线程数
    Integer secureServiceThreadCount = 5;


    // 文件路径
    // server
//    String KeyStore = "/Users/liujinren/Documents/workspace/java/filesystem/src/main/resources/jks/icekeystore.jks";
//    String KeyStore = "F:\\project\\udc\\GAEC\\EnterpriseServer\\trunk\\src\\main\\resources\\jks\\icekeystore.jks";
    String KeyStore = "/home/server/certificate/icekeystore.jks";
    // client
//    String JksPath = "/Users/liujinren/Documents/workspace/java/filesystem/src/main/resources/jks/icetruststore.jks";
//    String JksPath = "F:\\project\\udc\\GAEC\\EnterpriseServer\\trunk\\src\\main\\resources\\jks\\icetruststore.jks";
    String JksPath = "/home/server/certificate/icetruststore.jks";

    // usercenter
    String jksPwd = "udcjerry";


}
