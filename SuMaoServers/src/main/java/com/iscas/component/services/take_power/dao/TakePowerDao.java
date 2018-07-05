package com.iscas.component.services.take_power.dao;


import com.iscas.component.core.dao.DaoManager;
import com.iscas.component.services.take_power.bean.TakePower;

public interface TakePowerDao extends DaoManager<TakePower> {
    /**
     * 更新数据
     * @param takePower
     * @return
     */
    public int updateTopOne(TakePower takePower);

    /**
     * @param takePower
     * @return
     */
    public int updateOneFast(TakePower takePower);

    /**
     * @param takePower
     * @return
     */
    public int updateHostAll(TakePower takePower);
}