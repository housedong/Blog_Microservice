package com.ysd.wr.controller.admin;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ysd.wr.bean.Article;
import com.ysd.wr.bean.RespBean;
import com.ysd.wr.exception.BusinessException;
import com.ysd.wr.untils.Util;




/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	private RestTemplate client;
	private static final String URL_ASV_ALL = "http://localhost:8060/admin/article/all";
	private static final String URL_ASV_TOTAL = "http://localhost:8060/admin/article/totalCount";
	private static final String URL_ASV_D = "http://localhost:8060/admin/article/dustbin/{state}";

    //admin//article/all  web
    @RequestMapping(value = "/article/all", method = RequestMethod.GET)
    public Map<String, Object> getArticleByStateByAdmin(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count,Long uid, String keywords) {
    	
    	try {
    		Long uid1 = Util.getCurrentUser().getId();
			String listUrl = URL_ASV_ALL + "?state={state}&page={page}&count={count}"
				+ "&uid={uid}&keywords={keywords}"; 
		    Article[] ary = this.client.getForObject(listUrl, Article[].class, -2, page, count, uid1, "");
			
			String totalCountUrl = URL_ASV_TOTAL + "?keywords={keywords}";
			
			Integer totalCount = this.client.getForObject(totalCountUrl, Integer.class,keywords);
			Map<String, Object> map = new HashMap<>();
			map.put("totalCount", totalCount);
	        map.put("articles", ary);
	        return map;
    		}catch (Exception e) {
    			logger.error("call article service error!",e.getMessage());
    			throw new BusinessException("call article service error!",e);
    		}
    }

    @RequestMapping(value = "/article/dustbin", method = RequestMethod.PUT)
    public RespBean updateArticleState(Long[] aids, Integer state) {
    	try {
    		System.out.println("使用的是WEBadmin/article/dustbin aids：" +aids+"state:"+state); 
    		this.client.put(URL_ASV_D,aids,state);
    		return new RespBean("success", "删除成功!");
    		}catch (Exception e) {
    			logger.error("call article service error!",e.getMessage());
    			throw new BusinessException("call article service error!",e);
    			//return new RespBean("success", "删除失败!");     		    
    		}

    }
}