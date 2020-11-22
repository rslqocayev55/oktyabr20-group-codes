package com.yalizada.democrud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yalizada.democrud.model.Book;

public interface BookDAO extends JpaRepository<Book, Integer> {
	
	
public List<Book> findAllByUsername(String username);

@Query(value="select * from book where username=?1 limit ?2,?3",nativeQuery=true)
public List<Book> findAllByUsernamePartial(String username,Integer begin,Integer length);

// findAllByUsernamePartial("admin",100,200);

// select * from book where username='admin' limit 100,200;

 
}
