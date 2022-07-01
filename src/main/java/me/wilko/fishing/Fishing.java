package me.wilko.fishing;

import me.wilko.fishing.settings.FishLoader;
import org.mineacademy.fo.plugin.SimplePlugin;

public final class Fishing extends SimplePlugin {

	private static Fishing instance;

	@Override
	protected void onPluginStart() {
		instance = this;

		// Loads all the fish in fish.yml
		FishLoader.getInstance().loadFish();
	}

	/**
	 * @return The instance of this plugin
	 */
	public static Fishing getInstance() {
		return instance;
	}
}
