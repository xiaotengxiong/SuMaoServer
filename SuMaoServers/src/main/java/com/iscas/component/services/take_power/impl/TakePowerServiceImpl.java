package com.iscas.component.services.take_power.impl;

import com.iscas.component.core.services.ServersManager;
import com.iscas.component.services.take_power.TakePowerService;
import com.iscas.component.services.take_power.bean.TakePower;
import com.iscas.component.services.take_power.dao.TakePowerDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:44
 */
@Service("takePowerService")
public class TakePowerServiceImpl extends ServersManager<TakePower, TakePowerDao> implements
        TakePowerService {
    @Resource(name = "takePowerDao")
    @Override
    public void setDao(TakePowerDao takePowerDao) {
        this.dao = takePowerDao;
    }

    @Override
    public int updateTopOne(TakePower takePower) {
        return this.dao.updateTopOne(takePower);
    }

    @Override
    public int updateOneFast(TakePower takePower) {
        return this.dao.updateOneFast(takePower);
    }

    @Override
    public int updateHostAll(TakePower takePower) {
        return this.dao.updateHostAll(takePower);
    }
}
