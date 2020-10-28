package com.ysd.wr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import com.ysd.wr.mapper.TongjiMapper;


@Service
@Transactional
public class TongjiService {
	
	@Autowired
	TongjiMapper tjmapper;
	
    public void pvStatisticsPerDay(Long uid ,int sumPageView) {
    	tjmapper.pvStatisticsPerDay(uid ,sumPageView);
    }

    /**
     * 获取最近七天的数据
     * @return
     */
    public List<Integer> getDataStatistics(@PathVariable Long uid) {
        return tjmapper.getDataStatistics(uid);
    }   
    
  /**
  * 获取最近七天的日期
  * @return
  */
    public List<String> getCategories(@PathVariable Long uid) {
     return tjmapper.getCategories(uid);
   }

    
}
