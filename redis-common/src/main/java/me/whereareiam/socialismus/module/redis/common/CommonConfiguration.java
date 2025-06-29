package me.whereareiam.socialismus.module.redis.common;

import com.google.inject.AbstractModule;
import me.whereareiam.socialismus.api.output.resource.CacheService;
import me.whereareiam.socialismus.api.output.resource.sync.SyncService;
import me.whereareiam.socialismus.module.redis.common.service.RedisCacheService;
import me.whereareiam.socialismus.module.redis.common.service.RedisSyncService;

public class CommonConfiguration extends AbstractModule {
	@Override
	protected void configure() {
		bind(SyncService.class).to(RedisSyncService.class);
		bind(CacheService.class).to(RedisCacheService.class);
	}
}
