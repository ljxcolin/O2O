package stu.ljx.o2o.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.Area;

public class TestAreaService extends BaseTest{
	
	@Autowired
	private AreaService areaService;
	@Autowired
	private CacheService cacheService;
	
	@Test
	public void testGetAreaList() throws Exception {
		List<Area> areaList = null;
		//首次查询，从database加载
		areaList = areaService.getAreaList();
		assertEquals("上海", areaList.get(0).getAreaName());
		//再次查询，从redis加载
		areaList = areaService.getAreaList();
		assertEquals("上海", areaList.get(0).getAreaName());
		
		//清除缓存
		cacheService.removeFromCache(AreaService.AREALISTKEY);
		
		//再次查询，从db加载
		areaList = areaService.getAreaList();
		assertEquals("上海", areaList.get(0).getAreaName());
		//再次查询，从redis加载
		areaList = areaService.getAreaList();
		assertEquals("上海", areaList.get(0).getAreaName());
	}
	
}
