package com.iscas.component.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.LoggerFactory;

import com.iscas.component.core.intfs.IscasCacheProvider;

public class CacheManager {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CacheManager.class);
    private static Properties p = new Properties();
    private static IscasCacheProvider cacheProvider;
    private static CacheManager instance;

    @PostConstruct
    public void afterPropertiesSet() {
        instance = this;
    }

    /**
     * 管理后台页面设置
     */
    public static Map<String, String> manageExpressMap = new HashMap<String, String>(); // 后台发货页面物流公司列表

    public static CacheManager getInstance() {
        return instance;
    }

    static {
        init();
    }

    /**
     * 实例化逻辑
     */
    private static void init() {
        // 需要实例化的逻辑
    }

    private static String buildKey(String key) {
        return "iscasComponent." + StringUtils.trimToEmpty(key);
    }

    /**
     * 添加缓存信息
     *
     * @param key
     * @param cacheObject
     */
    public static void putCacheObject(String key, Serializable cacheObject) {
        String keyU = buildKey(key);
        if (cacheObject == null) {
            cacheProvider.remove(keyU);
        } else {
            cacheProvider.put(keyU, cacheObject);
        }
    }


    /**
     * 获取缓存信息
     *
     * @param key
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T getCacheObject(String key) {
        return (T) cacheProvider.get(buildKey(key));
    }

    public String getProperty(String key) {
        return p.getProperty(key);
    }

    // 应用缓存管理
    public IscasCacheProvider getCacheProvider() {
        return cacheProvider;
    }

    public void setCacheProvider(IscasCacheProvider cacheProvider) {
        logger.info("开始实例化缓存对象================");
        CacheManager.cacheProvider = cacheProvider;
    }

}
