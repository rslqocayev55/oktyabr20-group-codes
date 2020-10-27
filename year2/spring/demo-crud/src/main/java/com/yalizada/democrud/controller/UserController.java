package com.yalizada.democrud.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.yalizada.democrud.dao.UserDAO;
import com.yalizada.democrud.model.User;

@Controller
public class UserController {

	@Autowired
	private UserDAO userDAO;

	@GetMapping("/signup")
	public String showSignUpForm(Model model) {
		System.out.println("showSignUpForm");
		User user=new  User();
		//user.setName("Yaqub");
		model.addAttribute("user", user);
		return "add-user";
	}

	@GetMapping("/index")
	public String index() {
		System.out.println("index");
		return "index";
	}

	@GetMapping("/")
	public String indexPage(Model m) {
		System.out.println("indexPage");
		m.addAttribute("users",userDAO.findAll());
		return "index";
	}
	@PostMapping("/adduser")
	public String addUser(@Valid User user, BindingResult result, Model model) {
		System.out.println("addUser");
		if (result.hasErrors()) {
			return "add-user";
		}

		userDAO.save(user);
		model.addAttribute("users", userDAO.findAll());
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
		model.addAttribute("users", userDAO.findAll());
		return "index";
	}

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model) {
		System.out.println("deleteUser");
		User user = userDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		userDAO.delete(user);
		model.addAttribute("users", userDAO.findAll());
		return "index";
	}
}