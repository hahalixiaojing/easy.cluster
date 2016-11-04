package easy.cluster.directory;

import com.alibaba.fastjson.JSON;
import easy.cluster.IRefreshDirectoryNodes;
import redis.clients.jedis.JedisPubSub;

public class RedisSubscriber extends JedisPubSub {
	@Override
	public void onMessage(String channel, String message) {

		if (null == message || message.isEmpty()) {
			return;
		}

		RedisMessage redisMessage = JSON.parseObject(message,
				RedisMessage.class);

		IRefreshDirectoryNodes directory = (IRefreshDirectoryNodes) DirectoryFactory
				.getDirectory(redisMessage.getServiceName());

		if (directory != null) {
			directory.refresh(redisMessage.getNodes());
		}
	}
}
