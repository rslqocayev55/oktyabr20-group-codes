package com.yalizada.democrud.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.yalizada.democrud.config.MySession;
import com.yalizada.democrud.dao.BookDAO;
import com.yalizada.democrud.dao.OrderDAO;
import com.yalizada.democrud.file.StorageService;
import com.yalizada.democrud.model.Book;
import com.yalizada.democrud.model.OrderModel;
import com.yalizada.democrud.model.SearchModel;
import com.yalizada.democrud.model.User;

@RestController
@RequestMapping(path="/orders-rest")
@CrossOrigin(origins="*")

// rest controller view-la bagli is gormur, bu yalniz gelen http isteklere cavab verir
public class OrderRestController {
	@Autowired
	private StorageService storageService;
	private String username;
	
	
	@Autowired
	private OrderDAO orderDAO;
	@Autowired
	private MySession mySession;
	
	@Autowired
	private ServletContext servletContext;

	@PostMapping (consumes=MediaType.APPLICATION_XML_VALUE,produces=MediaType.APPLICATION_XML_VALUE)
	public OrderModel add(@RequestBody OrderModel orderModel){
		System.out.println(orderModel);
		return this.orderDAO.save(orderModel);
	}
	
	 @GetMapping(path="/{username}")
	public List<OrderModel> findAllByUsername(@PathVariable(name="username") String username){
		return orderDAO.findAllByUsername(username);
	} 
	  
}