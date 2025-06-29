package me.whereareiam.socialismus.module.redis.common;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.output.resource.CacheService;
import me.whereareiam.socialismus.api.output.resource.sync.SyncService;
import me.whereareiam.socialismus.module.redis.common.provider.JedisPoolProvider;
import me.whereareiam.socialismus.module.redis.common.service.RedisCacheService;
import me.whereareiam.socialismus.module.redis.common.service.RedisSyncService;
import redis.clients.jedis.JedisPool;

public class CommonConfiguration extends AbstractModule {
	@Override
	protected void configure() {
		bind(JedisPool.class).toProvider(JedisPoolProvider.class);
		bind(SyncService.class).to(RedisSyncService.class);
		bind(CacheService.class).to(RedisCacheService.class);
	}
}
