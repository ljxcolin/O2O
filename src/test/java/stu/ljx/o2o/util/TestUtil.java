package stu.ljx.o2o.util;

import java.io.File;
//import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

//import javax.imageio.ImageIO;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import stu.ljx.o2o.dto.ImageHolder;

//import net.coobird.thumbnailator.Thumbnails;
//import net.coobird.thumbnailator.geometry.Positions;

public class TestUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(TestUtil.class);
	
	@Test
	public void testImageUtil() {
		//需加水印的原图片
//		File sourceFile = new File("E:/STSFile/Resources/images/ship.jpg");
		File sourceFile = new File("E:\\STSFile\\Resources\\images\\bird.jpg");
		//加完水印后输出的目标图片
//		File destFile = new File("E:/STSFile/Resources/images/ship-with-watermark.jpg");
		//水印图片
		File waterMarkFile = FileUtil.getWaterMarkFile();
		logger.info("waterMarkFileName: {}", waterMarkFile.getName());
		//ImageUtil.generateThumbnail(sourceFile, "\\");
		FileInputStream ins = null;
		try {
			ins = new FileInputStream(sourceFile);
			ImageUtil.generateThumbnail(new ImageHolder(ins, sourceFile.getName()), "\\");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("创建缩略图失败：" + e.toString());
		}
//		try {
			//加水印操作
//			Thumbnails.of(sourceFile).size(500, 500).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(waterMarkFile), 0.25f).outputQuality(0.8).toFile(destFile);
//			logger.info("水印添加成功，带水印图片：{}", destFile.getAbsolutePath());
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new RuntimeException("创建缩略图失败：" + e.toString());
//		}
	}
	
}
