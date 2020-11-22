package com.yalizada.democrud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yalizada.democrud.dao.UserDAO;
import com.yalizada.democrud.file.StorageService;
import com.yalizada.democrud.model.User;

@Controller
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	
	
	@RequestMapping(value = "/createUserProcess", method = RequestMethod.POST)
	public String createUserProcess(  @ModelAttribute("user") User user,   Model model 
			 ) {
	  System.out.println(user);
	  String viewName="";
	  
	  boolean result=  userDAO.createUser(user);
	if(result){
		model.addAttribute("userCreatedSuccessfully", "");
		viewName="my-login";
	}else{
		model.addAttribute("userAlreadyExists", "");
		viewName="create-user-form";
	}
		 
		return viewName;
	}
}
