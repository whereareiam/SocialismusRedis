package me.whereareiam.socialismus.module.redis.api.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RedisSettings {
	private Redis redis;

	@Getter
	@Setter
	@ToString
	public static class Redis {
		private String host;
		private int port;

		private String password;

		private boolean ssl;
		private long timeout;
	}
}
