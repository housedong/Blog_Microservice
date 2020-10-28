package com.ysd.wr.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ysd.wr.bean.Category;
import com.ysd.wr.bean.RespBean;
import com.ysd.wr.service.CategoryService;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
	    @Autowired
        CategoryService categoryService;

	    @GetMapping("/all")
	    public List<Category> getAllCategories() {
	        return categoryService.getAllCategories();
	    }
	    
	    @DeleteMapping("/{ids}")
	    public RespBean deleteById(@PathVariable String ids) {
	    	boolean result = categoryService.deleteCategoryByIds(ids);
	        if (result) {
	            return new RespBean("success", "删除成功!");
	        }
	        return new RespBean("error", "删除失败!");
	    }
	    
	    @GetMapping("/{cid}")
	    public String getCateNameById(@PathVariable Long cid) {
	    	System.out.println("getCateNameById:"+cid );
	    	
	      return categoryService.getCateNameById(cid);
	    }
	    @PostMapping("/")
	    public RespBean addNewCate(@RequestBody Category category) {
	    	
	    	System.out.println("category:" + category.getCateName()  );
	    	
        if ("".equals(category.getCateName()) || category.getCateName() == null) {
	            return new RespBean("error", "请输入栏目名称!");
	        }

	        int result = categoryService.addCategory(category);

	        if (result == 1) {
	            return new RespBean("success", "添加成功!");
	        }
	        return new RespBean("error", "添加失败!");
	    }

	    @PutMapping("/")
	    public RespBean updateCate(@RequestBody Category category) {
	    	int i = categoryService.updateCategoryById(category);
	        if (i == 1) {
	            return new RespBean("success", "修改成功!");
	        }
	        return new RespBean("error", "修改失败!");
	    }
}
