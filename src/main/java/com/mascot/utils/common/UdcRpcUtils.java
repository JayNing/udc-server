package com.mascot.utils.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;


/**
 * UDC RPC Utils
 *
 * @author liujinren
 * @date 2018-1-16
 */
public class UdcRpcUtils {

    // Mysql 目录
    private final static String mysqlPath = "/data-server/mysql";
    private final static String mongoPath = "/data-server/mongodb";
    private final static String mqPath = "/data-server/mq";
    private final static String serverCodePath = "/config/app-code";
    private static String selfPath = null;

    private static String currentServerCode = null;

    private final static Properties properties = new Properties();

    private static Log logger = LogFactory.getLog(UdcRpcUtils.class.getName());

    private static abstract class AbstractZooKeeper implements Watcher {
        private static final int SESSION_TIME = 2000;
        protected static ZooKeeper zooKeeper = null;
        protected CountDownLatch countDownLatch = new CountDownLatch(1);

        public void connect(String hosts, String[] auth) throws IOException, InterruptedException {
            if (zooKeeper == null || !zooKeeper.getState().isConnected()) {
                zooKeeper = new ZooKeeper(hosts, SESSION_TIME, this);
                if (auth != null && auth.length == 2) {
                    zooKeeper.addAuthInfo("digest", (auth[0] + ":" + auth[1]).getBytes());
                } else {
                    logger.info("zooKeeper auth info is null or error");
                }
                countDownLatch.await();
            }
        }

        public void close() throws InterruptedException {
            if (zooKeeper != null && zooKeeper.getState().isConnected())
                zooKeeper.close();
        }

