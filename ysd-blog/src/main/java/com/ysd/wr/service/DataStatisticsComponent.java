package com.ysd.wr.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.ysd.wr.untils.Util;


/**
 * Created by sang on 2017/12/25.
 */
@Component
public class DataStatisticsComponent {
    private static final Logger logger = LoggerFactory.getLogger(DataStatisticsComponent.class);
	private static final String PV_SRV_COMPUTE_URI = "http://localhost:8060/tongji/pvStatisticsPerDay"; 
	
	@Autowired
    private RestTemplate client;
    //每天执行一次，统计PV
    @Scheduled(cron = "1 0 0 * * ?")
    public void pvStatisticsPerDay() {
    	try {
    		Long uid = Util.getCurrentUser().getId(); 
    		this.client.put(PV_SRV_COMPUTE_URI,uid);
    	}catch(Exception e) {
    		logger.error(e.getMessage(), e);
    	}
    }  
}
