package com.iscas.component.services.take_power;

import com.iscas.component.core.services.Services;
import com.iscas.component.services.take_power.bean.TakePower;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:23
 */
public interface TakePowerService extends Services<TakePower> {
    /**
     * 更新数据
     *
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
