package me.whereareiam.socialismus.module.redis.common.service;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.RequiredArgsConstructor;
import me.whereareiam.socialismus.api.output.resource.CacheService;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import java.io.*;
import java.util.Optional;

@Singleton
@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class RedisCacheService implements CacheService {
	private final JedisPool jedisPool;

	@Override
	public <T> Optional<T> get(String key, Class<T> type) {
		try (Jedis jedis = jedisPool.getResource()) {
			byte[] data = jedis.get(key.getBytes());
			if (data == null) return Optional.empty();
			try (var in = new ObjectInputStream(new ByteArrayInputStream(data))) {
				return Optional.of(type.cast(in.readObject()));
			} catch (IOException | ClassNotFoundException e) {
				throw new JedisException(e);
			}
		}
	}

	@Override
	public void set(String key, Object value, long ttlSeconds) {
		try (Jedis jedis = jedisPool.getResource()) {
			try (var out = new ByteArrayOutputStream();
			     var objOut = new ObjectOutputStream(out)) {
				objOut.writeObject(value);
				byte[] data = out.toByteArray();
				if (ttlSeconds > 0) {
					jedis.setex(key.getBytes(), ttlSeconds, data);
					return;
				}

				jedis.set(key.getBytes(), data);
			} catch (IOException e) {
				throw new JedisException(e);
			}
		}
	}
}
