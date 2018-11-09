package stu.ljx.o2o.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;
import stu.ljx.o2o.dto.HeadLineExecution;
import stu.ljx.o2o.entity.HeadLine;
import stu.ljx.o2o.enums.HeadLineStateEnum;

public class TestHeadLineService extends BaseTest {
	
	@Autowired
	private HeadLineService headLineService;
	
	@Test
	public void testGetHeadLineList() {
		//头条状态：0-->不可用，1-->可用
		HeadLine headLineCnd = new HeadLine();
		//查询所有不可用的头条信息
//		headLineCnd.setEnableStatus(0);
//		HeadLineExecution hle = headLineService.getHeadLineList(headLineCnd);
//		assertEquals(HeadLineStateEnum.SUCCESS.getState(), hle.getState());
//		assertEquals(1, hle.getCount());
//		for (HeadLine headLine : hle.getHeadLineList()) {
//			System.out.println(headLine);
//		}

		//查询所有可用的头条信息
		headLineCnd.setEnableStatus(1);
		HeadLineExecution hle = headLineService.getHeadLineList(headLineCnd);
		assertEquals(HeadLineStateEnum.SUCCESS.getState(), hle.getState());
		assertEquals(2, hle.getHeadLineList().size());
		for (HeadLine headLine : hle.getHeadLineList()) {
			System.out.println(headLine);
			
		}
	}

}
