package me.whereareiam.socialismus.module.redis;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import me.whereareiam.socialismus.api.Logger;
import me.whereareiam.socialismus.api.output.module.SocialisticModule;
import me.whereareiam.socialismus.api.output.resource.ResourceProvider;
import me.whereareiam.socialismus.api.type.ResourceType;

import java.nio.file.Path;
import java.util.Map;

public class SocialismusRedis extends SocialisticModule implements ResourceProvider {
	private final Path dataPath;

	@Inject
	public SocialismusRedis(@Named("dataPath") Path dataPath) {
		this.dataPath = dataPath;
	}

	@Override
	public void onLoad() {
		Logger.info("SocialismusRedis loaded!");
		Logger.info("Data path: " + dataPath);
		Logger.info("SocialismusRedis path: " + workingPath);
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
		return Map.of();
	}
}
