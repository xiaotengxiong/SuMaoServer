package com.iscas.component.core.cache;

import java.io.Serializable;
import java.util.Map;

import com.google.common.collect.Maps;
import com.iscas.component.core.intfs.IscasCacheProvider;

/**
 * simple cache provider use map
 * @author dylan
 */
public class SimpleCacheProvider implements IscasCacheProvider {
    public SimpleCacheProvider() {

    }
    private static SimpleCacheProvider instance = new SimpleCacheProvider();
    private static Map<String, Serializable> cacheContainer = Maps.newHashMap();
    
    public SimpleCacheProvider getInstance() {
        return instance;
    }

    @Override
    public void put(String key, Serializable cacheObject) {
        cacheContainer.put(key, cacheObject);
    }

    @Override
    public Serializable get(String key) {
        return cacheContainer.get(key);
    }

    @Override
    public void remove(String key) {
        cacheContainer.remove(key);
    }

    @Override
    public void clear() {
        cacheContainer.clear();
    }
}
