package me.whereareiam.socialismus.module.redis.common.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.resource.sync.SyncService;
import me.whereareiam.socialismus.api.output.resource.sync.SyncSubscriber;
import me.whereareiam.socialismus.module.redis.api.model.RedisSettings;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

@Singleton
public class RedisSyncService implements SyncService {
	private final Jedis publisher;
	private final Jedis subscriberClient;

	@Inject
	public RedisSyncService(RedisSettings settings) {
		var redis = settings.getRedis();
		boolean useSsl = redis.isSsl();
		int timeout = (int) redis.getTimeout();

		this.publisher = new Jedis(redis.getHost(), redis.getPort(), timeout, useSsl);
		this.subscriberClient = new Jedis(redis.getHost(), redis.getPort(), timeout, useSsl);

		if (redis.getPassword() != null && !redis.getPassword().isBlank()) {
			publisher.auth(redis.getPassword());
			subscriberClient.auth(redis.getPassword());
		}
	}

	@Override
	public void publish(String channel, byte[] payload) {
		publisher.publish(channel.getBytes(), payload);
	}

	@Override
	public void subscribe(String channel, SyncSubscriber sub) {
		new Thread(() -> subscriberClient.subscribe(new JedisPubSub() {
			@Override
			public void onMessage(String ch, String msg) {
				sub.onMessage(ch, msg.getBytes());
			}
		}, channel), channel).start();
	}
}
