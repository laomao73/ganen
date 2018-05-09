package com.ganen.util;

import com.ganen.dao.EmployeeDao;
import com.ganen.dao.ServiceDao;
import com.ganen.entity.CompanyOrder;
import com.ganen.entity.EmployeeOrder;
import com.ganen.entity.Service;
import com.ganen.entity.ServiceOrder;
import com.ganen.service.IMatchService;
import com.ganen.service.IServerService;
import com.ganen.service.impl.CardTypeService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 匹配服务
 */
@org.springframework.stereotype.Service("IMatchService")
public class MatchService implements IMatchService {

    @Autowired
    private CardTypeService cardTypeService;
    @Autowired
    private IServerService serverService;
    @Resource
    public EmployeeDao employeeDao;

    @Resource
    public ServiceDao serviceDao;

    //灵活用工
    public Map<String, Object> matchServiceType1(CompanyOrder companyOrder, List<EmployeeOrder> employeeList) {
        Service service2 = null;
        List<Integer> id=new ArrayList<Integer>();
        Map<String, Object> list = new HashMap<String, Object>();
        List<EmployeeOrder> elist = new ArrayList<EmployeeOrder>();
        //根据员工类型匹配服务公司
        Service service = serviceDao.serviceByPeople(companyOrder.getCompanyOrderType());
        //每个季度不超过100万
        BigDecimal bigDecimal = new BigDecimal(1000000.0);
        //税
        BigDecimal number = BigDecimal.ZERO;
        //企业订单实发金额
        BigDecimal companyPrice = BigDecimal.ZERO;
        //企业订单个税金额
        BigDecimal companyTax = BigDecimal.ZERO;

        //服务1订单实发金额
        BigDecimal servicePrice = BigDecimal.ZERO;
        //服务2订单实发金额
        BigDecimal servicePrice2 = BigDecimal.ZERO;

        //1计算订单总金额
        //员工订单
        for (int i = 0; i < employeeList.size(); i++) {

            int j = i;
            EmployeeOrder employeeOrder = employeeList.get(i);
            //第一个季度
            BigDecimal spring = employeeDao.employeeSpring(employeeOrder.getEmployee().getEmployeeCard());
            //第二个季度
            BigDecimal summer = employeeDao.employeeSummer(employeeOrder.getEmployee().getEmployeeCard());
            //第三个季度
            BigDecimal fall = employeeDao.employeeFall(employeeOrder.getEmployee().getEmployeeCard());
            //第四个季度
            BigDecimal winter = employeeDao.employeeWinter(employeeOrder.getEmployee().getEmployeeCard());
            if (spring == null) spring = BigDecimal.ZERO;
            if (summer == null) summer = BigDecimal.ZERO;
            if (fall == null) fall = BigDecimal.ZERO;
            if (winter == null) winter = BigDecimal.ZERO;
            //个税承担
            if (companyOrder.getCompanyOrderTax() == 1) {
                number = new BigDecimal(0.98);
                //企业承担
                //实发工资=应发工资
                employeeOrder.setEmployeePrice(employeeOrder.getEmployeeSalary());
                //应发工资=实际工资%0.98
                employeeOrder.setEmployeeSalary(employeeOrder.getEmployeeSalary().divide(number, 2, BigDecimal.ROUND_HALF_UP));
            } else {
                number = new BigDecimal(0.98);
                //个人承担
                //实发工资=应发工资*0.98
                employeeOrder.setEmployeePrice(employeeOrder.getEmployeeSalary().multiply(number).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            employeeOrder.setEmployeeOrderState("未发放");
            number = new BigDecimal("0.02");
            //个税=应发工资*0.02
            employeeOrder.setEmployeeTax(employeeOrder.getEmployeeSalary().multiply(number).setScale(2, BigDecimal.ROUND_HALF_UP));
            //应发总额
            companyPrice = companyPrice.add(employeeOrder.getEmployeePrice());
            //个税总额
            companyTax = companyTax.add(employeeOrder.getEmployeeTax());
            //是否员工超过100万
            if (spring.compareTo(bigDecimal) != 1 && summer.compareTo(bigDecimal) != 1 && fall.compareTo(bigDecimal) != 1 && winter.compareTo(bigDecimal) != 1) {
                servicePrice = servicePrice.add(employeeOrder.getEmployeePrice());
                //没超过
            } else {
                //超过
                //使用宁波公司
                servicePrice2 = servicePrice.add(employeeOrder.getEmployeePrice());
                service2 = serverService.serviceByPeople(3);
                elist.add(employeeOrder);
                id.add(i);
            }
        }
        number = new BigDecimal(0.92);
        //企业订单状态
        companyOrder.setCompanyOrderState("执行中");
        //企业税总额
        companyOrder.setCompanyOrderTaxCount(companyTax);
        //企业实发总额
        companyOrder.setCompanyOrderPrice(companyPrice);
        //企业付款总额
        companyOrder.setCompanyOrderPriceCount(companyPrice.divide(number, 2, BigDecimal.ROUND_HALF_UP));

        List<ServiceOrder> slist = new ArrayList<ServiceOrder>();
        ServiceOrder serviceOrder = new ServiceOrder();
        //服务B
        serviceOrder.setService(service);
        //总人数
        serviceOrder.setServiceOrderCount(employeeList.size());
        //总金额
        serviceOrder.setServiceOrderPrice(servicePrice.divide(number, 2, BigDecimal.ROUND_HALF_UP));
        //服务费
        serviceOrder.setServiceOrderServicePrice(serviceOrder.getServiceOrderPrice().subtract(servicePrice));
        //服务订单状态
        serviceOrder.setServiceOrderState("执行中");

        if (id.size() > 0) {
            for (int i = 0; i < id.size(); i++) {
                employeeList.remove(id.get(i));
            }
        }

        //员工订单
        serviceOrder.setEmployeeOrderList(employeeList);
        slist.add(serviceOrder);

        if (service2 != null) {
            serviceOrder = new ServiceOrder();
            //服务B
            serviceOrder.setService(service2);
            //总人数
            serviceOrder.setServiceOrderCount(elist.size());
            //总金额
            serviceOrder.setServiceOrderPrice(servicePrice2.divide(number, 2, BigDecimal.ROUND_HALF_UP));
            //服务费
            serviceOrder.setServiceOrderServicePrice(serviceOrder.getServiceOrderPrice().subtract(servicePrice2));
            //服务订单状态
            serviceOrder.setServiceOrderState("执行中");
            //员工订单
            serviceOrder.setEmployeeOrderList(elist);
            slist.add(serviceOrder);
        }
        list.put("companyOrder", companyOrder);
        list.put("serviceOrder", slist);
        return list;
    }

    //企业用工
    @Override
    public Map<String, Object> matchServiceType2(CompanyOrder companyOrder, List<EmployeeOrder> employeeList) {
        Map<String, Object> list = new HashMap<String, Object>();
        //税
        BigDecimal number = BigDecimal.ZERO;
        //企业订单实发金额
        BigDecimal companyPrice = BigDecimal.ZERO;
        //企业订单个税金额
        BigDecimal companyTax = BigDecimal.ZERO;

        //根据服务B服务员工类型 匹配服务公司
        Service service = serverService.serviceByPeople(companyOrder.getCompanyOrderType());
        //是否服务B已经超过1000万
        BigDecimal bigDecimal = new BigDecimal("10000000.00");
        BigDecimal servicePriceCount = serviceDao.servicePriceCount(service.getServiceID());

        if (servicePriceCount == null) {
            servicePriceCount = BigDecimal.ZERO;
        }
        if (servicePriceCount.compareTo(bigDecimal) == 1) {
            list.put("result", 0);
            list.put("content", "订单金额过大，请联系销售人员沟通");
            return list;
        }
        //1计算订单总金额
        //员工订单
        for (int i = 0; i < employeeList.size(); i++) {
            EmployeeOrder employeeOrder = employeeList.get(i);
            //个税承担
            if (companyOrder.getCompanyOrderTax() == 1) {
                number = new BigDecimal(0.98);
                //企业承担
                //实发工资=应发工资
                employeeOrder.setEmployeePrice(employeeOrder.getEmployeeSalary());
                //应发工资=实际工资%0.98
                employeeOrder.setEmployeeSalary(employeeOrder.getEmployeeSalary().divide(number, 2, BigDecimal.ROUND_HALF_UP));
            } else {
                number = new BigDecimal(0.98);
                //个人承担
                //实发工资=应发工资*0.98
                employeeOrder.setEmployeePrice(employeeOrder.getEmployeeSalary().multiply(number).setScale(2, BigDecimal.ROUND_HALF_UP));
            }
            number = new BigDecimal("0.02");
            //个税=应发工资*0.02
            employeeOrder.setEmployeeTax(employeeOrder.getEmployeeSalary().multiply(number).setScale(2, BigDecimal.ROUND_HALF_UP));
            //应发总额
            companyPrice = companyPrice.add(employeeOrder.getEmployeePrice());
            //个税总额
            companyTax = companyTax.add(employeeOrder.getEmployeeTax());
        }

        if (companyPrice.add(servicePriceCount).compareTo(bigDecimal) == 1) {
            list.put("result", 0);
            list.put("content", "订单金额过大，请联系销售人员沟通");
            return list;
        }


        number = new BigDecimal(0.92);
        //企业订单状态
        companyOrder.setCompanyOrderState("执行中");
        //企业税总额
        companyOrder.setCompanyOrderTaxCount(companyTax);
        //企业实发总额
        companyOrder.setCompanyOrderPrice(companyPrice);
        //企业付款总额
        companyOrder.setCompanyOrderPriceCount(companyPrice.divide(number, 2, BigDecimal.ROUND_HALF_UP));

        //服务订单
        ServiceOrder serviceOrder = new ServiceOrder();
        //服务公司
        serviceOrder.setService(service);
        //总人数
        serviceOrder.setServiceOrderCount(employeeList.size());
        //总金额
        serviceOrder.setServiceOrderPrice(companyOrder.getCompanyOrderPriceCount());
        //服务费
        serviceOrder.setServiceOrderServicePrice(companyOrder.getCompanyOrderPriceCount().subtract(companyOrder.getCompanyOrderPrice()));
        //服务订单状态
        serviceOrder.setServiceOrderState("执行中");
        list.put("result", 1);
        list.put("serviceOrder", serviceOrder);
        list.put("employeeOrder", employeeList);
        list.put("companyOrder", companyOrder);
        return list;
    }


}
