package stu.ljx.o2o.service;

public interface CacheService {
	
	/**
	 * 从缓存中删除前缀为keyPrefix的key（记录）
	 * @param keyPrefix
	 * @return 删除的key的个数
	 */
	long removeFromCache(String keyPrefix);

}
