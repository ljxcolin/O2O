package stu.ljx.o2o.util;

import java.io.File;
import java.io.IOException;
//import java.io.InputStream;
//import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import stu.ljx.o2o.dto.ImageHolder;

public class ImageUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 将CommonsMultipartFile对象转为File对象
	 * @param cmfile spring用于接收上传文件的CommonsMultipartFile对象
	 * @return 转换后的File对象
	 */
	/*public static File multipartFileToFile(CommonsMultipartFile cmfile) {
		File file = null;
		try {
			file = new File(cmfile.getOriginalFilename());
			cmfile.transferTo(file); //Transfer the received file to the given destination file. 
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			logger.error("MultipartFileToFile failed：{}", e.getMessage());
		}
		return file;
	}*/
	
	/**
	 * 生成缩略图（商铺的门面照以及商品小图），shop的水印图标要存放在tb_shop的 shop_img字段
	 * @param ImageHolder 需添加水印的目标文件（即前台上传的图片）
	 * @param destPath 添加水印后的文件的存放目录
	 * @return 缩略图的相对路径（可减少因项目迁移带来的不必要的改动）
	 */
	public static String generateThumbnail(ImageHolder imgHolder, String destPath) {
		String relativePath = null;
		try {
			String fileName = randomFileName();
			String extension = getFileExtension(imgHolder.getFileName());
			validateDestPath(destPath);
			relativePath = destPath + fileName + extension;
			logger.info("图片相对路径：{}", relativePath);
			File destFile = new File(FileUtil.getImgRootPath() + relativePath);
			logger.info("图片绝对路径：{}", destFile.getAbsolutePath());
			//给目标文件添加水印后输出到目标存储目录
			//of：给哪些文件添加水印
			//size：缩略图尺寸大小
			//watermark：添加水印，指明位置、水印文件流和不透明度
			//outputQuality：指定输出时压缩程度
			//toFile：创建缩略图并输出到磁盘
			//Thumbnails.of(file).size(500, 500).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(FileUtil.getWaterMarkFile()), 0.25f).outputQuality(0.8).toFile(destFile);
			Thumbnails.of(imgHolder.getIns()).size(500, 500).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(FileUtil.getWaterMarkFile()), 0.25f).outputQuality(0.8).toFile(destFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建水印图片失败：" + e.toString());
		}
		return relativePath;
	}
	
	/**
	 * 生成单个无水印缩略图
	 * @param ImageHolder 需上传的目标文件（即前台上传的图片）
	 * @param destPath 上传后的文件的存放目录
	 * @return 缩略图的相对路径（可减少因项目迁移带来的不必要的改动）
	 */
	public static String generateRawImg(ImageHolder imgHolder, String destPath) {
		String relativePath = null;
		try {
			String fileName = randomFileName();
			String extension = getFileExtension(imgHolder.getFileName());
			validateDestPath(destPath);
			relativePath = destPath + fileName + extension;
			logger.info("图片相对路径：{}", relativePath);
			File destFile = new File(FileUtil.getImgRootPath() + relativePath);
			logger.info("图片绝对路径：{}", destFile.getAbsolutePath());
			Thumbnails.of(imgHolder.getIns()).size(300, 300).outputQuality(0.8).toFile(destFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("创建水印图片失败：" + e.toString());
		}
		return relativePath;
	}
	
	/**
	 * 批量生成商品详情图片
	 */
	public static Set<String> generateRawImgs(Set<ImageHolder> productDetailImgs, String destPath) {
		int count = 0;
        Set<String> relativePaths = new HashSet<String>();
        if (productDetailImgs != null && productDetailImgs.size() > 0) {
            validateDestPath(destPath);
            //为文件名随机命名
            String fileName = randomFileName();
            for (ImageHolder imgHolder : productDetailImgs) {
            	try {
	                //获取文件扩展名
	            	String extension = getFileExtension(imgHolder.getFileName());
	                //拼接新的文件名：destPath+fileName+i+extension
	                String relativePath = destPath + fileName + count + extension;
	                logger.info("图片相对路径：{}", relativePath);
	                //绝对路径的形式创建文件
	                File destFile = new File(FileUtil.getImgRootPath() + relativePath);
	                logger.info("图片完整路径：{}", destFile.getAbsolutePath());
                    //生成商品详情图片，不加水印，设置为比缩略图大一点的图片（因为是商品详情图片）
                    Thumbnails.of(imgHolder.getIns()).size(600, 300).outputQuality(0.5).toFile(destFile);
                    //将图片的相对路径名称添加到list中
                    relativePaths.add(relativePath);
                    count++;
                } catch (IOException e) {
                    e.printStackTrace();
                    throw new RuntimeException("创建图片失败：" + e.toString());
                }
            }
        }
        return relativePaths;
	}

	/**
	 * 生成随机文件名，规则：当前系统时间+5位随机数（10000~99999）
	 * @return
	 */
	private static String randomFileName() {
		String currentTime = sdf.format(new Date());
		int randomNo = (int) (Math.random() * (99999 - 10000 + 1)) + 10000;
		String randomFileName = currentTime + randomNo;
		logger.debug("fileName={}", randomFileName);
		return randomFileName;
	}

	/**
	 * 获取文件（图片）扩展名
	 * @param fileName 要获取的文件名称
	 * @return 返回扩展名
	 */
	private static String getFileExtension(String fileName) {
		String extension = fileName.substring(fileName.lastIndexOf("."));
		logger.debug("extension={}" + extension);
		return extension;
	}
	
	/**
	 * 校验目标存放目录是否存在，若不存在则逐级创建
	 * @param destPath
	 */
	private static void validateDestPath(String destPath) {
		String absolutePath = FileUtil.getImgRootPath() + destPath;
		File file = new File(absolutePath);
		if(!file.exists()) {
			file.mkdirs();
		}
	}
	
	/**
	 * 
	 * @param path
	 */
	public static void removePath(String path) {
		File file = new File(FileUtil.getImgRootPath() + path);
		if(file != null) {
			if(file.isDirectory()) {
				File[] subFiles = file.listFiles();
				for (File subFile : subFiles) {
					subFile.delete();
				}
			}
			file.delete();
		}
	}

}
