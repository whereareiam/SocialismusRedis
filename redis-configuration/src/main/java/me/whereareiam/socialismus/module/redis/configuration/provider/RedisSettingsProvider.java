package me.whereareiam.socialismus.module.redis.configuration.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.api.Reloadable;
import me.whereareiam.socialismus.api.input.registry.Registry;
import me.whereareiam.socialismus.api.output.config.ConfigurationLoader;
import me.whereareiam.socialismus.api.output.config.ConfigurationManager;
import me.whereareiam.socialismus.module.redis.api.model.RedisSettings;
import me.whereareiam.socialismus.module.redis.configuration.template.RedisSettingsTemplate;

import java.nio.file.Path;

@Singleton
public class RedisSettingsProvider implements Provider<RedisSettings>, Reloadable {
	private final Path workingPath;
	private final ConfigurationLoader configLoader;

	private RedisSettings settings;

	@Inject
	public RedisSettingsProvider(
			@Named("workingPath") Path workingPath,
			ConfigurationLoader configLoader,
			ConfigurationManager configManager,
			RedisSettingsTemplate template,
			Registry<Reloadable> reloadableRegistry
	) {
		this.workingPath = workingPath;
		this.configLoader = configLoader;

		configManager.addTemplate(RedisSettings.class, template);

		reloadableRegistry.register(this);
		get();
	}

	@Override
	public RedisSettings get() {
		if (settings != null) return settings;

		load();

		return settings;
	}

	@Override
	public void reload() {
		load();
	}

	private void load() {
		settings = configLoader.load(workingPath.resolve("settings"), RedisSettings.class);
	}
}
