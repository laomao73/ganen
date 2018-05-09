package com.ganen.controller;

import com.ganen.entity.CardType;
import com.ganen.service.impl.CardTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/cardType")
public class CardTypeController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private CardTypeService service;


    //证件类型
    @RequestMapping(value = "/cardTypeAll.do",method = RequestMethod.GET)
    @ResponseBody
    public List<CardType> selectCardTypeAll(){
        return service.selectCardTypeAll();
    }

}
