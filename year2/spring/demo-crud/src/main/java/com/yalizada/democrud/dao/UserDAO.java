package com.yalizada.democrud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yalizada.democrud.model.User;

public interface UserDAO extends JpaRepository<User, Integer> {
public List<User> findAllByUsername(String username);
// select * user where username=username
// query method
// native query
}
