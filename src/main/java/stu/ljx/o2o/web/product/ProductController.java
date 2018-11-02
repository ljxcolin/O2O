package stu.ljx.o2o.web.product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import stu.ljx.o2o.dto.ImageHolder;
import stu.ljx.o2o.dto.ProductExecution;
import stu.ljx.o2o.entity.Product;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.enums.ProductStateEnum;
import stu.ljx.o2o.service.ProductService;
import stu.ljx.o2o.util.HttpSvlReqUtil;
import stu.ljx.o2o.util.SessionUtil;
import stu.ljx.o2o.util.VerifyCodeUtil;

@Controller
@RequestMapping("/product")
public class ProductController {

	//最大上传图片数量
	private static final int MAXIMAGECOUNT = 6;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 添加商品
	 * 1、获取前端传递过来的Product对象，使用jackson的API将其转换为Product对象
	 * 2、获取前端传递过来的商品缩略图以及商品详情图片，通过CommonsMultipartResolver来处理
	 * 3、调用Service层的服务来持久化数据及图片的操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addProduct(HttpServletRequest request, MultipartResolver fileResolver){
		Map<String, Object> modelMap = new HashMap<String, Object>();
        Product product = null;
        //商品图片缩略图（输入流和名称的封装类）
        ImageHolder thumbnail = null;
        //将HttpServletRequest转型为MultipartHttpServletRequest，可以很方便地得到文件名和文件内容
        MultipartHttpServletRequest fileRequest = null;
        //接收商品缩略图
        MultipartFile productImg = null;
        //接收商品详情图片
        Set<ImageHolder> productDetailImgs = new HashSet<ImageHolder>();

        //Step1:校验验证码
        if (!VerifyCodeUtil.verifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "验证码不正确");
            return modelMap;
        }

        try {
        	//Step2:使用jackson提供的API实例化Product
        	ObjectMapper mapper = new ObjectMapper();
        	//获取前端传递过来的product，约定好使用productStr
        	String productStr = HttpSvlReqUtil.getString(request, "productStr");
            product = mapper.readValue(productStr, Product.class);
            
	        //Step3:处理商品缩略图和商品详情图
            //判断request是否有文件上传，即多部分请求
            if (fileResolver.isMultipart(request)) {
            	//将request转换成多部分request
            	fileRequest = (MultipartHttpServletRequest) request;

            	//得到商品图片的CommonsMultipartFile，并构建ImageHolder对象，和前端约定好使用productImg传递
            	productImg = (CommonsMultipartFile) fileRequest.getFile("productImg");
            	if(productImg != null) {
            		//转化为ImageHolder，以满足使用service层方法入参类型
            		thumbnail = new ImageHolder(productImg.getInputStream(), productImg.getOriginalFilename());
            	}
            	//得到商品详情的列表，并构建ImageHolder对象，和前端约定使用detailImg + i传递 
            	for (int i = 0; i < MAXIMAGECOUNT; i++) {
            		MultipartFile detailImg = (CommonsMultipartFile) fileRequest.getFile("detailImg" + i);
            		if (detailImg == null) { //如果从请求中获取的到file为空，终止循环
            			break;
            		}
            		ImageHolder productDetailImg = new ImageHolder(detailImg.getInputStream(), detailImg.getOriginalFilename());
            		productDetailImgs.add(productDetailImg);
            	}
            	//Step4:调用Service层
            	//从session中获取shop信息，不依赖前端的传递更加安全
            	Shop currentShop = SessionUtil.getCurrentShop(request);
            	product.setShop(currentShop);
            	//调用Service层addProduct
            	ProductExecution pe = productService.addProduct(product, thumbnail, productDetailImgs);
            	if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
            		modelMap.put("success", true);
            	} else {
            		modelMap.put("success", false);
            		modelMap.put("errMsg", pe.getStateInfo());
            	}
            } else {
            	modelMap.put("success", false);
            	modelMap.put("errMsg", "上传图片不能为空");
            }    
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        } 
        return modelMap;
    }
	
	
}
