package com.ysd.wr.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.ysd.wr.bean.Article;


@FeignClient("article-service")
public interface ArticleServiceClient {
	
	
	@GetMapping("/article/allArticle")
	public List<Article> getArticleByState(Long uid);
	 

}
