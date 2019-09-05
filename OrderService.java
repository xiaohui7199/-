package com.cashsystem.service;

import com.cashsystem.dao.OrderDao;
import com.cashsystem.entity.Order;

import java.util.List;

public class OrderService {
    public OrderDao orderDao;
    public OrderService(){
        this.orderDao = new OrderDao();
    }
    public boolean commitOrder(Order order){
        return this.orderDao.commitOrder(order);
    }

    public List<Order> queryOrderByAcount(Integer accountId){
        return this.orderDao.queryOrderByAcount(accountId);
    }
}
