package com.ysd.wr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ysd.wr.bean.Category;

@Mapper
public interface CategoryMapper {
	
//    List<Category> getAllCategories();
//
//    void deleteCategoryByIds( int ids);
//
//    Category updateCategoryById(Category category);
//    
//    Category addCategory(Category category);
    
    List<Category> getAllCategories();

    int deleteCategoryByIds(@Param("ids") String[] ids);

    int updateCategoryById(Category category);

    int addCategory(Category category);

	String getCategoryNameById(Long cid);

	String getCateNameById(Long cid);
}
