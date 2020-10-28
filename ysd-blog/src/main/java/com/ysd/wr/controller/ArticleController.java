package com.ysd.wr.controller;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.ysd.wr.bean.Article;
import com.ysd.wr.bean.RespBean;
import com.ysd.wr.exception.BusinessException;
import com.ysd.wr.untils.Util;


@RestController
@RequestMapping("/article")
public class ArticleController {
    private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);

	private static final String URL_ASV = "http://localhost:8060/article/";
	private static final String URL_ASV_ALL = "http://localhost:8060/article/all";
	private static final String URL_ASV_TOTAL = "http://localhost:8060/article/totalCount";
	private static final String URL_ASV_AID = "http://localhost:8060/article/{aid}";
	private static final String URL_ASV_RESTORE = "http://localhost:8060/article/restore";
	private static final String URL_ASV_UP = "http://localhost:8060/article/uploadimg";
	private static final String URL_ASV_D = "http://localhost:8060/article/dustbin/{state}";
	private static final String URL_TSV_DATA = "http://localhost:8060/tongji/dataStatistics/{uid}";
	private static final String URL_CSV_NAME = "http://localhost:8060/admin/category/{cid}";
	@Autowired
	private RestTemplate client;

	 @PostMapping("/")
	    public RespBean addNewArticle(Article article) {
	    	try {
	    		String nickname=Util.getCurrentUser().getNickname();
	    		Long cid = article.getCid();
	    		article.setNickname(nickname);
	    		String cateName =this.client.getForObject(URL_CSV_NAME,String.class,cid);
	    		article.setCateName(cateName);	    		
	    		System.out.println("添加笔名是 article.setnickname：" + nickname+"添加分类名是 cid："+cateName);
	    		return this.client.postForObject(URL_ASV,article, RespBean.class);	    		
	    		}catch (Exception e) {
	    			logger.error("call article service error!",e.getMessage());
	    			throw new BusinessException("call article service error!",e);
	    		}
	    }

	    /**
	     * 上传图片
	     *
	     * @return 返回值为图片的地址
	     */
	    @PostMapping("/uploadimg")
	    public RespBean uploadImg(HttpServletRequest req, MultipartFile image) {
	    	try {
	    		return this.client.getForObject(URL_ASV_UP,RespBean.class,req,image);
	    		}catch (Exception e) {
	    			logger.error("call article service error!",e.getMessage());
	    			throw new BusinessException("call article service error!",e);
	    		}
	    }
	    
     // /article/all web
		@GetMapping("/all")
	    public Map<String, Object> getArticleByState(@RequestParam(value = "state", defaultValue = "-1") Integer state, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count,Long uid,String keywords) {
	    	try {
	    		
	    		Long uid1 = Util.getCurrentUser().getId(); 
				String listUrl = URL_ASV_ALL + "?state={state}&page={page}&count={count}"
					+ "&uid={uid}&keywords={keywords}";					
				Article[] ary = this.client.getForObject(listUrl, Article[].class,state,page,count, uid1,keywords);				 
				System.out.println("Article[] ary：" + ary);	
				
				String totalCountUrl = URL_ASV_TOTAL + "?state={state}&uid={uid}&keywords={keywords}";				
				Integer totalCount = this.client.getForObject(totalCountUrl, Integer.class,state,uid1,keywords);
				
				Map<String, Object> map = new HashMap<>();
				map.put("totalCount", totalCount);
		        map.put("articles", ary);
		        return map;
				         
	    		}catch (Exception e) {
	    			logger.error("call article service error!",e.getMessage());
	    			throw new BusinessException("call article service error!",e);
	    		}
	    } 
	    
	    @GetMapping("/{aid}")
	    public Article getArticleById(@PathVariable Long aid) {
	    	try {
	    		System.out.println("getArticleById:"+aid );
	    		return this.client.getForObject(URL_ASV_AID,Article.class,aid);
	    		}catch (Exception e) {
	    			logger.error("call article service error!",e.getMessage());
	    			throw new BusinessException("call article service error!",e);
	    		}
	    }

	    @PutMapping("/dustbin")
	    public RespBean updateArticleState(Long[] aids, Integer state) {
	    	try {
	    		System.out.println("aids:" + aids+"和state:"+state);	
	    	    this.client.put(URL_ASV_D,aids,state);
	    	     return new RespBean("success", "删除成功!");
	    	            	        
	    		}catch (Exception e) {
	    			logger.error("call article service error!",e.getMessage());
	    			//throw new BusinessException("call article service error!",e);
	    			return new RespBean("error", "删除失败!");	    			
	    		}
	    }

	    @PutMapping("/restore")
	    public RespBean restoreArticle(Integer articleId) {
	    	try {
	    		this.client.put(URL_ASV_RESTORE,articleId);
	    		return new RespBean("success", "还原成功!");
	    		}catch (Exception e) {
	    			logger.error("call article service error!",e.getMessage());
	    			//throw new BusinessException("call article service error!",e);
	    			return new RespBean("success", "还原成功!");	    			
	    		}
	    }

	    @GetMapping("/dataStatistics")
	    public Map<String,Object> dataStatistics() {	    	
	    	try {
	    		Long uid = Util.getCurrentUser().getId();
	    		Map<String, Object> map = new HashMap<>();
	    		map =this.client.getForObject(URL_TSV_DATA,Map.class,uid);
	    		//Article[] ary = this.client.getForObject(URL_TSV_DATA, Article[].class,state,page,count, uid1,"");
				System.out.println("map：" + map);
				
				//Map<String, Object> map = new HashMap<>();
		        //map.put("articles", ary);
		        return map;	    		
	    		}catch (Exception e) {
	    			logger.error("call article service error!",e.getMessage());
	    			throw new BusinessException("call article service error!",e);
	    		}
	    }
	    

}
