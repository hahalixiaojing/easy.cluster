package easy.cluster.directory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisSubscriberThread {
	private final JedisPool pool;
	private Thread thread;

	public RedisSubscriberThread(JedisPool pool) {
		this.pool = pool;
	}

	public void start() {

		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					try (Jedis jedis = pool.getResource()) {
						jedis.subscribe(new RedisSubscriber(), "redisRegistry");
					} catch (Exception e) {
					}
				}
			}
		});

		thread.setDaemon(true);
		thread.start();
	}

}
