package me.whereareiam.socialismus.module.redis.common.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.resource.CacheService;
import me.whereareiam.socialismus.module.redis.api.model.RedisSettings;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.Optional;

@Singleton
public class RedisCacheService implements CacheService {
	private final Jedis client;

	@Inject
	public RedisCacheService(RedisSettings settings) {
		var redis = settings.getRedis();
		this.client = new Jedis(redis.getHost(), redis.getPort(), (int) redis.getTimeout(), redis.isSsl());
		if (redis.getPassword() != null && !redis.getPassword().isBlank())
			client.auth(redis.getPassword());
	}

	@Override
	public <T> Optional<T> get(String key, Class<T> type) {
		byte[] data = client.get(key.getBytes());
		if (data == null) return Optional.empty();
		try (var in = new ObjectInputStream(new ByteArrayInputStream(data))) {
			Object o = in.readObject();
			return Optional.of(type.cast(o));
		} catch (IOException | ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void set(String key, Object value, long ttlSeconds) {
		try (var bos = new ByteArrayOutputStream();
		     var out = new ObjectOutputStream(bos)) {
			out.writeObject(value);
			client.setex(key.getBytes(), (int) ttlSeconds, bos.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
