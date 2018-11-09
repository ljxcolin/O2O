package stu.ljx.o2o.service;


import java.util.Set;

import stu.ljx.o2o.dto.ImageHolder;
import stu.ljx.o2o.dto.ProductExecution;
import stu.ljx.o2o.entity.Product;
import stu.ljx.o2o.exception.ProductException;

public interface ProductService {
	
	/*添加商品*/
	//不仅要处理商品的缩略图，还要处理商品详情中的多个图片信息，参数过多，抽象出一个工具类ImageHandler
	/*ProductExecution addProduct(Product product, InputStream productImgIns, String productImgName, List<InputStream> productDetailIns, List<String> productDetailNames)
		throws ProductException;*/
	/**
	 * 添加商品
	 * @param product 商品
	 * @param productImg 商品缩略图封装类
	 * @param productDetailImgs 商品详情图片信息封装类
	 * @return ProductExecution 操作商品结果状态类
	 * @throws ProductException 商品操作异常类
	 */
	ProductExecution addProduct(Product product, ImageHolder productImg, Set<ImageHolder> productDetailImgs) throws ProductException;
	
	/**
	 * 获取商品列表
	 * @param productCnd
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ProductExecution getProductList(Product productCnd, int pageIndex, int pageSize);
	
	/**
	 * 获取指定ID的商品
	 * @param productId
	 * @return
	 */
	Product getProductById(Integer productId);

	/* 修改商品 */
	ProductExecution editProduct(Product product, ImageHolder productImg, Set<ImageHolder> productDetailImgs) throws ProductException;
	
	/* 上架或下架商品 */
	ProductExecution changeProductStatus(Product product);
	
}
