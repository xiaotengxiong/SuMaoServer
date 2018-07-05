package com.iscas.component.services.hotel_extension;

import com.iscas.component.core.services.Services;
import com.iscas.component.services.hotel_extension.bean.HotelExtension;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:22
 */
public interface HotelExtensionService extends Services<HotelExtension> {

    /**
     * 根据DeviceName 更新表
     *
     * @param hotelExtension
     */
    public int updateByDeviceName(HotelExtension hotelExtension);
}
