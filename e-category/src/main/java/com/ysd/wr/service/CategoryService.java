package com.ysd.wr.service;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ysd.wr.bean.Category;
import com.ysd.wr.mapper.CategoryMapper;

@Service
@Transactional
public class CategoryService {
	    @Autowired
		CategoryMapper categoryMapper;
	    	
	    public List<Category> getAllCategories() {
	        return categoryMapper.getAllCategories();
	    }

	    public boolean deleteCategoryByIds(String ids) {
	    	String[] split = ids.split(",");
	        int result = categoryMapper.deleteCategoryByIds(split);
	        return result == split.length;
	    }
	 
	    public int updateCategoryById(Category category) {
	        return categoryMapper.updateCategoryById(category);
	    }
	
	    public int addCategory(Category category) {
	        category.setDate(new Timestamp(System.currentTimeMillis()));
	        return categoryMapper.addCategory(category);
	        
	    }
	    public String getCateNameById(Long cid) {
	    	String cateName = categoryMapper.getCateNameById(cid);
	        System.out.println("getCategoryNameById:"+cateName);
	        return cateName;
	    }


	    
}
