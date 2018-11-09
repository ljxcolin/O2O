package stu.ljx.o2o.service;

import java.util.List;

import stu.ljx.o2o.entity.Area;

public interface AreaService {
	
	public static final String AREALISTKEY = "arealist";

	List<Area> getAreaList() throws Exception;
	
}
