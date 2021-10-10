package com.groupbuysg.product.service;

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
	
	public Product itemCreate(Product product, Long userId) {
		log.info("Inside itemCreate method of ProductService");
		product.setStatus("OPEN");
		
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
	
	public ResponseObject getItemById(Long productId) {
		log.info("Inside getItemById method of ProductService");
		ResponseObject obj=new ResponseObject();
		Product product=productRepository.findByProductId(productId);	
		
		User user=
				restTemplate.getForObject("http://USER-SERVICE/users/list/" + product.getUserId()
				, User.class);
		
		obj.setProduct(product);
		obj.setUser(user);
		
		return obj;
	}
	
	public Product updateItem(Long productId, Product productDetails) {
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
	
	public ResponseObject deleteItemById(Long productId) {
		log.info("Inside deleteItemById method of ProductService");
		productRepository.deleteById(productId);	
		return null;
	}
	
	public Product closeItem(Long productId) {
		log.info("Inside closeItem method of ProductService");
		Product product = new Product();
		product = productRepository.findByProductId(productId);
		product.setStatus("CLOSED");
		return productRepository.save(product);
	}

}