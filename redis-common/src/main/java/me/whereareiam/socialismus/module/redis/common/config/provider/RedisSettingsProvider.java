package me.whereareiam.socialismus.module.redis.common.config.provider;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.name.Named;
import me.whereareiam.configura.Config;
import me.whereareiam.socialismus.Reloadable;
import me.whereareiam.socialismus.module.redis.api.model.RedisSettings;
import me.whereareiam.socialismus.module.redis.common.config.template.RedisSettingsTemplate;
import me.whereareiam.socialismus.registry.base.Registry;

import java.nio.file.Path;

@Singleton
public class RedisSettingsProvider implements Provider<RedisSettings>, Reloadable {
	private final Path workingPath;
	private RedisSettings settings;

	@Inject
	public RedisSettingsProvider(
			@Named("workingPath") Path workingPath,
			Registry<Reloadable> reloadableRegistry
	) {
		this.workingPath = workingPath;

		Config.registerTemplate(RedisSettingsTemplate.class);
		reloadableRegistry.register(this);
	}

	@Override
	public RedisSettings get() {
		if (settings != null) return settings;
		settings = Config.update(workingPath.resolve("settings"), RedisSettings.class);
		return settings;
	}

	@Override
	public void reload() {
		settings = Config.update(workingPath.resolve("settings"), RedisSettings.class);
	}
}
