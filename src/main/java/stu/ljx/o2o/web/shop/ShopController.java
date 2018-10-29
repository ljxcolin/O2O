package stu.ljx.o2o.web.shop;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.multipart.commons.CommonsMultipartFile;
//import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import stu.ljx.o2o.dto.ShopExecution;
import stu.ljx.o2o.entity.Area;
import stu.ljx.o2o.entity.Shop;
import stu.ljx.o2o.entity.ShopCategory;
import stu.ljx.o2o.entity.User;
import stu.ljx.o2o.enums.ShopStateEnum;
import stu.ljx.o2o.service.AreaService;
import stu.ljx.o2o.service.ShopCategoryService;
import stu.ljx.o2o.service.ShopService;
import stu.ljx.o2o.util.HttpSvlReqUtil;
import stu.ljx.o2o.util.PageUtil;
import stu.ljx.o2o.util.VerifyCodeUtil;

@Controller
@RequestMapping("/shop")
public class ShopController {

	@Autowired
	private ShopService shopService;
	@Autowired
	private ShopCategoryService shopCategoryService;
	@Autowired
	private AreaService areaService;
	
	/* ############################## 商铺注册模块 ##################################### */
	/**
	 * 获取商铺类别和所属区域的数据返回给前端以初始化商铺相关的操作页面
	 * @return Map<String, Object> 封装了shopCategoryList和areaList
	 */
	@RequestMapping(value="/getshopinitinfo", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getshopInitInfo(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<ShopCategory> shopCategoryList = null;
		List<Area> areaList = null;
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("success", true);
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	/**
	 * 注册商铺
	 * 	1.接收前端传来的JSON字符串的商铺信息(shopStr)，转换相应参数
	 * 	2.注册商铺
	 * 	3.返回操作结果给前端
	 * @param request
	 * @param shopImg
	 * @return Map<String, Object>
	 */
	@RequestMapping("/register")
	@ResponseBody
	public Map<String, Object> registerShop(HttpServletRequest request, MultipartFile shopImg){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//首先校验验证码
		if(!VerifyCodeUtil.verifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			return modelMap;
		}
		//判断用户是否已登录，只有登录了才能注册商铺。用户在Session中的key约定为"user"
		User user = (User) request.getSession().getAttribute("user");
		if(user == null) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请先登录再操作");
			return modelMap;
		}
		
		//Step1 ：获取参数并转换相应参数
		//1.1 商铺基本信息
		//shopStr：商铺信息，与前端约定好的JSON格式字符串
		String shopStr = HttpSvlReqUtil.getString(request, "shopStr");
		//使用jackson-databind将json字符串转为pojo
		ObjectMapper objMapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = objMapper.readValue(shopStr, Shop.class);
			//1.2商铺头像信息（基于Apache-Commons-FileUpload的文件上传）
			/*CommonsMultipartFile shopImg = null;
			CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getServletContext());
			if(commonsMultipartResolver.isMultipart(request)) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
				shopImg = (CommonsMultipartFile)multipartRequest.getFile("shopImg");
			}*/
			//Step2：注册商铺
			if(shop != null && shopImg != null) {
				//用户登录后才能注册商铺
				shop.setOwner(user);
				//注册商铺
				//ShopExecution se = shopService.addShop(shop, ImageUtil.multipartFileToFile(shopImg)); //使用InputStream代替File作为入参
				ShopExecution se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
				if (se.getState() == ShopStateEnum.CHECKING.getState()) {
	                modelMap.put("success", true);
	                modelMap.put("sucMsg", "商铺注册成功");
	            }else {
	                modelMap.put("success", false);
	                modelMap.put("errMsg", se.getStateInfo());
	            }
			}else {
				 modelMap.put("success", false);
	             modelMap.put("errMsg", "商铺信息不完整");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//将错误信息返回给前端
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	/* ############################## 商铺编辑模块 ##################################### */
	/**
	 * 需求设计：针对商铺拥有者
	 * 		商铺ID不可更改
	 * 		商铺名称不可更改
	 * 		商铺类别不可更改
	 * 		其余信息可更改
	 */
	/**
	 * 获取编辑商铺所需的数据，包括该ID的商铺和所有区域，由于商铺类别不可更改，
	 * 且DAO层获取指定ID商铺时已关联查询出该商铺对应的类别了，因此无需查询商铺类别。
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getshopinfobyid", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopInfoById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//获得要修改的商铺的ID
		Integer shopId = HttpSvlReqUtil.getInteger(request, "shopId");
		Shop shop = null;
		List<Area> areaList = null;
		try {
			if(shopId > 0) {
				shop = shopService.getShopById(shopId);
				areaList = areaService.getAreaList();
				modelMap.put("success", true);
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
			}else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "商铺ID不合法");
			}
		}catch(Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	/**
	 * 修改商铺，可参考注册商铺，注意以下事项：
	 * 		1、获取原有的商铺图片
	 * 		2、判断是否需要更换商铺图片
	 * @param request
	 * @param shopImg
	 * @return
	 */
	@RequestMapping("/modify")
	@ResponseBody
	public Map<String, Object> modifyShop(HttpServletRequest request, MultipartFile shopImg){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//首先校验验证码
		if(!VerifyCodeUtil.verifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "验证码不正确");
			return modelMap;
		}
		//Step1 ：获取参数并转换相应参数
		String shopStr = HttpSvlReqUtil.getString(request, "shopStr");
		ObjectMapper objMapper = new ObjectMapper();
		Shop shop = null;
		ShopExecution se = null;
		try {
			shop = objMapper.readValue(shopStr, Shop.class);
			//Step2：修改商铺
			if(shop != null && shop.getShopId() != null) {
				//修改商铺不需要从Session中验证用户是否已登录
				//先获取旧的商铺图片
				String oldShopImg = HttpSvlReqUtil.getString(request, "oldShopImg");
				if(shopImg != null) {
					//需要更换图片
					se = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename(), oldShopImg);
				}else {
					//不需要更换图片
					se = shopService.modifyShop(shop, null, null, oldShopImg);
				}
				if (se.getState() == ShopStateEnum.SUCCESS.getState()) {
	                modelMap.put("success", true);
	                modelMap.put("sucMsg", "商铺修改成功");
	            }else {
	                modelMap.put("success", false);
	                modelMap.put("errMsg", se.getStateInfo());
	            }
			}else {
				 modelMap.put("success", false);
	             modelMap.put("errMsg", "商铺不存在");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//将错误信息返回给前端
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	/* ############################## 商铺列表模块 ##################################### */
	/**
	 * 获取当前用户（session中的user）拥有的商铺列表
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/list", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getShopList(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//从session中获取当前用户，待开发，先模拟
		//User owner = (User) request.getSession().getAttribute("user");
		User owner = new User();
		owner.setUserId(1);
//		if(owner == null) {
//			modelMap.put("success", false);
//			modelMap.put("errMsg", "请先登录再操作");
//			return modelMap;
//		}
		Shop shopCnd = new Shop();
		try {
			shopCnd.setOwner(owner);
			ShopExecution se = shopService.getShopList(shopCnd, 1, PageUtil.PAGESIZE);
			modelMap.put("success", true);
			modelMap.put("shopList", se.getShopList());
			modelMap.put("count", se.getCount());
			modelMap.put("user", owner);
		}catch(Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}
		return modelMap;
	}
	
	/* ############################## 商铺管理模块 ##################################### */
	/**
	 * 管理商铺
	 * 商家在商铺列表页面点击“进入”按钮，请求Url为"/o2o/shop/manage?shopId=xxx"
	 * 若shopId不合法，则尝试从session中获取当前商铺
	 * 若shopId合法，则从数据库获取该商铺并存入session中
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/manage", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> manageShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Integer shopId = HttpSvlReqUtil.getInteger(request, "shopId");
		Shop currentShop = null;
		if(shopId > 0) { //shopId合法
			currentShop = shopService.getShopById(shopId);
			if(currentShop != null) {
				request.getSession().setAttribute("currentShop", currentShop); //将当前商铺存入session
			}
		}else { //shopId不合法
			currentShop = (Shop) request.getSession().getAttribute("currentShop");
		}
		if(currentShop != null) {
			modelMap.put("redircet", false);
			modelMap.put("shopId", currentShop.getShopId());
		}else {
			modelMap.put("redirect", true);
			modelMap.put("url", "/o2o/shopadmin/list");
			modelMap.put("errMsg", "该商铺不存在");
		}
		return modelMap;
	}
	
}