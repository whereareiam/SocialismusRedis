package me.whereareiam.socialismus.module.redis.configuration.template;

import com.google.inject.Singleton;
import me.whereareiam.socialismus.api.output.DefaultConfig;
import me.whereareiam.socialismus.module.redis.api.model.RedisSettings;

@Singleton
public class RedisSettingsTemplate implements DefaultConfig<RedisSettings> {
	@Override
	public RedisSettings getDefault() {
		RedisSettings config = new RedisSettings();

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
