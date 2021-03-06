package stu.ljx.o2o.util;

import java.io.File;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	//路径分隔符，Linux上为"/"，Windows上为"\"
	private static String separator = System.getProperty("file.separator");
	
	/**
	 * 获取图片根目录
	 * @return
	 */
	public static String getImgRootPath() {
		String os = System.getProperty("os.name");
		logger.debug("os.name={}", os);
		String rootPath = null;
		if(os.toLowerCase().startsWith("win")) { //Windows
			rootPath = "E:/ItemFile/o2o";
		}else { //Unix/Linux
			rootPath = "/home/colin/o2o";
		}
		rootPath = rootPath.replace("/", FileUtil.separator); //replace separator
		logger.debug("rootPath={}", rootPath);
		return rootPath;
	}
	
	/**
	 * 获取指定商铺的图片的实际目录
	 * 商铺的图片根据ID划分，商铺不同，其图片位于其对于的ID目录下
	 * 商铺图片最终路径为rootPath+shopImgPath
	 * @param shopId
	 * @return
	 */
	public static String getShopImgPath(Integer shopId) {
		if(shopId != null && shopId > 0) {
			String shopImgPath = "/upload/shopImage/" + shopId + "/";
			shopImgPath = shopImgPath.replace("/", FileUtil.separator);
			logger.debug("shopImgPath={}", shopImgPath);
			return shopImgPath;
		}
		logger.info("The shop doesn't exist!");
		return null;
	}
	
	/**
	 * 获取指定商铺指定商品及其详情的图片的实际目录
	 * 商品的图片根据商铺ID和商品ID划分，商铺和商品不同，其图片位于其对于的ID目录下
	 * 商品图片最终路径为rootPath+productImgPath
	 * @param shopId
	 * @return
	 */
	public static String getProductImgPath(Integer shopId, Integer productId) {
		if((shopId != null && shopId > 0) && (productId != null && productId > 0)) {
			String shopImgPath = getShopImgPath(shopId);
			String productImgPath = shopImgPath + "product_" + productId + "/";
			productImgPath = productImgPath.replace("/", FileUtil.separator);
			logger.debug("productImgPath={}", productImgPath);
			return productImgPath;
		}
		logger.info("The shop or the product doesn't exist!");
		return null;
	}
	
	/**
	 * 获取用户头像图片的目录，以用户名划分
	 * @param userName
	 * @return
	 */
	public static String getUserImgPath(String userName) {
		if((StringUtils.isNotBlank(userName))) {
			String userImgPath = "/upload/userImage/" + userName + "/";
			userImgPath = userImgPath.replace("/", FileUtil.separator);
			logger.debug("userImgPath={}", userImgPath);
			return userImgPath;
		}
		logger.info("The user doesn't exist!");
		return null;
	}
	
	/**
	 * 获取水印文件
	 * @return
	 */
	public static File getWaterMarkFile() {
		String waterMarkPath = FileUtil.getImgRootPath() + "/watermark/watermark.png";
		waterMarkPath = waterMarkPath.replace("/", FileUtil.separator);
		logger.debug("WaterMarkPath={}", waterMarkPath);
		return new File(waterMarkPath);
	}

}
