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
import com.yalizada.democrud.file.StorageService;
import com.yalizada.democrud.model.Book;
import com.yalizada.democrud.model.SearchModel;
import com.yalizada.democrud.model.User;

@RestController
@RequestMapping(path="/rest-books")
//@CrossOrigin(origins="*")

// rest controller view-la bagli is gormur, bu yalniz gelen http isteklere cavab verir
public class BookRestController {
	@Autowired
	private StorageService storageService;
	private String username;
	
	
	@Autowired
	private BookDAO bookDAO;
	@Autowired
	private MySession mySession;
	
	@Autowired
	private ServletContext servletContext;

	// bu metod kitab obyektlerini xml formatina cevirir ve istek gelen yere qaytarir
	@GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
	public List<Book> findBooks(){
		
		return this.bookDAO.findAllPartial(0, 100);
	}
	 
	@GetMapping(path="/search/{search}",produces = MediaType.APPLICATION_XML_VALUE)
	public List<Book> findBooksSearch(@PathVariable(name="search") String search){
		
		return addImagePath( this.bookDAO.findAllPartialSearch(0, 100, search));
	}
	
	@PostMapping(path="/search-post",produces = MediaType.APPLICATION_XML_VALUE)
	public List<Book> findBooksSearchPost(@RequestBody SearchModel searchModel){
		
		System.out.println(searchModel.getSearch());
		
		return addImagePath( this.bookDAO.findAllPartialSearch(0, 100, searchModel.getSearch()));
	}
	
	
	private List<Book> addImagePath(List<Book> users) {
		String contextPath = servletContext.getContextPath();
		System.out.println("contextPath : " + contextPath);
		for (Book user : users) {
			user.setImagePath(contextPath + "/files/" + user.getImagePath());
		}
		return users;
	}
	
}