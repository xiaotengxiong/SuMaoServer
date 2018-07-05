package com.iscas.component.main;

import com.iscas.component.mqtt.MqttServer;
import com.iscas.component.util.SocketServerUtil;
import com.iscas.component.util.SpringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author adams 组件入口类
 */
public class IscasComponentMain {
    private static Logger log = LoggerFactory.getLogger(IscasComponentMain.class);

    /**
     * @param args 组件入口方法
     */
    public static void main(String[] args) {
        log.info("main start run================");
        /**
         * 加载Spring配置文件<br>
         * 创建SpringUtil类统一获取ApplicationContext<br>
         */
          ClassPathXmlApplicationContext ctx = SpringUtil.getSpringContext();
        // LoadCacheManager loadCacheManager = (LoadCacheManager) ctx.getBean("loadCacheManager");
          MqttServer mqttServer = (MqttServer) ctx.getBean("mqttServer");
        // loadCacheManager.loadAllCache();
          mqttServer.init();
        // 加载缓存处理对象
       //  SocketServerUtil socketServerUtil = (SocketServerUtil) ctx.getBean("socketServerUtil");
        // 加载缓存

       //  socketServerUtil.init(ctx);
    }
}
