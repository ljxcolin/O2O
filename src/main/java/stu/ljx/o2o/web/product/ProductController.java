package stu.ljx.o2o.web.product;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import stu.ljx.o2o.dto.ImageHolder;
import stu.ljx.o2o.dto.ProductExecution;
import stu.ljx.o2o.entity.Product;
import stu.ljx.o2o.entity.ProductCategory;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.enums.ProductStateEnum;
import stu.ljx.o2o.service.ProductCategoryService;
import stu.ljx.o2o.service.ProductService;
import stu.ljx.o2o.util.HttpSvlReqUtil;
import stu.ljx.o2o.util.PageUtil;
import stu.ljx.o2o.util.VerifyCodeUtil;

@Controller
@RequestMapping("/product")
public class ProductController {

	//最大上传图片数量
	private static final int MAXIMAGECOUNT = 6;
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductCategoryService productCategorySerivce;
	
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
        //获取前端传递过来的页码
        int pageIndex = HttpSvlReqUtil.getInteger(request, "pageIndex");

        //从session中获取shop信息，主要是获取shopId，不依赖前台的参数，尽可能保证安全
        Shop currentShop = getCurrentShop(request);
        //空值判断
        if (pageIndex > 0 && currentShop != null && currentShop.getShopId() != null) {
            //获取前台可能传递过来的需要查询的条件
            Integer productCategoryId = HttpSvlReqUtil.getInteger(request, "category");
            String productName = HttpSvlReqUtil.getString(request, "name");
            Integer enableStatus = HttpSvlReqUtil.getInteger(request, "status");
            //拼装查询条件，根据前端传入的条件进行组合
            Product productCnd = combineProductCnd(currentShop, productCategoryId, productName, enableStatus);
            //调用服务
            ProductExecution pe = productService.getProductList(productCnd, pageIndex, PageUtil.PAGESIZE);
            //将结果返回给前台
            modelMap.put("success", true);
            modelMap.put("productList", pe.getProductList());
            modelMap.put("count", pe.getCount());
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "分页错误或商铺不存在");
        }
        return modelMap;
	}	
	
	/**
	 * 组装查询条件
	 * @param currentShop
	 * @param productCategoryId
	 * @param productName
	 * @return
	 */
	private Product combineProductCnd(Shop currentShop, Integer productCategoryId, String productName, Integer enableStatus) {
		Product productCnd = new Product();
		productCnd.setShop(currentShop);
		productCnd.setProductName(productName);
		if(productCategoryId != -1) {
			ProductCategory pc = new ProductCategory();
			pc.setProductCategoryId(productCategoryId);
			productCnd.setProductCategory(pc);
		}
		if(enableStatus != -1) {
			productCnd.setEnableStatus(enableStatus);
		}
		return productCnd;
	}

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
	public Map<String, Object> addProduct(HttpServletRequest request, CommonsMultipartResolver fileResolver){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//Step1:校验验证码
		if (!VerifyCodeUtil.verifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			return modelMap;
		}
		
        Product product = null;
        //商品图片缩略图（输入流和名称的封装类）
        ImageHolder thumbnail = null;
        //将HttpServletRequest转型为MultipartHttpServletRequest，可以很方便地得到文件名和文件内容
        MultipartHttpServletRequest fileRequest = null;
        //接收商品缩略图
        MultipartFile productImg = null;
        //接收商品详情图片
        Set<ImageHolder> productDetailImgs = new HashSet<ImageHolder>();

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
            	Shop currentShop = getCurrentShop(request);
            	product.setShop(currentShop);
            	//调用Service层addProduct
            	ProductExecution pe = productService.addProduct(product, thumbnail, productDetailImgs);
            	if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
            		modelMap.put("success", true);
            		modelMap.put("sucMsg", "添加成功");
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
	
	/**
	 * 获取指定ID的商品和商品类别以初始化编辑商品页面
	 * 参数名称与前端请求参数key保持一致，springmvc自动匹配
	 * @param productId
	 * @return
	 */
	@RequestMapping(value="/getproductinfobyid", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getProductInfoById(Integer productId){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Product product = null;
		List<ProductCategory> productCategories = null;
		try {
			product = productService.getProductById(productId);
			if(product != null && product.getShop() != null && product.getShop().getShopId() > 0) {
				productCategories = productCategorySerivce.getProductCategories(product.getShop().getShopId());
				modelMap.put("success", true);
				modelMap.put("product", product);
				modelMap.put("productCategories", productCategories);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "商品或商铺不存在");
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "系统异常");
		}
		return modelMap;
	}
	
	/**
	 * 编辑商品，逻辑参见添加商品
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/edit", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editProduct(HttpServletRequest request, CommonsMultipartResolver fileResolver){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//Step1:校验验证码
		if (!VerifyCodeUtil.verifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			return modelMap;
		}
		
        Product product = null;
        //商品图片缩略图（输入流和名称的封装类）
        ImageHolder thumbnail = null;
        //将HttpServletRequest转型为MultipartHttpServletRequest，可以很方便地得到文件名和文件内容
        MultipartHttpServletRequest fileRequest = null;
        //接收商品缩略图
        MultipartFile productImg = null;
        //接收商品详情图片
        Set<ImageHolder> productDetailImgs = new HashSet<ImageHolder>();

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
            }
            
            //Step4:调用Service层
            //从session中获取shop信息，不依赖前端的传递更加安全
            Shop currentShop = getCurrentShop(request);
            product.setShop(currentShop);
            //调用Service层addProduct
            ProductExecution pe = productService.editProduct(product, thumbnail, productDetailImgs);
            if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
            	modelMap.put("success", true);
            	modelMap.put("sucMsg", "更新成功");
            } else {
            	modelMap.put("success", false);
            	modelMap.put("errMsg", pe.getStateInfo());
            }
        }catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        } 
        return modelMap;
    }
	
	/**
	 * 
	 * @return
	 */
	@RequestMapping(value="/changestatus", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> changeStatus(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
        //获取前端传递过来的product（只有ID和enableStatus），约定好使用productStr
        try {
            String productStr = HttpSvlReqUtil.getString(request, "productStr");
            Product product = mapper.readValue(productStr, Product.class);
            Shop currentShop = getCurrentShop(request);
            product.setShop(currentShop);

            ProductExecution pe = productService.changeProductStatus(product);
            if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                modelMap.put("success", true);
                modelMap.put("sucMsg", pe.getStateInfo());
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", pe.getStateInfo());
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
        }
        return modelMap;
	}
	
	private Shop getCurrentShop(HttpServletRequest request) {
		return (Shop)request.getSession().getAttribute("currentShop");
	}
	
}
