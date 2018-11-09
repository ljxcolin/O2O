package stu.ljx.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import stu.ljx.o2o.entity.HeadLine;

public interface HeadLineMapper {

	/**
	 * 获取状态可用的头条，用于首页轮播图
	 * @param headLineCnd
	 * @return
	 */
	List<HeadLine> queryHeadLine(@Param("headLineCnd")HeadLine headLineCnd);
}
