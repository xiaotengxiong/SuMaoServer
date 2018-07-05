package com.iscas.component.services.hotel_extension.dao.impl;

import com.iscas.component.core.PagerModel;
import com.iscas.component.core.dao.BaseDao;
import com.iscas.component.services.hotel_extension.bean.HotelExtension;
import com.iscas.component.services.hotel_extension.dao.HotelExtensionDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:32
 */
@Repository("hotelExtensionDao")
public class HotelExtensionDaoImpl implements HotelExtensionDao {
    @Resource
    private BaseDao baseDao;

    public void setDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    public int insert(HotelExtension hotelExtension) {
        return this.baseDao.insert("hotel_extension.insert",hotelExtension);
    }

    public int delete(HotelExtension hotelExtension) {
        return 0;
    }

    public int update(HotelExtension hotelExtension) {
        return 0;
    }

    public HotelExtension selectOne(HotelExtension hotelExtension) {
        return (HotelExtension) baseDao.selectOne("hotel_extension.select.one", hotelExtension);
    }

    public PagerModel selectPageList(HotelExtension hotelExtension) {
        return null;
    }

    public List<HotelExtension> selectList(HotelExtension hotelExtension) {
        return null;
    }

    public int deleteById(int id) {
        return 0;
    }

    public HotelExtension selectById(String id) {
        return null;
    }

    @Override
    public int updateByDeviceName(HotelExtension hotelExtension) {
        return baseDao.update("hotel_extension.updateByDeviceName", hotelExtension);
    }
}
