package com.ganen.service;

import com.ganen.util.LimitPageList;

public interface ILimitPageService {
    //查询全部订单服务
    public LimitPageList serviceOrderOne(int serviceID, Integer pageNow, String companyOrderNumber, String companyName, String companyOrderState);

    //查询全部企业
    public LimitPageList companyOrderOne(Integer pageNow, String companyOrderNumber, String companyName, String companyOrderState, int companyID);

    LimitPageList ganenOrder(Integer pageNow, String companyOrderNumber, String companyName, String companyOrderState);

    LimitPageList companyAll(Integer pageNow, String companyName);

    LimitPageList serviceAll(Integer pageNow, String serviceName);
}
