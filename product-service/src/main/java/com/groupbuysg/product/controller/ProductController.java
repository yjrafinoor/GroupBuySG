package com.groupbuysg.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.groupbuysg.product.entity.Product;
import com.groupbuysg.product.service.ProductService;
import com.groupbuysg.product.valueobject.ResponseObject;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/products")
@Slf4j
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/")
	public List<Product> itemsList (){
		log.info("Inside itemsList method of ProductController");
		return productService.itemsList();
	}
	
	@PostMapping("/create/{uid}")
	public Product itemCreate (@RequestBody Product product, @PathVariable("uid") Long userId){
		log.info("Inside itemCreate method of ProductController");
		return productService.itemCreate(product, userId);
	}
	
	@GetMapping("/list/{pid}")
	public ResponseObject getItemById(@PathVariable("pid") Long productId){
		log.info("Inside getItemById method of ProductController");
		return productService.getItemById(productId);
	}
	
	@PutMapping("/update/{pid}")
	public Product updateItem (@PathVariable("pid") Long productId, @RequestBody Product product){
		log.info("Inside updateItem method of ProductController");
		return productService.updateItem(productId, product);
	}
	
	@DeleteMapping("/delete/{pid}")
	public ResponseObject deleteItemById(@PathVariable("pid") Long productId){
		log.info("Inside deleteItemById method of ProductController");
		return productService.deleteItemById(productId);
	}
	
	@PostMapping("/close/{pid}")
	public Product closeItem (@PathVariable("pid") Long productId){
		log.info("Inside closeItem method of ProductController");
		return productService.closeItem(productId);
	}
	
}


