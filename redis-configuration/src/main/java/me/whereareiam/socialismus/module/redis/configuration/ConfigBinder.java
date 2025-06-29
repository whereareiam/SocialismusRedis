package me.whereareiam.socialismus.module.redis.configuration;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import me.whereareiam.socialismus.module.redis.api.model.RedisSettings;
import me.whereareiam.socialismus.module.redis.configuration.provider.RedisSettingsProvider;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigBinder extends AbstractModule {
	private final Path workingPath;

	public ConfigBinder(Path workingDirectory) {
		this.workingPath = workingDirectory;
	}

	@Override
	protected void configure() {
		bind(Path.class).annotatedWith(Names.named("workingPath")).toInstance(workingPath);
		createDirectories();

		bind(RedisSettingsProvider.class).asEagerSingleton();
		bind(RedisSettings.class).toProvider(RedisSettingsProvider.class);
	}

	private void createDirectories() {
		try {
			Files.createDirectories(workingPath);
		} catch (IOException e) {
			throw new RuntimeException("Failed to create directories", e);
		}
	}
}
