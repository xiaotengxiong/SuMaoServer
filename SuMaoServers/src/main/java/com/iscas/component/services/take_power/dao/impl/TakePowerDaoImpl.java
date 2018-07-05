package com.iscas.component.services.take_power.dao.impl;

import com.iscas.component.core.PagerModel;
import com.iscas.component.core.dao.BaseDao;
import com.iscas.component.services.take_power.bean.TakePower;
import com.iscas.component.services.take_power.dao.TakePowerDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:33
 */
@Repository("takePowerDao")
public class TakePowerDaoImpl implements TakePowerDao {
    @Resource
    private BaseDao baseDao;

    public void setDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    public int insert(TakePower takePower) {
        return baseDao.insert("take_power.insert", takePower);
    }

    public int delete(TakePower takePower) {
        return 0;
    }

    public int update(TakePower takePower) {
        return 0;
    }

    public TakePower selectOne(TakePower takePower) {
        return null;
    }

    public PagerModel selectPageList(TakePower takePower) {
        return null;
    }

    public List<TakePower> selectList(TakePower takePower) {
        return null;
    }

    public int deleteById(int id) {
        return 0;
    }

    public TakePower selectById(String id) {
        return null;
    }

    @Override
    public int updateTopOne(TakePower takePower) {
        return baseDao.update("take_power.updateTopOne", takePower);
    }

    @Override
    public int updateHostAll(TakePower takePower) {
        return this.baseDao.update("take_power.updateHostAll", takePower);
    }

    @Override
    public int updateOneFast(TakePower takePower) {
        return this.baseDao.update("take_power.updateOneFast", takePower);
    }


}
