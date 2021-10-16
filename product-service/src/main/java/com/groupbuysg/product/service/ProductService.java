package com.groupbuysg.product.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.groupbuysg.product.entity.Product;
import com.groupbuysg.product.repository.ProductRepository;
import com.groupbuysg.product.valueobject.ResponseObject;
import com.groupbuysg.product.valueobject.User;
import com.groupbuysg.product.valueobject.Listing;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private RestTemplate restTemplate;

	public List<Product> itemsList(){
		log.info("Inside itemsList method of ProductService");
		return productRepository.findAll();
	}
	
	public Product itemCreate(Product product, long userId) {
		log.info("Inside itemCreate method of ProductService");
		product.setStatus("OPEN");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        product.setCreatedDate(dateFormat.format(date).toString());
        		
		product.setUserId(userId);
		productRepository.save(product);
		
		Listing listing = new Listing();
		//Create Leader
		restTemplate.postForObject("http://LISTING-SERVICE/listings/createLeader/" + product.getProductId() + "/" + product.getUserId()
		, listing, Listing.class);
		//Create Admin
		restTemplate.postForObject("http://LISTING-SERVICE/listings/createAdmin/" + product.getProductId()
		, listing, Listing.class);
		
		return product;
	}
	
	public Product getItemById(long productId) {
		log.info("Inside getItemById method of ProductService");
		ResponseObject obj=new ResponseObject();
		Product product=productRepository.findByProductId(productId);
		
		/*
		User user=
				restTemplate.getForObject("http://USER-SERVICE/users/list/" + product.getUserId()
				, User.class);
		
		obj.setProduct(product);
		obj.setUser(user);
		
		return obj;
		*/
		return product;
	}
	
	public Product updateItem(long productId, Product productDetails) {
		log.info("Inside updateItem method of ProductService");
		Product product = productRepository.findByProductId(productId);
		product.setProductName(productDetails.getProductName());
		product.setDescription(productDetails.getDescription());
		product.setDetails(productDetails.getDetails());
		product.setUrl(productDetails.getUrl());
		product.setDueDate(productDetails.getDueDate());
		product.setCategory(productDetails.getCategory());
		product.setStatus(product.getStatus());
		product.setFirstQuantity(productDetails.getFirstQuantity());
		product.setSecondPrice(productDetails.getFirstPrice());
		product.setSecondQuantity(productDetails.getSecondQuantity());
		product.setFirstPrice(productDetails.getSecondPrice());
		product.setThirdQuantity(productDetails.getThirdQuantity());
		product.setThirdPrice(productDetails.getThirdPrice());
		product.setCreatedDate(product.getCreatedDate());
		product.setUserId(product.getUserId());
		return productRepository.save(product);
	}
	
	public ResponseObject deleteItemById(long productId) {
		log.info("Inside deleteItemById method of ProductService");
		productRepository.deleteById(productId);	
		return null;
	}
	
	public Product updateStatus(long productId, int code) {
		log.info("Inside closeItem method of ProductService");
		Product product = new Product();
		product = productRepository.findByProductId(productId);
		
		if(code==1) {
			product.setStatus("IN PROGRESS");
		}
		
		if(code==2) {
			product.setStatus("CLOSED");
		}
		return productRepository.save(product);
	}

}