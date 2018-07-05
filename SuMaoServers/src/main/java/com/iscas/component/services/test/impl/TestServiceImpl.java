package com.iscas.component.services.test.impl;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.iscas.component.core.PagerModel;
import com.iscas.component.core.services.ServersManager;
import com.iscas.component.services.test.TestService;
import com.iscas.component.services.test.bean.Test;
import com.iscas.component.services.test.dao.TestDao;

@Service("testService")
public class TestServiceImpl extends ServersManager<Test, TestDao> implements
		TestService {
	@Resource(name = "testDao")
	@Override
	public void setDao(TestDao testDao) {
		this.dao = testDao;
	}
	@Override
	public int insert(Test e) {
		return dao.insert(e);
	}

	@Override
	public int delete(Test e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deletes(String[] ids) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Test e) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Test selectOne(Test e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Test selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PagerModel selectPageList(Test e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Test> selectList(Test e) {
		// TODO Auto-generated method stub
		return dao.selectList(e);
	}
}
