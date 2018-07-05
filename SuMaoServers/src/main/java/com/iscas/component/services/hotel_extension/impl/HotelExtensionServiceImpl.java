package com.iscas.component.services.hotel_extension.impl;


import com.iscas.component.core.services.ServersManager;
import com.iscas.component.services.hotel_extension.HotelExtensionService;
import com.iscas.component.services.hotel_extension.bean.HotelExtension;
import com.iscas.component.services.hotel_extension.dao.HotelExtensionDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:38
 */
@Service("hotelExtensionService")
public class HotelExtensionServiceImpl extends ServersManager<HotelExtension, HotelExtensionDao> implements
        HotelExtensionService {
    @Resource(name = "hotelExtensionDao")
    public void setDao(HotelExtensionDao hotelExtensionDao) {
        this.dao = hotelExtensionDao;
    }

    @Override
    public int updateByDeviceName(HotelExtension hotelExtension) {
        return this.dao.updateByDeviceName(hotelExtension);
    }
}
