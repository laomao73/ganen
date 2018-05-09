package com.ganen.dao;

import com.ganen.entity.CardType;

import java.util.List;

public interface CardTypeDao {
    //查询全部证件类型
    public List<CardType> selectCardTypeAll();

}
