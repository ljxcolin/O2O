package stu.ljx.o2o.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.entity.HeadLine;

public class TestHeadLineMapper extends BaseTest {

	@Autowired
	private HeadLineMapper headLineMapper;
	
	@Test
	public void testQueryHeadLine() {
		//头条状态：0-->不可用，1-->可用
		HeadLine headLineCnd = new HeadLine();
		//查询所有不可用的头条信息
//		headLineCnd.setEnableStatus(0);
//		List<HeadLine> headLineList = headLineMapper.queryHeadLine(headLineCnd);
//		assertEquals(1, headLineList.size());
//		for (HeadLine headLine : headLineList) {
//			System.out.println(headLine);
//		}
		
		//查询所有可用的头条信息
		headLineCnd.setEnableStatus(1);
		List<HeadLine> headLineList = headLineMapper.queryHeadLine(headLineCnd);
		assertEquals(2, headLineList.size());
		for (HeadLine headLine : headLineList) {
			System.out.println(headLine);
		}
		
		//查询所有的头条信息
//		List<HeadLine> headLineList = headLineMapper.queryHeadLine(headLineCnd);
//		assertEquals(3, headLineList.size());
//		for (HeadLine headLine : headLineList) {
//			System.out.println(headLine);
//		}	
	}
	
	@Test
	public void testQueryNullHeadLine() {
		List<HeadLine> headLines = headLineMapper.queryHeadLine(null);
		assertNotNull(headLines);
		assertEquals(3, headLines.size());
	}
	
}
