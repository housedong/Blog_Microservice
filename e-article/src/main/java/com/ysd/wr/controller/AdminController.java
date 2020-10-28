package com.ysd.wr.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ysd.wr.bean.Article;
import com.ysd.wr.bean.RespBean;
import com.ysd.wr.service.ArticleService;

/**
 * 超级管理员专属Controller
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ArticleService articleService;
    
    //admin//article/all  微服务
    @RequestMapping(value = "/article/all", method = RequestMethod.GET)    
    public List<Article> getArticleByStateByAdmin(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, Long uid,String keywords) {
    	System.out.println("使用的是admin/article/all articles：" ); 
    	return articleService.getArticleByStateByAdmin(page, count, keywords);       

    }
    //admin//article/totalCount  微服务
    @RequestMapping(value = "/article/totalCount", method = RequestMethod.GET)    
    public Integer getAdminTotalCount(String keywords) {
    	Integer totalCount=	articleService.getArticleCountByState(1, null, keywords);
    	System.out.println("使用的是admin/article/totalCount articles：" ); 
    	return totalCount;       
    }
    @RequestMapping(value = "/article/dustbin/{state}", method = RequestMethod.PUT)
    public RespBean updateArticleState(@RequestBody Long[] aids,@PathVariable Integer state) {
    	System.out.println("使用的是admin/article/dustbin aids：" +aids+"state:"+state); 
        if (articleService.updateArticleState(aids, state) == aids.length) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }
}
