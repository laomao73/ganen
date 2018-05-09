package com.ganen.controller;

import com.alibaba.fastjson.JSONObject;
import com.ganen.util.Tool;
import com.ganen.util.Url;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/restTemplate")
public class RestTemplateController {


    @RequestMapping(path="/getDemoObj.do")
    public JSONObject getDemoObj() {


//        JSONObject httpResponseJson = RestTemplateConfig.getHttpResponseJson(Url.ONE_TEST_SIGN_API, map);
        return null;
    }


}
