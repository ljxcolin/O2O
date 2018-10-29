package stu.ljx.o2o.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stu.ljx.o2o.dao.ProductCategoryMapper;
import stu.ljx.o2o.dto.ProductCategoryExecution;
import stu.ljx.o2o.entity.ProductCategory;
import stu.ljx.o2o.enums.ProductCategoryStateEnum;
import stu.ljx.o2o.exception.ProductException;
import stu.ljx.o2o.service.ProductCategoryService;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductCategoryServiceImpl.class);
	
	@Autowired
	private ProductCategoryMapper productCategoryMapper;
	
	@Override
	public List<ProductCategory> getProductCategories(Integer shopId) {
		return productCategoryMapper.queryProductCategory(shopId);
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public ProductCategoryExecution addProductCategories(List<ProductCategory> productCategories) throws ProductException {
		if(productCategories != null && productCategories.size() > 0) {
			try {
				//批量新增商品类别
				int row = productCategoryMapper.batchInsertProductCategory(productCategories);
				if(row > 0) {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS, productCategories, row);
				}else {
					logger.error("addProductCategories error: {}", "批量新增商品类别失败");
					return new ProductCategoryExecution(ProductCategoryStateEnum.FAILED);
				}
			} catch (Exception e) {
				logger.error("addProductCategories error: {}", e.toString());
				throw new ProductException("addProductCategories error: " + e.getMessage());
			}
		}else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_CATEGORY_LIST);
		}
	}

	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public ProductCategoryExecution deleteProductCategory(Integer productCategoryId, Integer shopId) {
		try {
			//直接删除商品类别
            int row = productCategoryMapper.deleteProductCategory(productCategoryId, shopId);
            if (row > 0) {
                return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
            } else {
                return new ProductCategoryExecution(ProductCategoryStateEnum.FAILED);
            }
        } catch (Exception e) {
            throw new ProductException(e.getMessage());
        }
	}
	
}
