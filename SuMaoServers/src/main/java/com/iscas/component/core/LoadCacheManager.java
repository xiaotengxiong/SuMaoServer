package com.iscas.component.core;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iscas.component.services.test.TestService;
import com.iscas.component.services.test.bean.Test;

/**
 * @author adams
 * 初始化缓存对象
 */
@Repository("loadCacheManager")
public class LoadCacheManager {
	@Autowired
	private TestService testService;
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	public void setCacheManager(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	/**
	 * 加载全部的缓存数据
	 * 
	 * @throws Exception
	 */
	public void loadAllCache() {
		setCacheTestInfo();
		System.out.println(getCacheTestInfo());
	}

	/**
	 * 初始化缓存数据
	 */
	public void setCacheTestInfo() {
		List<Test> testList = testService.selectList(null);
		cacheManager.putCacheObject("testList", (Serializable) testList);
	}

	/**
	 * @return 缓存数据
	 */
	public List<Test> getCacheTestInfo() {
		return cacheManager.getCacheObject("testList");
	}
}
