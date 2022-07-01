package me.wilko.fishing.settings;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.wilko.fishing.model.Fish;
import org.bukkit.Material;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.List;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FishLoader extends YamlConfig {

	@Getter
	private static final FishLoader instance = new FishLoader();

	@Override
	protected void onLoad() {

		if (isEmpty())
			return;
		
		Set<String> fishNames = getMap("fish").keySet();

		for (String fishName : fishNames) {
			setPathPrefix("fish." + fishName);

			Fish.Rarity rarity = Fish.Rarity.valueOf(getString("rarity", "common").toUpperCase());

			setPathPrefix("fish." + fishName + ".item");

			CompMaterial material = CompMaterial.fromMaterial(
					Material.valueOf(getString("material", "cod").toUpperCase()));

			String name = getString("name", material.toString());

			List<String> lore = getStringList("lore");

			Fish.addFish(new Fish(rarity, material, name, lore));
		}
	}

	/**
	 * Loads all the fish from fish.yml
	 */
	public void loadFish() {
		loadConfiguration(NO_DEFAULT, "fish.yml");
	}
}
