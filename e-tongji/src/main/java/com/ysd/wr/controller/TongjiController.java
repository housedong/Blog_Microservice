package com.ysd.wr.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ysd.wr.bean.Article;
import com.ysd.wr.service.ArticleServiceClient;
import com.ysd.wr.service.TongjiService;


@RestController
@RequestMapping("/tongji")
public class TongjiController {
	@Autowired
    private ArticleServiceClient client;
	
	@Autowired
	TongjiService tongjiservice;
	
	 @GetMapping("/dataStatistics/{uid}")
	    public Map<String,Object> dataStatistics(@PathVariable Long uid) {
	        Map<String, Object> map = new HashMap<>();
	        
	        List<String> categories = tongjiservice.getCategories(uid);
	        List<Integer> dataStatistics = tongjiservice.getDataStatistics(uid);
	        map.put("categories", categories);
	        map.put("ds", dataStatistics);
	        return map;
	    }

	    @PutMapping("/pvStatisticsPerDay")
	    public void pvStatisticsPerDay(Long uid) {
//	    	INSERT INTO pv(countDate,pv,uid) VALUES(NOW(),#{sumPageView},#{uid}) 
	    	List<Article> articleList = this.client.getArticleByState(uid);
	    	int sumPageView = 0;
	        for (Article article : articleList){
	        	sumPageView=sumPageView+article.getPageView();
	        }
	    	tongjiservice.pvStatisticsPerDay( uid, sumPageView);
	    }
}
