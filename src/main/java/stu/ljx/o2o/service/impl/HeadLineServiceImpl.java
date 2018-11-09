package stu.ljx.o2o.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import stu.ljx.o2o.cache.JedisKit;
import stu.ljx.o2o.dao.HeadLineMapper;
import stu.ljx.o2o.dto.HeadLineExecution;
import stu.ljx.o2o.entity.HeadLine;
import stu.ljx.o2o.enums.HeadLineStateEnum;
import stu.ljx.o2o.exception.HeadLineException;
import stu.ljx.o2o.service.HeadLineService;

@Service
public class HeadLineServiceImpl implements HeadLineService {
	
	private static final Logger logger = LoggerFactory.getLogger(HeadLineServiceImpl.class);
	
	@Autowired
	private HeadLineMapper headLineMapper;
	@Autowired
	private JedisKit.Keys jedisKeys;
	@Autowired
	private JedisKit.Strings jedisStrings;
	
	@Override
	public HeadLineExecution getHeadLineList(HeadLine headLineCnd) {
		HeadLineExecution hle = null;
		List<HeadLine> headLineList = null;
		String key = HEADLINEKEY;
		ObjectMapper mapper = new ObjectMapper();
		//key三种格式：headline/headline_0/headline_1
		if(headLineCnd != null && headLineCnd.getEnableStatus() != null) {
			key = key + "_" + headLineCnd.getEnableStatus();
		}
		try {
			//判断redis缓存中是否存在key
			if(jedisKeys.exists(key)) { //存在
				String jsonStr = jedisStrings.get(key);
				JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, HeadLine.class);
				headLineList = mapper.readValue(jsonStr, javaType);
			}else { //不存在
				headLineList = headLineMapper.queryHeadLine(headLineCnd);
				String jsonStr = mapper.writeValueAsString(headLineList);
				jedisStrings.set(key, jsonStr);
			}
			hle = new HeadLineExecution(HeadLineStateEnum.SUCCESS, headLineList);
		} catch (Exception e) {
			logger.error("getHeadLineList error: {}", e.toString());
			throw new HeadLineException(HeadLineStateEnum.FAILED.getStateInfo());
		}
		return hle;
	}

}
