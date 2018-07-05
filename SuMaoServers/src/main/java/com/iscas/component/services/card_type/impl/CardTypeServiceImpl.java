package com.iscas.component.services.card_type.impl;

import com.iscas.component.core.services.ServersManager;
import com.iscas.component.services.card_type.CardTypeService;
import com.iscas.component.services.card_type.bean.CardType;
import com.iscas.component.services.card_type.dao.CardTypeDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/12/5
 * Time: 11:36
 */
@Service("cardTypeService")
public class CardTypeServiceImpl extends ServersManager<CardType, CardTypeDao> implements
        CardTypeService {
    @Resource(name = "cardTypeDao")
    @Override
    public void setDao(CardTypeDao cardTypeDao) {
        this.dao = cardTypeDao;
    }
}
