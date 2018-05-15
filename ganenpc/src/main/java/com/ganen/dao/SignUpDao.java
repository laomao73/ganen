package com.ganen.dao;

import com.ganen.entity.ServiceOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SignUpDao {

    List<ServiceOrder> getServiceOrder(@Param("companyOrderNumber")String companyOrderNumber);


}
