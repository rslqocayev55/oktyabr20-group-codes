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
public class BookController {
	@Autowired
	private StorageService storageService;
	private String username;
	
	
	@Autowired
	private BookDAO bookDAO;
	@Autowired
	private MySession mySession;
	
	@Autowired
	private ServletContext servletContext;

	
	
	@GetMapping("/showMyLoginPage")
	public String showMyLoginPage( ) {
	 
		return "my-login";
	}
	
	
	// Model atributu controller terefinden yaddasa hansisa obyekti qoymaq ve bunu view terefde istifade etmek ucundur
	@GetMapping("/createUserShowForm")
	public String createUserShowForm( Model model) {
		User user=new User();
		
	 model.addAttribute("user", user);
		return "create-user-form";
	}
	
	@GetMapping("/signup")
	public String showSignUpForm(Model model) {
		System.out.println("showSignUpForm");
		Book user = new Book();
		user.setId(0);
		// user.setName("Yaqub");
		model.addAttribute("user", user);
		return "add-user";
	}

	@GetMapping(path={"/index","/"})
	public String index(Model m ) {
		System.out.println("indexPage"); 
		 int begin=0;
		 int length=100; 
		 Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		    String username = loggedInUser.getName();
		    mySession.setMessage("Hello session");
		    mySession.setUsername(username);
		    this.username=username;
		    int totalBookCount=bookDAO.findAllByUsername(username).size();
		    
		    totalBookCount/=length;
		    m.addAttribute("totalBookCount",totalBookCount);
		    List<Integer> links=new ArrayList<Integer>();
		    for (int i = 1; i <=totalBookCount; i++) {
		    	links.add(i);
			}
		    m.addAttribute("links",links);
		    m.addAttribute("users", addImagePath(bookDAO.findAllByUsernamePartial(this.username,begin,length)));
		return "index";
	} 
	@GetMapping(path={"/rest-books-list"})
	public String indexRestBooks() {
		 return "book-list-rest";
	} 
	
	
	
	
	
	
	
	
	@RequestMapping(value = "/adduser/{id}", method = RequestMethod.POST)
	public String addUser(@Valid @ModelAttribute("user") Book user, BindingResult result, Model model,
			@RequestParam(value = "image",required=true) MultipartFile image,@PathVariable("id") Integer id) {
		user.setId(id); //15
		if(id>0){
				user.setImagePath(bookDAO.findById(id).get().getImagePath());
		}
	
		
		
		System.out.println("user - "+user);
		 
if(result.hasErrors()){
	return "add-user";
	
}


String imageName="fakeimage.png";

System.out.println(image.getSize());

if(image==null || image.getSize()==0L){
	
}else{ 
	imageName = storageService.store(image);
}
		if(id==0){
			user.setImagePath(imageName);
		}else if(!imageName.equals("fakeimage.png")){
			user.setImagePath(imageName);
		} 
		user.setUsername(username);
		bookDAO.save(user);

		model.addAttribute("users", addImagePath(bookDAO.findAllByUsername(this.username)));
		return "index";
	}

	// additional CRUD methods

	@GetMapping("/edit/{id}")
	public String showUpdateForm(@PathVariable("id") int id, Model model) {
		System.out.println("showUpdateForm");
		Book user = bookDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

		model.addAttribute("user", user);
		return "add-user";
	}
 
	
	
	
	@GetMapping("/book-pagination/{link}")
	public String bookPagination(@PathVariable("link") int link, Model model) {
		System.out.println("showUpdateForm");
		int length=100;
		int begin=(link-1)*length;
		
		
		
		 int totalBookCount=bookDAO.findAllByUsername(username).size();
		    
		    totalBookCount/=length;
		    model.addAttribute("totalBookCount",totalBookCount);
		    List<Integer> links=new ArrayList<Integer>();
		    for (int i = 1; i <=totalBookCount; i++) {
		    	links.add(i);
			}
		    model.addAttribute("links",links);
		    model.addAttribute("users", addImagePath(bookDAO.findAllByUsernamePartial(this.username,begin,length)));
		     
		return "index";
	}
 
	
	

	@GetMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") int id, Model model) {
		System.out.println("deleteUser");
		Book user = bookDAO.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
		bookDAO.delete(user);
		model.addAttribute("users", addImagePath(bookDAO.findAllByUsername(this.username)));
		return "index";
	}

	private List<Book> addImagePath(List<Book> users) {
		String contextPath = servletContext.getContextPath();
		System.out.println("contextPath : " + contextPath);
		for (Book user : users) {
			user.setImagePath(contextPath + "/files/" + user.getImagePath());
		}
		return users;
	}

	
	
	
	
	

	
	@RequestMapping(value = "/book-search", method = RequestMethod.POST)
	public String search(  Model model,
			@RequestParam(value = "searchText",required=true) String searchText 
			 ) {
		 
System.out.println(searchText);
		model.addAttribute("users", addImagePath(bookDAO.findAllByUsernamePartialSearch(username, 0, 10, searchText)));
		return "index";
	}
	
	
}