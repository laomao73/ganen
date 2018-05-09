package com.ganen.service.impl;

import com.ganen.dao.CardTypeDao;
import com.ganen.entity.CardType;
import com.ganen.service.ICardTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("CardTypeService")
public class CardTypeService implements ICardTypeService {

    @Resource
    private CardTypeDao cardTypeDao;

    //查询全部证件类型
    public List<CardType> selectCardTypeAll() {
        List<CardType> cardTypes = cardTypeDao.selectCardTypeAll();
        return cardTypes;
    }
}
