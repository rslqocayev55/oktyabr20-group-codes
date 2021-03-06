package com.yalizada.democrud.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yalizada.democrud.model.Book;

// bu interface kitab obyektlerini bazada idare edir
public interface BookDAO extends JpaRepository<Book, Integer> {
	
	
public List<Book> findAllByUsername(String username);

@Query(value="select * from book where username=?1 limit ?2,?3",nativeQuery=true)
public List<Book> findAllByUsernamePartial(String username,Integer begin,Integer length);



@Query(value="select * from book where username=?1 and concat(name,id) like %?4% limit ?2,?3",nativeQuery=true)
public List<Book> findAllByUsernamePartialSearch(String username,Integer begin,Integer length,String search);



@Query(value="select * from book  limit ?1,?2",nativeQuery=true)
public List<Book> findAllPartial(Integer begin,Integer length);


@Query(value="select * from book where concat(name,id) like %?3% limit ?1,?2",nativeQuery=true)
public List<Book> findAllPartialSearch(Integer begin,Integer length,String search);


// findAllByUsernamePartial("admin",100,200);

// select * from book where username='admin' limit 100,200;

 
}
