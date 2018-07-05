package com.iscas.component.services.card_type.dao.impl;

import com.iscas.component.core.PagerModel;
import com.iscas.component.core.dao.BaseDao;
import com.iscas.component.services.card_type.bean.CardType;
import com.iscas.component.services.card_type.dao.CardTypeDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:30
 */
@Repository("cardTypeDao")
public class CardTypeDaoImpl implements CardTypeDao{
    @Resource
    private BaseDao baseDao;

    public void setDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }
    public int insert(CardType cardType) {
        return this.baseDao.insert("card_type.insert",cardType);
    }

    public int delete(CardType cardType) {
        return 0;
    }

    public int update(CardType cardType) {
        return 0;
    }

    public CardType selectOne(CardType cardType) {
        return null;
    }

    public PagerModel selectPageList(CardType cardType) {
        return null;
    }

    public List<CardType> selectList(CardType cardType) {
        return null;
    }

    public int deleteById(int id) {
        return 0;
    }

    public CardType selectById(String id) {
        return null;
    }
}
