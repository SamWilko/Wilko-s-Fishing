package me.wilko.fishing.model;

import lombok.Getter;
import lombok.Setter;
import me.wilko.fishing.settings.Settings;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.settings.YamlConfig;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Fish extends YamlConfig {

	private static final List<Fish> fishList = new ArrayList<>();

	private Rarity rarity;
	private CompMaterial material;
	private String name;
	private List<String> lore;

	public Fish(Rarity rarity, CompMaterial material, String name, List<String> lore) {
		this.rarity = rarity;
		this.material = material;
		this.name = name;
		this.lore = lore;
	}

	/**
	 * @return The catchable item of this fish
	 */
	public ItemStack getItem() {

		return ItemCreator.of(material, name, getItemLore())
				.glow(rarity.glow)
				.make();
	}

	/**
	 * Lore that is displayed on the item.
	 * Includes the rarity prefix and watermark
	 *
	 * @return Lore as a string list
	 */
	private List<String> getItemLore() {
		List<String> itemLore = new ArrayList<>();

		if (Settings.Rarities.SHOW_PREFIX) {
			itemLore.add(rarity.getPrefix());
			itemLore.add("");
		}

		itemLore.addAll(lore);

		itemLore.add("");
		itemLore.add(Settings.FISH_WATERMARK);

		return itemLore;
	}

	@Override
	public String toString() {
		return name;
	}

	public enum Rarity {

		COMMON,
		UNCOMMON,
		RARE,
		EPIC,
		LEGENDARY;

		@Getter
		@Setter
		private String prefix;

		@Getter
		@Setter
		private boolean glow;

		@Getter
		@Setter
		private int chance;
	}

	/**
	 * @return All loaded fish
	 */
	public static List<Fish> getAll() {
		return fishList;
	}

	public static Fish getFishAt(int index) {
		return fishList.get(index);
	}

	/**
	 * Adds a fish to the static list of loaded fish
	 *
	 * @param fish fish to add
	 */
	public static void addFish(Fish fish) {
		Fish.fishList.add(fish);
	}
}
