package com.iscas.component.core.intfs;

import java.io.Serializable;

/**
 * @author adams
 * 缓存公共接口
 */
public interface IscasCacheProvider {

    /**
     * 放入cache中
     * @param key
     * @param cacheObject
     */
    public void put(String key, Serializable cacheObject);

    /**
     * 获取放在cache中的内容
     * @param key
     * @return
     */
    public Serializable get(String key);

    /**
     * 清除cache中对应的值
     * @param key
     */
    public void remove(String key);

    /**
     * 清除所有的cache
     */
    public void clear();

}
