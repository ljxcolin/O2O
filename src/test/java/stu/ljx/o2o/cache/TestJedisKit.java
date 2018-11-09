package stu.ljx.o2o.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;
import stu.ljx.o2o.cache.JedisKit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-redis.xml"})
public class TestJedisKit {
	
	@Autowired
	private JedisKit jedisKit;
	@Autowired
	private JedisKit.Keys keys;
	@Autowired
	private JedisKit.Strings strings;
	
	@Test
	public void testJedisKit() {
		Jedis jedis = jedisKit.getJedis();
		System.out.println(jedis);
		System.out.println(keys);
		System.out.println(strings.get("name"));
	}
	
}
