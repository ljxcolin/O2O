package stu.ljx.o2o.service;

import java.util.List;

import stu.ljx.o2o.dto.ProductCategoryExecution;
import stu.ljx.o2o.entity.ProductCategory;
import stu.ljx.o2o.exception.ProductException;

public interface ProductCategoryService {
	
	List<ProductCategory> getProductCategories(Integer shopId);

	ProductCategoryExecution addProductCategories(List<ProductCategory> productCategories) throws ProductException;
	
	ProductCategoryExecution deleteProductCategory(Integer productCategoryId, Integer shopId) throws ProductException;
	
}
