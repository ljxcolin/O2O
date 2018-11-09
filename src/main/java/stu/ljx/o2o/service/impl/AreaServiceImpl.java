package stu.ljx.o2o.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import stu.ljx.o2o.cache.JedisKit;
import stu.ljx.o2o.dao.AreaMapper;
import stu.ljx.o2o.entity.Area;
import stu.ljx.o2o.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaMapper areaMapper;
	
	@Autowired
	private JedisKit.Keys jedisKeys;
	@Autowired
	private JedisKit.Strings jedisStrings;
	
	/*获取所有区域*/
	/**
	 * 若redis中存在"arealist"的key，则redis中取出并返回
	 * 否则查询数据库，将查询结果放入redis缓存中并返回
	 * @throws IOException
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	@Override
	public List<Area> getAreaList() throws Exception {
		List<Area> areaList = null;
		ObjectMapper mapper = new ObjectMapper();
		//判断redis缓存中是否存在AREALISTKEY
		if(jedisKeys.exists(AREALISTKEY)) { //存在
			String jsonStr = jedisStrings.get(AREALISTKEY);
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, Area.class);
			areaList = mapper.readValue(jsonStr, javaType);
		}else { //不存在
			areaList = areaMapper.queryArea();
			String jsonStr = mapper.writeValueAsString(areaList);
			jedisStrings.set(AREALISTKEY, jsonStr);
		}
		return areaList;
	}
	
}
