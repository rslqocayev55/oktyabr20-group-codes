package com.yalizada.democrud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yalizada.democrud.model.OrderModel;

public interface OrderDAO extends JpaRepository<OrderModel, Integer>{
public List<OrderModel> findAllByUsername(String username);

}
