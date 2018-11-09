package stu.ljx.o2o.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stu.ljx.o2o.cache.JedisKit;
import stu.ljx.o2o.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService {

	@Autowired
	private JedisKit.Keys jedisKeys;
	
	@Override
	public long removeFromCache(String keyPrefix) {
		Set<String> keys = jedisKeys.keys(keyPrefix + "*");
		String[] keyArray = new String[keys.size()];
		keys.toArray(keyArray);
		long count = jedisKeys.del(keyArray);
		return count;
	}

}
