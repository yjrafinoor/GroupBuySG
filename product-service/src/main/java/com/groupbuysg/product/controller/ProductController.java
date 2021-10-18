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
	
	@GetMapping("/openList")
	public List<Product> itemsListOpen (){
		log.info("Inside itemsListOpen method of ProductController");
		return productService.itemsListOpen();
	}
	
	@GetMapping("/progressList")
	public List<Product> itemsListProgress (){
		log.info("Inside itemsListProgress method of ProductController");
		return productService.itemsListProgress();
	}
	
	@GetMapping("/closeList")
	public List<Product> itemsListClose (){
		log.info("Inside itemsListClose method of ProductController");
		return productService.itemsListClose();
	}
	
	@PostMapping("/create/{uid}")
	public Product itemCreate (@RequestBody Product product, @PathVariable("uid") long userId){
		log.info("Inside itemCreate method of ProductController");
		return productService.itemCreate(product, userId);
	}
	
	@GetMapping("/list/{pid}")
	public Product getItemById(@PathVariable("pid") long productId){
		log.info("Inside getItemById method of ProductController");
		return productService.getItemById(productId);
	}
	
	@PostMapping("/update/{pid}")
	public Product updateItem (@PathVariable("pid") long productId, @RequestBody Product product){
		log.info("Inside updateItem method of ProductController");
		return productService.updateItem(productId, product);
	}
	
	@DeleteMapping("/delete/{pid}")
	public ResponseObject deleteItemById(@PathVariable("pid") long productId){
		log.info("Inside deleteItemById method of ProductController");
		return productService.deleteItemById(productId);
	}
	
	@PostMapping("/status/{pid}/{code}")
	public Product updateStatus (@PathVariable("pid") long productId, @PathVariable("code") int code){
		log.info("Inside updateStatus method of ProductController");
		return productService.updateStatus(productId, code);
	}
	
}


