package com.yalizada.democrud.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yalizada.democrud.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {

}
