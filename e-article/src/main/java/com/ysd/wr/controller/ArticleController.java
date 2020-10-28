package com.ysd.wr.controller;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ysd.wr.bean.Article;
import com.ysd.wr.bean.RespBean;
import com.ysd.wr.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {


    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    ArticleService articleService;

    @PostMapping("/")
    public RespBean addNewArticle(@RequestBody Article article) {
    	String nickname = article.getNickname();
    	String cateName = article.getCateName();
    	System.out.println("添加笔名是 article.setnickname：" + nickname+"添加的分类名是："+cateName); 
        int result = articleService.addNewArticle(article);        
        if (result == 1) {
            return new RespBean("success", article.getId() + "");
        } else {
            return new RespBean("error", article.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
        }
    }

    /**
     * 上传图片
     *
     * @return 返回值为图片的地址
     */
    @PostMapping("/uploadimg")
    public RespBean uploadImg(HttpServletRequest req, MultipartFile image) {
        StringBuffer url = new StringBuffer();
        String filePath = "/blogimg/" + sdf.format(new Date());
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url.append("/").append(imgName);
            return new RespBean("success", url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RespBean("error", "上传失败!");
    }
    
    //微服务 /article/all
    @GetMapping("/all")
    public List<Article> getArticleByState(@RequestParam(value = "state", defaultValue = "-1") Integer state, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count,Long uid,String keywords) { 
    	System.out.println("使用的是普通的：" + state ); 
    	return articleService.getArticleByState(state, page, count,uid,keywords);

    }
    //article/allArticle
    @GetMapping("/allArticle")
    public List<Article> getallArticle(Long uid) { 
    	System.out.println("使用的是普通的全部getallArticle：" + uid ); 
    	return articleService.getAllArticle(uid);

    }
    
    //微服务 /article/totalCount
    @GetMapping("/totalCount")
    public Integer gettotalCount(@RequestParam(value = "state", defaultValue = "-1") Integer state,Long uid,String keywords) {
           	
    	int  totalCount = articleService.getArticleCountByState(state, uid,keywords); 
    	System.out.println("使用的是普通的：" + state );  	
    	return totalCount;
    }
    @GetMapping("/{aid}")
    public Article getArticleById(@PathVariable Long aid) {
    	System.out.println("getArticleById:"+aid );
    	
        return articleService.getArticleById(aid);
    }

    @PutMapping("/dustbin/{state}")
    public RespBean updateArticleState(@RequestBody Long[] aids,@PathVariable Integer state) {
    	
    	System.out.println("使用的是普通的aids：" + aids + "和state:"+state);
    	
        if (articleService.updateArticleState(aids, state) == aids.length) {
            return new RespBean("success", "删除成功!");
        }
        return new RespBean("error", "删除失败!");
    }

    @PutMapping("/restore")
    public RespBean restoreArticle(Integer articleId) {
        if (articleService.restoreArticle(articleId) == 1) {
            return new RespBean("success", "还原成功!");
        }
        return new RespBean("error", "还原失败!");
    }

//    @GetMapping("/dataStatistics")
//    public Map<String,Object> dataStatistics() {
//    	
//        Map<String, Object> map = new HashMap<>();
//        //        List<String> categories = articleService.getCategories();
////        List<Integer> dataStatistics = articleService.getDataStatistics();
////        map.put("categories", categories);
////        map.put("ds", dataStatistics);
//        
//        return map;
//    }
}
