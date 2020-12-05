package com.yalizada.democrud.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yalizada.democrud.config.MySession;
import com.yalizada.democrud.dao.BookDAO;
import com.yalizada.democrud.file.StorageService;
import com.yalizada.democrud.model.Book;
import com.yalizada.democrud.model.User;

@Controller
@RequestMapping(path="/customer")
public class CustomerController {
	@Autowired
	private StorageService storageService;
	private String username;
	
	
	@Autowired
	private BookDAO bookDAO;
	@Autowired
	private MySession mySession;
	
	@Autowired
	private ServletContext servletContext;

	
	
	@GetMapping
	public String showBooks(Model m ) {
		System.out.println("indexPage"); 
		 int begin=0;
		 int length=100; 
		  
		    int totalBookCount=bookDAO.findAll().size();
		    
		    totalBookCount/=length;
		    m.addAttribute("totalBookCount",totalBookCount);
		    
		    List<Integer> links=new ArrayList<Integer>();
		    for (int i = 1; i <=totalBookCount; i++) {
		    	links.add(i);
			}
		    m.addAttribute("links",links);
		   
		    m.addAttribute("books", addImagePath(bookDAO.findAllPartial(begin,length)));
		
		return "customer";
	}
	
	 
	

	@GetMapping("/pagination/{link}")
	public String showBooksPagination(@PathVariable("link") int link, Model model) {
		System.out.println("showUpdateForm");
		int length=100;
		int begin=(link-1)*length;
		
		
		
		 int totalBookCount=bookDAO.findAll().size();
		    
		    totalBookCount/=length;
		    model.addAttribute("totalBookCount",totalBookCount);
		    List<Integer> links=new ArrayList<Integer>();
		    for (int i = 1; i <=totalBookCount; i++) {
		    	links.add(i);
			}
		    model.addAttribute("links",links);
		    model.addAttribute("books", addImagePath(bookDAO.findAllPartial(begin,length)));
		     
		return "customer";
	}
	
	@RequestMapping(value = "/book-search", method = RequestMethod.POST)
	public String search(  Model model,
			@RequestParam(value = "searchText",required=true) String searchText 
			 ) {
		 
		int length=100;
		int begin=0;
		 
		 int totalBookCount=bookDAO.findAll().size();
		    
		    totalBookCount/=length;
		    model.addAttribute("totalBookCount",totalBookCount);
		    List<Integer> links=new ArrayList<Integer>();
		    for (int i = 1; i <=totalBookCount; i++) {
		    	links.add(i);
			}
		    model.addAttribute("links",links);
		    model.addAttribute("books", addImagePath(bookDAO.findAllPartialSearch(begin,length,searchText)));
		     
		return "customer";
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