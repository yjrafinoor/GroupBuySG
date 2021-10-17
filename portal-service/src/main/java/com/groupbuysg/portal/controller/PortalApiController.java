package com.groupbuysg.portal.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.groupbuysg.portal.service.PortalApiService;
import com.groupbuysg.portal.valueobject.Comment;
import com.groupbuysg.portal.valueobject.Listing;
import com.groupbuysg.portal.valueobject.Product;
import com.groupbuysg.portal.valueobject.User;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class PortalApiController {
	
	@Autowired
	PortalApiService portalApiService;
	
	@RequestMapping(value = "/logInViaGoogle", method = RequestMethod.POST)
	public ModelAndView logInViaGoogle (@ModelAttribute User user, Model model){
		log.info("Inside logInViaGoogle method of PortalApiController");
log.info("Hee logInViaGoogle1: "+user+";"+model);		
		User userResponse =  portalApiService.logInViaGoogle(user);
log.info("Hee logInViaGoogle2: "+userResponse);		

		List<Product> listProducts =portalApiService.getProductList();
		model.addAttribute("listProducts",listProducts);
		
		model.addAttribute("successMessage", "Login Successful");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		
		return modelAndView;
	}
	
	@RequestMapping("/")	
	public String indexpage (Model model){
		log.info("Inside indexpage method of PortalApiController");
		//List<User> userList =portalApiService.getUserList();

		return "index";
	}
	
	@RequestMapping("/home")	
	public String homepage (Model model){
		log.info("Inside homepage method of PortalApiController");
		//List<User> userList =portalApiService.getUserList();

		List<Product> listProducts =portalApiService.getProductList();
		model.addAttribute("listProducts",listProducts);
		
		return "home";
	}
	
	@RequestMapping("/user_list")
	public String userpage (Model model){
		log.info("Inside userpage method of PortalApiController");
		List<User> listUsers =portalApiService.getUserList();
		model.addAttribute("listUsers",listUsers);
		return "user_list";
	}
	
	@RequestMapping(value = "/saveUser", method = RequestMethod.POST)
	public ModelAndView save(@ModelAttribute User user, Model model)
	{
		
		portalApiService.saveUser(user);
		/*
		 * Here you can write the code to save the user information in database
		 */
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		List<User> listUsers =portalApiService.getUserList();
		modelAndView.addObject("listUsers",listUsers);
		
		model.addAttribute("successMessage", "Sigup Successful");
		

		return modelAndView;
	}
	
	@RequestMapping(path = {"/editUser/{id}"})
    public String editUserPage(Model model, @PathVariable("id") long userId) {
		log.info("Inside editUserPage method of PortalApiController");

		try {
	        User user = portalApiService.getUserById(userId);
	        model.addAttribute("user", user);
log.info("Hee editUserPage: "+user);	

			return "profile_update";
			
		} catch (Exception ex) {
	        // log exception first, 
	        // then show error
	        String errorMessage = ex.getMessage();
	        log.error(errorMessage);
	        model.addAttribute("errorMessage", errorMessage);
	        
	        return "";
	    }

    }
	
	@RequestMapping(value = "/updateUser/{uid}", method = RequestMethod.POST)
	public ModelAndView updateUser(@ModelAttribute User userDetails, @PathVariable("uid") long userId, Model model){
		log.info("Inside updateUser method of PortalApiController");
		
log.info("HEE updateUser: "+userDetails +" : "+userId);		
		
		portalApiService.updateUser(userDetails, userId);
		
		User user =portalApiService.getUserById(userId);
		model.addAttribute("user",user);
		
		model.addAttribute("successMessage", "Update Successful");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("profile_update");

		return modelAndView;
	}
	
	@RequestMapping(path = "/deleteUser/{uid}")
    public ModelAndView deleteUserById(Model model, @PathVariable("uid") long productId) {
		log.info("Inside deleteUserById method of PortalApiController");
		portalApiService.deleteUserById(productId);
		
		List<User> listUsers =portalApiService.getUserList();
		model.addAttribute("listUsers",listUsers);
		
		model.addAttribute("successMessage", "Delete Successful");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user_list");
				
		return modelAndView;
    }
	
	@RequestMapping(value = "/requestLeader/{id}", method = RequestMethod.POST)
	public ModelAndView requestLeader(@PathVariable("id") long userId, Model model){
		log.info("Inside requestLeader method of PortalApiController");
		
log.info("HEE requestLeader: "+userId);				
		portalApiService.requestLeader(userId);
		
		User user = portalApiService.getUserDetails(userId);
		model.addAttribute("user",user);
		
		model.addAttribute("successMessage", "Request Leader Successful");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("profile_update");

		return modelAndView;
	}
	
	@RequestMapping(value = "/approveLeader/{id}", method = RequestMethod.POST)
	public ModelAndView approveLeader(@PathVariable("id") long userId, Model model){
		log.info("Inside approveLeader method of PortalApiController");
		
log.info("HEE approveLeader: "+userId);				
		portalApiService.adminReviewLeaderRequest(userId, 1);
		
		List<User> listUsers =portalApiService.getUserList();
		model.addAttribute("listUsers",listUsers);
		
		model.addAttribute("successMessage", "Approved Leader Successful");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user_list");

		return modelAndView;
	}
	
	@RequestMapping(value = "/rejectLeader/{id}", method = RequestMethod.POST)
	public ModelAndView rejectLeader(@PathVariable("id") long userId, Model model){
		log.info("Inside rejectLeader method of PortalApiController");
		
log.info("HEE rejectLeader: "+userId);				
		portalApiService.adminReviewLeaderRequest(userId, 2);
		
		List<User> listUsers =portalApiService.getUserList();
		model.addAttribute("listUsers",listUsers);
		
		model.addAttribute("successMessage", "Reject Leader Successful");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user_list");

		return modelAndView;
	}
	
	@RequestMapping("/product_create")	
	public String productCreatePate (Model model){
		log.info("Inside productCreatePate method of PortalApiController");
		//List<User> userList =portalApiService.getUserList();

		return "product_create";
	}
	
	@RequestMapping(value = "/saveProduct", method = RequestMethod.POST)
	public ModelAndView saveProduct(@ModelAttribute Product product, Model model){
		log.info("Inside saveProduct method of PortalApiController");
		long userId=(long) 2;
log.info("HEE product: "+product);		
log.info("HEE userId: "+userId);		
		
		Product newProduct = new Product();
		newProduct = portalApiService.saveProduct(product, userId);
		
		List<Product> listProducts =portalApiService.getProductList();
		model.addAttribute("listProducts",listProducts);
		
		model.addAttribute("successMessage", "Create Successful");

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/editProduct/"+newProduct.getProductId());

		return modelAndView;
	}
	
	@RequestMapping("/product_list")
	public String productpage (Model model){
		log.info("Inside productpage method of PortalApiController");
		List<Product> listProducts =portalApiService.getProductList();
		model.addAttribute("listProducts",listProducts);
		return "product_list";
	}
	
	@RequestMapping(path = {"/editProduct/{id}"})
    public String editProductPage(Model model, @PathVariable("id") long productId) {
		log.info("Inside editProductPage method of PortalApiController");

		try {
	        Product product = portalApiService.getProductById(productId);
	        model.addAttribute("product", product);
	        
	        User user = portalApiService.getUserDetails(product.getUserId());
			model.addAttribute("user",user);
			
			return "product_update";
			
		} catch (Exception ex) {
	        // log exception first, 
	        // then show error
	        String errorMessage = ex.getMessage();
	        log.error(errorMessage);
	        model.addAttribute("errorMessage", errorMessage);
	        
	        return "";
	    }

    }
	
	@RequestMapping(value = "/updateProduct/{pid}", method = RequestMethod.POST)
	public ModelAndView updateProduct(@ModelAttribute Product productDetails, @PathVariable("pid") long productId, Model model){
		log.info("Inside updateProduct method of PortalApiController");
		
log.info("HEE product: "+productDetails +" : "+productId);		
		
		portalApiService.updateProduct(productDetails, productId);
		
		Product product =portalApiService.getProductById(productId);
		model.addAttribute("product",product);
		
		User user = portalApiService.getUserDetails(product.getUserId());
		model.addAttribute("user",user);
		
		model.addAttribute("successMessage", "Update Successful");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("product_update");

		return modelAndView;
	}
	
	@RequestMapping(path = "/deleteProduct/{pid}")
    public ModelAndView deleteProductById(Model model, @PathVariable("pid") long productId) {
		log.info("Inside deleteProductById method of PortalApiController");
		portalApiService.deleteProductById(productId);
		
		model.addAttribute("successMessage", "Delete Successful");
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("product_list");
		
        return modelAndView;
    }

	@RequestMapping("/join/{pid}")	
	public ModelAndView joinPage (@PathVariable("pid") long productId, Model model){
		log.info("Inside joinPage method of PortalApiController");
	
		Product product = portalApiService.getProductById(productId);
		model.addAttribute("product",product);
log.info("HEE product: "+product);		
		Listing leader = portalApiService.getLeader(productId);
		model.addAttribute("leader",leader);
	
		User leaderDetails = portalApiService.getUserDetails(leader.getUserId());
		model.addAttribute("leaderDetails",leaderDetails);
		
		Listing admin = portalApiService.getAdmin(productId);
		model.addAttribute("admin",admin);

		User adminDetails = portalApiService.getUserDetails(admin.getUserId());
		model.addAttribute("adminDetails",adminDetails);
		
		List<Listing> allJoiners = portalApiService.getJoiners(productId);
		model.addAttribute("allJoiners",allJoiners);
		
		List<Comment> allComments = portalApiService.getComments(productId);
		model.addAttribute("allComments",allComments);
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("join");

		return modelAndView;
	}
	
	@RequestMapping(value = "/leaderConfirm/{id}", method = RequestMethod.POST)
	public RedirectView leaderConfirm(@PathVariable("id") long productId, @ModelAttribute Listing listingDetails, Model model){
		log.info("Inside leaderConfirm method of PortalApiController");
		
log.info("HEE leaderConfirm: "+productId +" : "+listingDetails);				
		portalApiService.leaderConfirm(listingDetails, productId);

		return new RedirectView("/join/"+productId);
	}
	
	@RequestMapping(value = "/adminRelease10/{id}", method = RequestMethod.POST)
	public RedirectView adminRelease10(@PathVariable("id") long productId, Model model){
		log.info("Inside adminRelease10 method of PortalApiController");
		
log.info("HEE adminRelease10: "+productId);				
		portalApiService.adminUpdate(productId, 3);

		return new RedirectView("/join/"+productId);
	}
	
	@RequestMapping(value = "/leaderReceived10/{id}", method = RequestMethod.POST)
	public RedirectView leaderReceived10(@PathVariable("id") long productId, Model model){
		log.info("Inside leaderReceived10 method of PortalApiController");
		
log.info("HEE adminRelease10: "+productId);				
		portalApiService.leaderUpdate(productId, 3);

		return new RedirectView("/join/"+productId);
	}

	@RequestMapping(value = "/leaderProceedOrder/{id}", method = RequestMethod.POST)
	public RedirectView leaderProceedOrder(@PathVariable("id") long productId, Model model){
		log.info("Inside leaderProceedOrder method of PortalApiController");
		
log.info("HEE leaderProceedOrder: "+productId);				
		portalApiService.leaderUpdate(productId, 4);

		return new RedirectView("/join/"+productId);
	}
	
	@RequestMapping(value = "/leaderReceivedParcel/{id}", method = RequestMethod.POST)
	public RedirectView leaderReceivedParcel(@PathVariable("id") long productId, Model model){
		log.info("Inside leaderReceivedParcel method of PortalApiController");
		
log.info("HEE leaderReceivedParcel: "+productId);				
		portalApiService.leaderUpdate(productId, 5);

		return new RedirectView("/join/"+productId);
	}
	
	@RequestMapping(value = "/adminRelease90/{id}", method = RequestMethod.POST)
	public RedirectView adminRelease90(@PathVariable("id") long productId, Model model){
		log.info("Inside adminRelease10 method of PortalApiController");
		
log.info("HEE adminRelease90: "+productId);				
		portalApiService.adminUpdate(productId, 7);

		return new RedirectView("/join/"+productId);
	}
	
	@RequestMapping(value = "/passToJoiner/{pid}/{uid}", method = RequestMethod.POST)
	public RedirectView passToJoiner(@PathVariable("pid") long productId, @PathVariable("uid") long userId, Model model){
		log.info("Inside passToJoiner method of PortalApiController");
		
log.info("HEE passToJoiner: "+productId);				
		portalApiService.passToJoiner(productId, userId);

		return new RedirectView("/join/"+productId);
	}
	
	@RequestMapping(value = "/leaderReceived90/{id}", method = RequestMethod.POST)
	public RedirectView leaderReceived90(@PathVariable("id") long productId, Model model){
		log.info("Inside leaderReceived90 method of PortalApiController");
		
log.info("HEE leaderReceived90: "+productId);				
		portalApiService.leaderUpdate(productId, 10);

		return new RedirectView("/join/"+productId);
	}

	@RequestMapping(value = "/postComment/{pid}", method = RequestMethod.POST)
	public RedirectView postComment(@ModelAttribute Comment comment, @PathVariable("pid") long productId, Model model){
		log.info("Inside postComment method of PortalApiController");
		
		long userId = 2;
		
log.info("HEE postComment: "+productId);				
		portalApiService.postComment(comment, productId, userId);

		return new RedirectView("/join/"+productId);
	}
}
