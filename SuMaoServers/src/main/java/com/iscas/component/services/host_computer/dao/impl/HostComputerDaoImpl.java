package com.iscas.component.services.host_computer.dao.impl;

import com.iscas.component.core.PagerModel;
import com.iscas.component.core.dao.BaseDao;
import com.iscas.component.services.host_computer.bean.HostComputer;
import com.iscas.component.services.host_computer.dao.HostComputerDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2018/2/1
 */
@Repository("hostComputerDao")
public class HostComputerDaoImpl implements HostComputerDao {
    @Resource
    private BaseDao baseDao;

    public void setDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    @Override
    public int insert(HostComputer hostComputer) {
        return this.baseDao.insert("host_computer.insert",hostComputer);
    }

    @Override
    public int delete(HostComputer hostComputer) {
        return 0;
    }

    @Override
    public int update(HostComputer hostComputer) {
        return 0;
    }

    @Override
    public HostComputer selectOne(HostComputer hostComputer) {
        return null;
    }

    @Override
    public PagerModel selectPageList(HostComputer hostComputer) {
        return null;
    }

    @Override
    public List<HostComputer> selectList(HostComputer hostComputer) {
        return null;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }

    @Override
    public HostComputer selectById(String id) {
        return null;
    }
}
