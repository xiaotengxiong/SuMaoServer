package com.iscas.component.services.hotel_extension.dao;

import com.iscas.component.core.dao.DaoManager;
import com.iscas.component.services.hotel_extension.bean.HotelExtension;

public interface HotelExtensionDao extends DaoManager<HotelExtension> {
    /**
     * 根据DeviceName 更新表
     *
     * @param hotelExtension
     */
    public int updateByDeviceName(HotelExtension hotelExtension);
}