        @Override
        public void process(WatchedEvent watchedEvent) {
            if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                countDownLatch.countDown();
            }
        }
    }

    public static class ConfigServerOperator extends AbstractZooKeeper {

        private static Stat mysqlStat = null;
        private static Stat mongoStat = null;
        private static Stat mqStat = null;

        private ConfigServerOperator() {
        }

        private static class SingletonCaseInstance {
            private static ConfigServerOperator instance = new ConfigServerOperator();
        }

        public static ConfigServerOperator instance() {
            return SingletonCaseInstance.instance;
        }

        public static ZooKeeper getZookeeper() {
            return zooKeeper;
        }

        public ConfigServerOperator connectConfigServer(String hosts, String username, String passwd) {
            try {
                connect(hosts, new String[]{username, passwd});
            } catch (IOException | InterruptedException e) {
                logger.error("连接配置服务器异常", e);
            }
            return this;
        }

        public ConfigServerOperator configMySQL() {

            try {
                mysqlStat = zooKeeper.exists(mysqlPath, false);
                byte[] data = zooKeeper.getData(mysqlPath, false, mysqlStat);

                logger.info("当前 Mysql 数据版本" + mysqlStat.getVersion());
                logger.info("获取 Mysql 数据" + JSON.parse(new String(data)));

                setMysqlConfig((JSONObject) JSON.parse(new String(data)));

            } catch (KeeperException | InterruptedException e) {
                logger.error("get mysql config data error from config server", e);
            }


            return this;
        }

        public ConfigServerOperator configMongo() {
            try {
                mongoStat = zooKeeper.exists(mongoPath, false);

                byte[] data = zooKeeper.getData(mongoPath, false, mongoStat);

                logger.info("当前 Mongo 数据版本" + mongoStat.getVersion());
                logger.info("获取 Mongo 数据" + JSON.parse(new String(data)));

                setMongoConfig((JSONObject) JSON.parse(new String(data)));
            } catch (Exception e) {
                logger.error("get mongo config data error from config server", e);
            }
            return this;
        }

        public ConfigServerOperator configMq() {
            try {
                mqStat = zooKeeper.exists(mqPath, false);

                byte[] data = zooKeeper.getData(mqPath, false, mqStat);

                logger.info("当前 Mq 数据版本" + mqStat.getVersion());
                logger.info("获取 Mq 数据" + JSON.parse(new String(data)));

                setMqConfig((JSONObject) JSON.parse(new String(data)));
            } catch (Exception e) {
                logger.error("get mongo config data error from config server", e);
            }
            return this;
        }


        public ConfigServerOperator registerConfigServer() {

            try {
                Stat exists = zooKeeper.exists(selfPath, false);
                byte[] data = zooKeeper.getData(selfPath, false, exists);
                logger.info("获取当前数据" + JSON.parse(new String(data)));

                // TODO 注册
                List<String> children = zooKeeper.getChildren(selfPath, false);

                JSONObject me_json = new JSONObject();
                me_json.put("ip.public", properties.getProperty("env.config.me.server.ip.public"));
                me_json.put("ip.intranet", properties.getProperty("env.config.me.server.ip.intranet"));
                me_json.put("port.web.socket", Integer.valueOf(properties.getProperty("env.config.me.server.port.web.socket")));
                me_json.put("port.web.socket", Integer.valueOf(properties.getProperty("env.config.me.server.port.web.socket")));
                me_json.put("port.socket", Integer.valueOf(properties.getProperty("env.config.me.server.port.socket")));
                me_json.put("port.thrift.simple", Integer.valueOf(properties.getProperty("env.config.me.server.port.thrift.simple")));
                me_json.put("port.thrift.secure", Integer.valueOf(properties.getProperty("env.config.me.server.port.thrift.secure")));

                Integer index = 0;
                String mePath = selfPath + "/" + index;
                if (children != null && children.size() > 0) {
                    for (String child : children) {
                        if (Integer.valueOf(child) > index) {
                            index = Integer.valueOf(child);
                        }
                    }
                    mePath = selfPath + "/" + (index + 1);
                }
                me_json.put("desc", mePath + " 用户中心服务子节点");
                String s = zooKeeper.create(mePath, me_json.toJSONString().getBytes(),
                        ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.EPHEMERAL);
                logger.info("注册当前服务返回结果: " + s);

            } catch (Exception e) {
                logger.error("register usercenter config error", e);
            }

            return this;
        }

        public ConfigServerOperator registerServerCode() {

            currentServerCode = UUID.randomUUID().toString();
            try {
                Stat exists = zooKeeper.exists(serverCodePath, false);
                if(exists != null){
                    // 创建子目录
                    String child = serverCodePath + "/" + currentServerCode;
                    String s = zooKeeper.create(child, "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                    logger.info("注册服务码返回: " + s);
                }else {
                    logger.error("不存在目录:" + serverCodePath);
                }
            }catch (Exception e){
                logger.error("注册服务吗失败", e);
            }
            return this;
        }

        public void destroy() {
            try {
                if (zooKeeper != null) {
                    zooKeeper.close();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void setMysqlConfig(JSONObject param) {
            if (param != null) {

                String property = properties.getProperty("env.is.intranet");
                if (property != null && Boolean.valueOf(property)) {
                    logger.info("配置生产环境 Mysql");
                    System.setProperty("mysql.driver", "com.mysql.jdbc.Driver");
                    System.setProperty("mysql.url", "jdbc:mysql://" + param.getString("ip_intranet") + ":" + param.getString("port_intranet") + "/" + properties.getProperty("server.mysql.dbname") + "?useUnicode=true&characterEncoding=utf8");
                    System.setProperty("mysql.username", param.getString("username"));
                    System.setProperty("mysql.password", param.getString("password"));
                    System.setProperty("mysql.initialSize", param.getString("initialSize"));
                    System.setProperty("mysql.maxActive", "20");
                    System.setProperty("mysql.minIdle", "2");

                } else {
                    logger.info("配置测试环境 Mysql");
                    System.setProperty("mysql.driver", "com.mysql.jdbc.Driver");
                    System.setProperty("mysql.url", "jdbc:mysql://" + param.getString("ip_public") + ":" + param.getString("port_public") + "/" + properties.getProperty("server.mysql.dbname") + "?useUnicode=true&characterEncoding=utf8");
                    System.setProperty("mysql.username", param.getString("username"));
                    System.setProperty("mysql.password", param.getString("password"));
                    System.setProperty("mysql.initialSize", param.getString("initialSize"));
                    System.setProperty("mysql.maxActive", "20");
                    System.setProperty("mysql.minIdle", "2");

                }

            }
            System.out.println("MYSQL URL: " + System.getProperty("mysql.url"));
        }

        private void setMongoConfig(JSONObject param) {
            if (param != null) {
                String property = properties.getProperty("env.is.intranet");
                if (property != null && Boolean.valueOf(property)) {
                    logger.info("配置生产环境 Mongo");
                    System.setProperty("mongo.host", param.getString("ip_intranet"));
                    System.setProperty("mongo.port", param.getString("port_intranet"));
                    System.setProperty("mongo.dbname", param.getString("dbname"));
                    System.setProperty("mongo.username", param.getString("username"));
                    System.setProperty("mongo.password", param.getString("password"));
                } else {
                    logger.info("配置测试环境 Mongo");
                    System.setProperty("mongo.host", param.getString("ip_public"));
                    System.setProperty("mongo.port", param.getString("port_public"));
                    System.setProperty("mongo.dbname", param.getString("dbname"));
                    System.setProperty("mongo.username", param.getString("username"));
                    System.setProperty("mongo.password", param.getString("password"));
                }
            }
        }

        private void setMqConfig(JSONObject param) {
            if (param != null) {
                String property = properties.getProperty("env.is.intranet");
                if (property != null && Boolean.valueOf(property)) {
                    logger.info("配置生产环境 MQ");
                    System.setProperty("mq.host", param.getString("ip_intranet"));
                    System.setProperty("mq.port", param.getString("port_intranet"));
                    System.setProperty("mq.username", param.getString("username"));
                    System.setProperty("mq.password", param.getString("password"));
                } else {
                    logger.info("配置测试环境 MQ");
                    System.setProperty("mq.host", param.getString("ip_public"));
                    System.setProperty("mq.port", param.getString("port_public"));
                    System.setProperty("mq.username", param.getString("username"));
                    System.setProperty("mq.password", param.getString("password"));
                }
            }
        }

    }

    public static ConfigServerOperator buildConfigServerOperator() {
        return ConfigServerOperator.instance();
    }


    private UdcRpcUtils() {
        System.out.println("================ 加载系统配置 ================");
        try {
            String proPath = "/properties/system.properties";
            properties.load(this.getClass().getResourceAsStream(proPath));
            // check config

            if (properties.getProperty("env.is.intranet") == null
                    || properties.getProperty("env.config.server.host") == null
                    || properties.getProperty("env.config.server.username") == null
                    || properties.getProperty("env.config.server.password") == null
                    || properties.getProperty("env.config.me.server.ip.intranet") == null
                    || properties.getProperty("env.config.me.server.ip.public") == null
                    || properties.getProperty("env.config.me.server.port.web") == null
                    || properties.getProperty("env.config.me.server.port.web.socket") == null
                    || properties.getProperty("env.config.me.server.port.socket") == null
                    || properties.getProperty("env.config.me.server.port.thrift.simple") == null
                    || properties.getProperty("env.config.me.server.port.thrift.secure") == null
                    ) {
                logger.error("系统配置文件异常：" + proPath, new RuntimeException(proPath + "exception"));
                return;
            }

        } catch (IOException e) {
            logger.error("加载系统配置失败！", e);
        }

        selfPath = properties.getProperty("env.config.me.server.path");

        // 配置必须向
        UdcRpcUtils.buildConfigServerOperator()
                .connectConfigServer(properties.getProperty("env.config.server.host"),
                        properties.getProperty("env.config.server.username"),
                        properties.getProperty("env.config.server.password")) // 连接
                .configMySQL()  //配置
                .configMongo()
                .configMq()
                .registerConfigServer()
                .registerServerCode()
        ;


        // 初始化组建
        String name = UdcRpcUtils.class.getName();
        String[] split = name.split("\\.");
        split[split.length - 1] = "*.class";
        StringBuilder newPath = new StringBuilder();
        newPath.append("classpath*:");
        for (String s : split) {
            newPath.append(s).append("/");
        }
        String packagePath = newPath.toString().substring(0, newPath.length() - 1);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            Resource[] resources = resolver.getResources(packagePath);
            if (resources != null) {
                for (Resource resource : resources) {
                    String className = resource.getURL().getPath();
                    className = className.split("(classes/)|(!/)")[1];
                    className = className.replace("/", ".").replace(".class", "");
                    Class<?> aClass = Class.forName(className);
                    if (aClass.isAnnotationPresent(UdcServerComponent.class)) {
                        UdcServerComponent annotation = aClass.getAnnotation(UdcServerComponent.class);
                        Method instanceMethod = aClass.getMethod(annotation.instance());
                        Object invoke = instanceMethod.invoke(null);
                        Method initMethod = aClass.getDeclaredMethod(annotation.init());
                        initMethod.setAccessible(true);
                        Object invoke1 = initMethod.invoke(null);
                        logger.info("========== 组建服务:" + aClass.getName() + "初始化成功");
                    }
                }
            }
        } catch (Exception e) {
            logger.error("初始化组建失败 ", e);
        }
    }

    private static class SingletonCaseInstance {
        private static UdcRpcUtils instance = new UdcRpcUtils();
    }

    public static UdcRpcUtils instance() {
        return UdcRpcUtils.SingletonCaseInstance.instance;
    }


    public String getCurrentServerCode() {
        return currentServerCode;
    }

    public Properties getAppProperties() {
        return properties;
    }

    public void destroy() {

        UdcRpcUtils.buildConfigServerOperator().destroy();

    }

}
