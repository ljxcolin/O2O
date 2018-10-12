package stu.ljx.o2o.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import stu.ljx.o2o.entity.Area;
import stu.ljx.o2o.service.AreaService;

@Controller
@RequestMapping("/superadmin")
public class AreaController {

	@Autowired
	private AreaService areaService;
	
	private Logger logger = LoggerFactory.getLogger(AreaController.class); 
	
	@RequestMapping(value="/listArea", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getAreas(){
		/*0日志测试0*/
		logger.info("############ GetAreas Begin ############");
		Long beginTime = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		List<Area> areaList = null;
		try {
			areaList = areaService.getAreaList();
			map.put("count", areaList.size());
			map.put("area", areaList);
			for (Area area : areaList) {
				System.out.println("area:" + area.getAreaName());
			}
		}catch (Exception e) {
			e.printStackTrace();
			map.put("success", false);
			map.put("errMsg", e.getMessage());
			logger.error("Exception Happens , desc [{}] ", e.getMessage());
		}
		Long endTime = System.currentTimeMillis();
		logger.debug("cost [{}ms]", endTime - beginTime);
		logger.info("############ GetAreas End ############");
		return map;
	}
	
}
