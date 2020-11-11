package com.yalizada.democrud.controller;

import java.util.List;

import javax.servlet.ServletContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yalizada.democrud.dao.UserDAO;
import com.yalizada.democrud.file.StorageService;
import com.yalizada.democrud.model.User;

@Controller
public class UserController {
	@Autowired
	private StorageService storageService;
	@Autowired
	private UserDAO userDAO;

	@GetMapping("/signup")
	public String showSignUpForm(Model model) {
		System.out.println("showSignUpForm");
		User user = new User();
		// user.setName("Yaqub");
		model.addAttribute("user", user);
		return "add-user";
	}

	@GetMapping("/index")
	public String index(Model m) {
		System.out.println("indexPage");
		m.addAttribute("users", addImagePath(userDAO.findAll()));
		return "index";
	}

	@GetMapping("/")
	public String indexPage(Model m) {
		System.out.println("indexPage");
		m.addAttribute("users", addImagePath(userDAO.findAll()));
		return "index";
	}

	@Autowired
	private ServletContext servletContext;

	@RequestMapping(value = "/adduser", method = RequestMethod.POST)
	public String addUser(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			@RequestParam(value = "image",required=true) MultipartFile image) {
		for (ObjectError error : result.getAllErrors()) {
			System.out.println(error.getDefaultMessage());	
			}
if(result.hasErrors()){
	return "add-user";
	
}


String imageName="fakeimage.png";

System.out.println(image);

if(image==null){
	
}else{
	imageName = storageService.store(image);
}
		
		user.setImagePath(imageName);
		userDAO.save(user);

		model.addAttribute("users", addImagePath(userDAO.findAll()));
		return "index";
	}

	// additional CRUD methods

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		System.out.println("showUpdateForm");
		User user = userDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		model.addAttribute("user", user);
		return "update-user";
	}

	@PostMapping("/update/{id}")
	public String updateUser(@PathVariable("id") int id, User user, BindingResult result, Model model) {
		System.out.println("updateUser");
		if (result.hasErrors()) {
			user.setId(id);
			return "update-user";
		}

		userDAO.save(user);
		model.addAttribute("users", addImagePath(userDAO.findAll()));

		return "index";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model) {
		System.out.println("deleteUser");
		User user = userDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userDAO.delete(user);
		model.addAttribute("users", addImagePath(userDAO.findAll()));
		return "index";
	}

	private List<User> addImagePath(List<User> users) {
		String contextPath = servletContext.getContextPath();
		System.out.println("contextPath : " + contextPath);
		for (User user : users) {
			user.setImagePath(contextPath + "/files/" + user.getImagePath());
		}
		return users;
	}

}