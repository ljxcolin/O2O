package stu.ljx.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu.ljx.o2o.dao.AreaMapper;
import stu.ljx.o2o.entity.Area;
import stu.ljx.o2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaMapper areaMapper;
	
	@Override
	public List<Area> getAreaList() {
		return areaMapper.queryArea();
	}
	
}
