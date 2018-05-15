package com.ganen.service.impl;

import com.ganen.dao.EmployeeDao;
import com.ganen.dao.ServiceDao;
import com.ganen.entity.Employee;
import com.ganen.entity.EmployeeOrder;
import com.ganen.entity.ServiceOrder;
import com.ganen.service.IEmployeeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.List;

@Service("EmployeeService")
public class EmployeeService implements IEmployeeService {

    @Resource
    private EmployeeDao employeeDao;
    @Resource
    private ServiceDao serviceDao;

    //新建员工订单
    public int newEmployeeService(ServiceOrder serviceOrder, Timestamp timestamp, String companyOrderNumber) {
        Integer integer = serviceDao.servieceID(serviceOrder.getCompanyOrder().getCompanyOrderNumber(),serviceOrder.getService().getServiceID());
        //获取全部员工订单
        List<EmployeeOrder> employeeOrderList = serviceOrder.getEmployeeOrderList();
        serviceOrder.setServiceOrderID(integer);
        for (int i = 0; i < employeeOrderList.size(); i++) {
            EmployeeOrder order = employeeOrderList.get(i);

            order.setServiceOrder(serviceOrder);
            order.setEmployeeOrderState("未发");
            order.setEmployeeOrderTime(timestamp);
            order.getEmployee().setEmployeeContractState(0);
            //创建员工
            //员工是否存在
            Integer employeeID = employeeDao.getEmployeeID(order.getEmployee().getEmployeeCard());
            if (employeeID == null) {
                //创建员工
                int result = employeeDao.newEmployee(order.getEmployee());
                employeeID = employeeDao.getEmployeeID(order.getEmployee().getEmployeeCard());
                order.getEmployee().setEmployeeID(employeeID);
            } else {
                //使用已创建的员工 创建订单
                order.getEmployee().setEmployeeID(employeeID);
            }
            employeeDao.newEmployeeOrder(order);
        }
        return 1;
    }

    @Override
    public List<EmployeeOrder> employeeOrderByID(int serviceOrderID) {
        return employeeDao.employeeOrderByServiceOrderID(serviceOrderID);
    }
}
