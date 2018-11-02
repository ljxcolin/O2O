package stu.ljx.o2o.service.impl;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import stu.ljx.o2o.dao.ProductImgMapper;
import stu.ljx.o2o.dao.ProductMapper;
import stu.ljx.o2o.dto.ImageHolder;
import stu.ljx.o2o.dto.ProductExecution;
import stu.ljx.o2o.entity.Product;
import stu.ljx.o2o.entity.ProductImg;
import stu.ljx.o2o.enums.ProductStateEnum;
import stu.ljx.o2o.exception.ProductException;
import stu.ljx.o2o.service.ProductService;
import stu.ljx.o2o.util.FileUtil;
import stu.ljx.o2o.util.ImageUtil;

@Service
public class ProductServiceImpl implements ProductService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ProductImgMapper productImgMapper;
	
	
	/**
	 * 添加商品，逻辑如下：
	 * 	1、处理商品的缩略图，获取相对路径，并将其写入tb_product表中的img_addr字段
	 * 	2、插入商品的同时取回商品的ID即product_id（Mybatis的useGeneratedKeys属性实现）
	 * 	3、为商品详情图片设置商品ID，并批量插入商品详情图片
	 * 	4、将商品详情图片批量更新到tb_proudct_img表
	 */
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public ProductExecution addProduct(Product product, ImageHolder productImg, Set<ImageHolder> productDetailImgs) throws ProductException {
		if (product != null && product.getShop() != null && product.getShop().getShopId() != null && product.getProductCategory() != null && product.getProductCategory().getProductCategoryId() != null) {
            //设置创建时间、最后修改时间及商品展示状态（默认为1：展示）
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            product.setEnableStatus(1);
            //如果文件的输入流和文件名不为空，添加文件到特定目录，并且将相对路径设置给product,这样product就有了ImgAddr，为下一步的插入tb_product提供了数据来源
            if (productImg != null) {
            	try {
					//商品参数齐全，可以插入商品并取回商品ID
            		int rowInsert = productMapper.insertProduct(product);
            		//更新商品缩略图
            		addProductImg(product, productImg); //imgAddr属性已设置好
            		int rowUpdate = productMapper.updateProductImg(product);
            		if(rowInsert < 1 || rowUpdate < 1) {
            			logger.error("addProduct error: {}", "添加商品失败");
    					throw new ProductException("添加商品失败");
            		}
            		//更新缩略图成功，可以批量处理商品详情图片
                    if (productDetailImgs != null && productDetailImgs.size() > 0) {
                    	addProductDetailImgs(product, productDetailImgs);
                    	return new ProductExecution(ProductStateEnum.SUCCESS, product);
                    }else {
                    	logger.error("addProduct error: {}", "商品详情图片为空");
    					return new ProductExecution(ProductStateEnum.NULL_DETAIL_IMAGE);
                    }
				} catch (Exception e) {
					logger.error("addProduct error: {}", e.getMessage());
					throw new ProductException("添加商品失败：" + e.getMessage());
				}
            }else {
            	logger.error("addProduct error: {}", "商品图片为空");
            	 return new ProductExecution(ProductStateEnum.NULL_IMAGE);
            }
        } else {
        	logger.error("addProduct error: {}", "该商品或所属商铺或所属类别不存在");
            return new ProductExecution(ProductStateEnum.PARAM_ERROR);
        }
	}

	/**
	 * 生成商品缩略图，并将relativePath设置给product的imgAddr属性
	 * @param product
	 * @param productImg
	 */
	private void addProductImg(Product product, ImageHolder productImg) {
		Integer shopId = product.getShop().getShopId();
		Integer productId = product.getProductId();
		String productImgPath = FileUtil.getProductImgPath(shopId, productId);
		String relativePath = ImageUtil.generateThumbnail(productImg, productImgPath);
		product.setImgAddr(relativePath);
	}
	
	/**
	 * 批量处理商品详情图片
	 * @param product
	 * @param productDetailImgs
	 */
	private void addProductDetailImgs(Product product, Set<ImageHolder> productDetailImgs) {
		String productImgPath = FileUtil.getProductImgPath(product.getShop().getShopId(), product.getProductId());
		//生成商品详情图片，不加水印
		Set<String> imgAddrs = ImageUtil.generateRawImgs(productDetailImgs, productImgPath);
		if (imgAddrs != null && imgAddrs.size() > 0) {
            Set<ProductImg> productImgList = new HashSet<ProductImg>();
            for (String imgAddr : imgAddrs) {
                ProductImg productImg = new ProductImg();
                productImg.setImgAddr(imgAddr);
                productImg.setProductId(product.getProductId());
                productImg.setCreateTime(new Date());
                productImgList.add(productImg);
            }
            try {
                int row = productImgMapper.batchInsertProductImg(productImgList);
                if (row < 1) {
                    throw new RuntimeException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductException(e.toString());
            }
		}
	}

}
