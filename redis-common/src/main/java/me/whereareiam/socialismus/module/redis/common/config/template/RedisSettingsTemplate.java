package me.whereareiam.socialismus.module.redis.common.config.template;

import com.google.inject.Singleton;
import me.whereareiam.configura.TemplateProvider;
import me.whereareiam.socialismus.module.redis.api.model.RedisSettings;

@Singleton
public class RedisSettingsTemplate implements TemplateProvider<RedisSettings> {
	@Override
	public RedisSettings supply(RedisSettings config) {
		// Default values
		RedisSettings.Redis redis = new RedisSettings.Redis();
		redis.setHost("localhost");
		redis.setPort(6379);
		redis.setPassword("");
		redis.setSsl(false);
		redis.setTimeout(2000);

		config.setRedis(redis);

		return config;
	}
}
