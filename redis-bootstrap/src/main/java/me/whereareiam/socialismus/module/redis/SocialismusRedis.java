package me.whereareiam.socialismus.module.redis;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import lombok.RequiredArgsConstructor;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.output.config.ConfigurationLoader;
import me.whereareiam.socialismus.api.output.config.ConfigurationManager;
import me.whereareiam.socialismus.api.output.module.SocialisticModule;
import me.whereareiam.socialismus.api.output.resource.CacheService;
import me.whereareiam.socialismus.api.output.resource.ResourceProvider;
import me.whereareiam.socialismus.api.output.resource.sync.SyncService;
import me.whereareiam.socialismus.api.type.ResourceType;
import me.whereareiam.socialismus.module.redis.common.CommonConfiguration;
import me.whereareiam.socialismus.module.redis.configuration.ConfigBinder;

import java.util.Map;

@RequiredArgsConstructor(onConstructor_ = {@Inject})
public class SocialismusRedis extends SocialisticModule implements ResourceProvider {
	private final Injector parentInjector;
	private final Registry<Reloadable> reloadableRegistry;
	private Injector injector;

	@Override
	public void onLoad() {
		injector =
				Guice.createInjector(
						new SocialismusRedisInjectorConfiguration(
								reloadableRegistry,
								parentInjector.getInstance(ConfigurationManager.class),
								parentInjector.getInstance(ConfigurationLoader.class)
						),
						new CommonConfiguration(),
						new ConfigBinder(workingPath));
	}

	@Override
	public void onEnable() {
	}

	@Override
	public void onDisable() {
	}

	@Override
	public void onUnload() {
	}

	@Override
	public Map<ResourceType, ?> provideResources() {
		return Map.of(
				ResourceType.SYNC, injector.getInstance(SyncService.class),
				ResourceType.CACHE, injector.getInstance(CacheService.class)
		);
	}
}
