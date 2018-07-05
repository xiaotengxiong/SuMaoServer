package com.iscas.component.intfaces;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.iscas.component.core.LoadCacheManager;
import com.iscas.component.core.intfs.ComponentIntface;
import com.iscas.component.services.test.bean.Test;

/**
 * @author adams 测试接口类
 */
@Repository("testIntface")
public class TestIntface implements ComponentIntface<Test> {
    @Autowired
    private LoadCacheManager loadCacheManager;

    public int updateData(Test e) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int deleteData(Test e) {
        // TODO Auto-generated method stub
        return 0;
    }

    public int insertData(Test e) {
        // TODO Auto-generated method stub
        return 0;
    }

    public List<Test> selectDateAsList(Test e) {
        List<Test> testList = loadCacheManager.getCacheTestInfo();
        return loadCacheManager.getCacheTestInfo();
    }
}
