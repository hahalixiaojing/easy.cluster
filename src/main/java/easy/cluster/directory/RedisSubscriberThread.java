package easy.cluster.directory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisSubscriberThread {
	private static final String CHANNEL = "redisRegistry";

	private final JedisPool pool;
	private Thread thread;
	private volatile boolean isRunning = false;

	public RedisSubscriberThread(JedisPool pool) {
		this.pool = pool;
	}

	public void start() {
		isRunning = true;
		thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (isRunning) {
					try (Jedis jedis = pool.getResource()) {
						jedis.subscribe(new RedisSubscriber(), CHANNEL);
					} catch (Exception e) {
					}
				}
			}
		});

		thread.setDaemon(true);
		thread.start();
	}

	public void close() {
		this.isRunning = false;
	}
}
