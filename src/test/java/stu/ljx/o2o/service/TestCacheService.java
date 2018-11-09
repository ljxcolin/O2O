package stu.ljx.o2o.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import stu.ljx.o2o.BaseTest;

public class TestCacheService extends BaseTest{

	@Autowired
	private CacheService cacheService;
	
	@Test
	public void testRemoveFromCache() {
		long count = cacheService.removeFromCache(ShopCategoryService.SCLISTKEY);
		assertEquals(3L, count);
	}
	
}
