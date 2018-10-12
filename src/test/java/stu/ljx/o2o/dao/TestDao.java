package stu.ljx.o2o.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.Area;

public class TestDao extends BaseTest {
	
	@Autowired
	private AreaMapper areaMapper;
	
	@Test
	public void testQueryArea() {
		List<Area> areaList = areaMapper.queryArea();
		assertEquals(2, areaList.size());
		System.out.println(areaList.get(0));
		assertEquals("上海", areaList.get(0).getAreaName());
	}
	
}
