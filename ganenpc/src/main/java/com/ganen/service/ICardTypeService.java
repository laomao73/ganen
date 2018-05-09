package com.ganen.service;

import com.ganen.entity.CardType;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ICardTypeService {

    public List<CardType> selectCardTypeAll();

}
