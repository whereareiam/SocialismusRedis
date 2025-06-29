package me.whereareiam.socialismus.module.redis.api.input;

import me.whereareiam.socialismus.api.output.resource.CacheService;
import me.whereareiam.socialismus.api.output.resource.sync.SyncService;

/**
 * Exposed in common-api so both core and modules can inject it
 */
public interface RedisProvider {
	/**
	 * Start/connect (e.g. open Jedis pools)
	 */
	void start();

	/**
	 * Gracefully shut down
	 */
	void stop();

	/**
	 * For consumers
	 */
	SyncService sync();

	/**
	 * For consumers
	 */
	CacheService cache();
}
