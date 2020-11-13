package com.yalizada.democrud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yalizada.democrud.model.Book;

public interface BookDAO extends JpaRepository<Book, Integer> {
public List<Book> findAllByUsername(String username);
 
}
