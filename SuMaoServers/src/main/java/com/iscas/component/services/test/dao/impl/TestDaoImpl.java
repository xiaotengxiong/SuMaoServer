package com.iscas.component.services.test.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.iscas.component.core.PagerModel;
import com.iscas.component.core.dao.BaseDao;
import com.iscas.component.services.test.bean.Test;
import com.iscas.component.services.test.dao.TestDao;

@Repository("testDao")
public class TestDaoImpl implements TestDao {
	@Resource
	private BaseDao	baseDao;
	public void setDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public int insert(Test e) {
		return baseDao.insert( "test.insert", e );
	}

	public List<Test> selectList(Test e) {
		// TODO Auto-generated method stub
		return baseDao.selectList( "test.select.list", e );
	}

	public int delete(Test e) {
		// TODO Auto-generated method stub
		return 0;
	}

	public int update(Test e) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Test selectOne(Test e) {
		// TODO Auto-generated method stub
		return null;
	}

	public PagerModel selectPageList(Test e) {
		// TODO Auto-generated method stub
		return null;
	}

	public int deleteById(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Test selectById(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
