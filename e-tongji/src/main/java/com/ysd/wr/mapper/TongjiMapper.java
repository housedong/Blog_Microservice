package com.ysd.wr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.ysd.wr.bean.Article;

@Mapper
public interface TongjiMapper {
	

    //INSERT INTO pv(countDate,pv,uid) SELECT NOW(),SUM(pageView),uid FROM article GROUP BY uid
    void pvStatisticsPerDay(Long uid ,int sumPageView);

    List<String> getCategories(Long uid);

    List<Integer> getDataStatistics(Long uid);
}
