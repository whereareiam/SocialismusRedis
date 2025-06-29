package me.whereareiam.socialismus.module.redis;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.output.config.ConfigurationLoader;
import me.whereareiam.socialismus.api.output.config.ConfigurationManager;

public class SocialismusRedisInjectorConfiguration extends AbstractModule {
	private final Registry<Reloadable> reloadableRegistry;

	private final ConfigurationManager configurationManager;
	private final ConfigurationLoader configurationLoader;

	public SocialismusRedisInjectorConfiguration(
			Registry<Reloadable> reloadableRegistry,
			ConfigurationManager configurationManager,
			ConfigurationLoader configurationLoader
	) {
		this.reloadableRegistry = reloadableRegistry;

		this.configurationManager = configurationManager;
		this.configurationLoader = configurationLoader;
	}

	@Override
	protected void configure() {
		bind(new TypeLiteral<Registry<Reloadable>>() {}).toInstance(reloadableRegistry);

		bind(ConfigurationManager.class).toInstance(configurationManager);
		bind(ConfigurationLoader.class).toInstance(configurationLoader);
	}
}
