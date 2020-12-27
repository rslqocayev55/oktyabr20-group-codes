package com.yalizada.democrud.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yalizada.democrud.model.OrderModel;

public interface OrderDAO extends JpaRepository<OrderModel, Integer>{

}
