package com.iscas.component.services.host_computer.impl;

import com.iscas.component.core.services.ServersManager;
import com.iscas.component.services.host_computer.HostComputerService;
import com.iscas.component.services.host_computer.bean.HostComputer;
import com.iscas.component.services.host_computer.dao.HostComputerDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2018/2/1
 * Time: 9:33
 */
@Service("hostComputerService")
public class HostComputerServiceImpl extends ServersManager<HostComputer, HostComputerDao> implements
        HostComputerService {
    @Resource(name = "hostComputerDao")
    @Override
    public void setDao(HostComputerDao hostComputerDao) {
        this.dao = hostComputerDao;
    }
}
