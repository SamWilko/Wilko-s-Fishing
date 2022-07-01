package me.wilko.fishing.settings;

import me.wilko.fishing.model.Fish;
import me.wilko.fishing.util.MathUtil;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.settings.SimpleSettings;

public class Settings extends SimpleSettings {

	public static Integer VANILLA_FISH_THRESHOLD;
	public static String FISH_WATERMARK = "&8Wilko's Fishing";

	private static void init() {
		setPathPrefix(null);

		if (isSet("vanilla-fish-threshold")) {
			String val = getString("vanilla-fish-threshold");

			if (MathUtil.isInteger(val)) {
				int intVal = Integer.parseInt(val);

				if (intVal < 0 || intVal > 100) {
					Common.logFramed("The value of 'vanilla-fish-threshold' in settings.yml must be between" +
							"0 and 100", "Setting to default value of 50.");

					VANILLA_FISH_THRESHOLD = 50;
				} else
					VANILLA_FISH_THRESHOLD = intVal;
			} else {
				Common.logFramed("The value of 'vanilla-fish-threshold' in settings.yml must be an integer!",
						"Setting to default value of 50.");

				VANILLA_FISH_THRESHOLD = 50;
			}
		}
	}

	public final static class Rarities {

		public static Boolean SHOW_PREFIX;

		private static void init() {
			setPathPrefix("rarities");

			SHOW_PREFIX = getBoolean("show-prefix");

			for (Fish.Rarity rarity : Fish.Rarity.values()) {
				setPathPrefix("rarities." + rarity.name().toLowerCase());

				rarity.setGlow(getBoolean("glow"));
				rarity.setPrefix(getString("item-prefix"));
				rarity.setChance(getInteger("chance"));
			}
		}
	}
}
