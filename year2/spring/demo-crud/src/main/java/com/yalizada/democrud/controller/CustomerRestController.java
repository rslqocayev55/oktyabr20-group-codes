package com.yalizada.democrud.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yalizada.democrud.config.MySession;
import com.yalizada.democrud.dao.BookDAO;
import com.yalizada.democrud.file.StorageService;
import com.yalizada.democrud.model.Book;
import com.yalizada.democrud.model.OrderModel;
@RestController
@RequestMapping(path="/customer-rest")
//@CrossOrigin(origins="*")
public class CustomerRestController {
	
	@Autowired
	private StorageService storageService;
	private String username;
	
	
	@Autowired
	private BookDAO bookDAO;
	@Autowired
	private MySession mySession;
	
	@Autowired
	private ServletContext servletContext;
	
	
	@GetMapping(path="/find-partial",produces = MediaType.APPLICATION_XML_VALUE)
	public List<Book> showBooks( ) {
		 
		 return  addImagePath (bookDAO.findAllPartial(0,100)) ;
		 
	}
	
	private List<Book> addImagePath(List<Book> users) {
		String contextPath = servletContext.getContextPath();
		System.out.println("contextPath : " + contextPath);
		for (Book user : users) {
			user.setImagePath(contextPath + "/files/" + user.getImagePath());
		}
		return users;
	}
	
	@PostMapping
	public void confirm(@RequestBody OrderModel orderModel){
		
	}
}
