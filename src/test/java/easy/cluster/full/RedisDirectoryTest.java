package easy.cluster.full;

import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

import easy.cluster.Node;
import easy.cluster.directory.RedisMessage;
import easy.cluster.directory.RedisSubscriberThread;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisDirectoryTest {

	static JedisPool pool;

	public RedisDirectoryTest() {
		pool = new JedisPool(new JedisPoolConfig(), "localhost");

		RedisSubscriberThread thread = new RedisSubscriberThread(pool);
		thread.start();

	}

	@Test
	public void redisSubscriberTest() throws Exception {

		RedisMessage message = new RedisMessage();
		message.setServiceName("myservice");
		message.setNodes(new ArrayList<Node>());

		Thread.sleep(12000);
		try (Jedis jedis = pool.getResource()) {
			jedis.publish("redisRegistry", JSON.toJSONString(message));
		}
		Thread.sleep(5000);
	}

	@AfterClass
	public static void clear() {

		pool.close();
	}
}
