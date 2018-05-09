package com.ganen.service.impl;

import com.ganen.dao.LimitDao;
import com.ganen.entity.Company;
import com.ganen.entity.CompanyOrder;
import com.ganen.service.ILimitPageService;
import com.ganen.util.LimitPageList;
import com.ganen.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("LimitPageService")
public class LimitPageService implements ILimitPageService {

    @Resource
    private LimitDao limitDao;

    //查询服务公司全部订单1
    @Override
    public LimitPageList serviceOrderOne(int serviceID, Integer pageNow, String companyOrderNumber, String companyName, String companyOrderState) {
        LimitPageList pageList=new LimitPageList();
        Integer totalCount = limitDao.serviceOrderOneCount(serviceID,companyOrderNumber,companyName,companyOrderState);
        List<CompanyOrder> companyOrders=new ArrayList<CompanyOrder>();
        Page page=null;
        page=new Page(2,totalCount);
        page.getTotalPageCount();
        if(pageNow!=null&&pageNow!=0){
            page.setPageNow(pageNow);
        }else{
            page.setPageNow(1);
        }
        companyOrders=limitDao.serviceOrderOneAll(serviceID,page.getStartPos(),page.getPageSize(),companyOrderNumber,companyName,companyOrderState);
        pageList.setPage(page);
        pageList.setList(companyOrders);
        return pageList;
    }

    public LimitPageList companyOrderOne(Integer pageNow, String companyOrderNumber, String companyName, String companyOrderState, int companyID) {
        LimitPageList pageList=new LimitPageList();
        Integer totalCount = limitDao.companyOrderOneCount(companyID,companyOrderNumber,companyName,companyOrderState);
        List<CompanyOrder> companyOrders=new ArrayList<CompanyOrder>();
        Page page=new Page(2,totalCount);
        page.getTotalPageCount();
        System.out.println("s"+pageNow);
        if(pageNow!=null){
            page.setPageNow(pageNow);
            System.out.println("s1"+page.getPageNow());
        }else{
            page.setPageNow(1);
            System.out.println("s2"+page.getPageNow());
        }
        System.out.println(page);
        companyOrders=limitDao.companyOrderOneAll(companyID,page.getStartPos(),page.getPageSize(),companyOrderNumber,companyName,companyOrderState);

        pageList.setPage(page);
        pageList.setList(companyOrders);
        return pageList;
    }

    //获取全部企业订单
    @Override
    public LimitPageList ganenOrder(Integer pageNow, String companyOrderNumber, String companyName, String companyOrderState) {
        LimitPageList pageList=new LimitPageList();
        Integer totalCount = limitDao.ganenOrderCount(companyOrderNumber,companyName,companyOrderState);
        List<CompanyOrder> companyOrders=new ArrayList<CompanyOrder>();
        Page page=new Page(2,totalCount);
        page.getTotalPageCount();
        if(pageNow!=null){
            page.setPageNow(pageNow);
        }else{
            page.setPageNow(1);
        }
        companyOrders=limitDao.ganenOrderAll(page.getStartPos(),page.getPageSize(),companyOrderNumber,companyName,companyOrderState);

        pageList.setPage(page);
        pageList.setList(companyOrders);

        return pageList;
    }

    //获取全部公司
    @Override
    public LimitPageList companyAll(Integer pageNow, String companyName) {
        LimitPageList pageList=new LimitPageList();
        Integer totalCount = limitDao.companyAllCount(companyName);
        List<Company> companyOrders=new ArrayList<Company>();
        Page page=new Page(2,totalCount);
        page.getTotalPageCount();
        if(pageNow!=null){
            page.setPageNow(pageNow);
        }else{
            page.setPageNow(1);
        }
        companyOrders=limitDao.companyAll(page.getStartPos(),page.getPageSize(),companyName);

        pageList.setPage(page);
        pageList.setList(companyOrders);

        return pageList;
    }

    //获取全部服务公司
    @Override
    public LimitPageList serviceAll(Integer pageNow, String serviceName) {
        LimitPageList pageList=new LimitPageList();
        Integer totalCount = limitDao.serviceAllCount(serviceName);
        List<Service> companyOrders=new ArrayList<Service>();
        Page page=new Page(2,totalCount);
        page.getTotalPageCount();
        if(pageNow!=null){
            page.setPageNow(pageNow);
        }else{
            page.setPageNow(1);
        }
        companyOrders=limitDao.serviceAll(page.getStartPos(),page.getPageSize(),serviceName);
        pageList.setPage(page);
        pageList.setList(companyOrders);

        return pageList;
    }
}
