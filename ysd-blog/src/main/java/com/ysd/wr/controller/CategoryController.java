package com.ysd.wr.controller;


import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ysd.wr.bean.Article;
import com.ysd.wr.bean.Category;
import com.ysd.wr.bean.RespBean;
import com.ysd.wr.exception.BusinessException;

import java.awt.PageAttributes.MediaType;
import java.util.Arrays;
import java.util.List;

/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin/category")
public class CategoryController {
	 private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);
		
		private static final String URL_CSV_ALL = "http://localhost:8060/admin/category/all";
		private static final String URL_CSV_NAME = "http://localhost:8060/admin/category/{cid}";
		private static final String URL_CSV_IDS = "http://localhost:8060/admin/category/{ids}";
		private static final String URL_CSV = "http://localhost:8060/admin/category/";

		@Autowired
		private RestTemplate client;
		
		@GetMapping("/all")
		List<Category> getCityList(){
			try {
			Category[] arr =  this.client.getForObject(URL_CSV_ALL,Category[].class);
			return Arrays.asList(arr);
			}catch (Exception e) {
				logger.error("call category service error!",e.getMessage());
				throw new BusinessException("call category service error!",e);
			}
		}
		
		@GetMapping("/{cid}")
		public String  getCateNameById(@PathVariable Long cid){
			try {
				return this.client.getForObject(URL_CSV_NAME,String.class,cid);
			
			}catch (Exception e) {
				logger.error("call category service error!",e.getMessage());
				throw new BusinessException("call category service error!",e);
			}
		}
		
		 @DeleteMapping("/{ids}")
		 public RespBean deleteById(@PathVariable String ids) {
		    	try {
		    		this.client.delete(URL_CSV_IDS,ids);		 	  
		 	        return new RespBean("success", "删除成功!");		 	        		 	       
		    		}catch (Exception e) {
		    			logger.error("call category service error!",e.getMessage());
		    			//throw new BusinessException("call category service error!",e);
		    			return new RespBean("error", "删除失败!");	    			
		    		}
		    }
	    
	    
	    
	    @PostMapping("/")
	    public RespBean addNewCate(Category category) {
	    	
	    	System.out.println("category:" + category.getCateName()  );
	    	try {
	    		//String listUrl = URL_CSV + "?category={category}";
	    		return this.client.postForObject(URL_CSV,category, RespBean.class);
	    		
	    		}catch (Exception e) {
	    			logger.error("call category service error!",e.getMessage());
	    			throw new BusinessException("call category service error!",e);
	    		}
	    }
	   
	    @PutMapping("/")
	    public RespBean updateCate(Category category) {	    	
	    	try {	    		
	    		this.client.put(URL_CSV,category);	    	
	 	        return new RespBean("success", "修改成功!");   
	    		}catch (Exception e) {
	    			logger.error("call category service error!",e.getMessage());
	    			//throw new BusinessException("call category service error!",e);
	    			return new RespBean("error", "修改失败!");
	    		}
	   	}	    
}
