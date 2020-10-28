//package com.ysd.wr.job;
//
//import java.util.List;
//
//import org.quartz.JobExecutionContext;
//import org.quartz.JobExecutionException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.quartz.QuartzJobBean;
//
//import com.ysd.wr.bean.Article;
//import com.ysd.wr.controller.TongjiController;
//import com.ysd.wr.mapper.TongjiMapper;
//import com.ysd.wr.service.ArticleServiceClient;
//
//
//public class SyncJob extends QuartzJobBean {
//
//	private  static final Logger logger = LoggerFactory.getLogger(TongjiController.class);
//	
//	@Autowired
//    private ArticleServiceClient client;
//	
//    //保存当前用户的全部文章PageView数据并且相加(在TongjiController实现)
//	@Override
//	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
//		logger.info("开始同步");
//		try {
//			List<Article> articleList = this.client.getArticleByState(uid);
//			int sumPageView = 0 ;
//            for (Article article : articleList){
//            	sumPageView=sumPageView+article.getPageView();
//            }
//            
//            logger.info("开始同步sumPageView="+sumPageView);
//            
//	    }catch(Exception e) {
//			logger.error(e.getMessage(),e);
//		}
//		logger.info("结束同步");
//	}
//}
