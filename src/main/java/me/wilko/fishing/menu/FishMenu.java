package me.wilko.fishing.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompSound;

import java.util.Arrays;
import java.util.List;

public class FishMenu extends Menu {

	private final int rodPos;
	private final int fishPos;
	private final List<Integer> darkBlueSlots;
	private final List<Integer> lightBlueSlots;
	private final int infoPos;
	private final int closePos;

	private final Button rodButton;
	private final Button fishButton;
	private final Button infoButton;
	private final Button closeButton;

	public FishMenu() {
		setTitle("&8Wilko's Fishing");
		setSize(9 * 6);

		rodPos = 20;
		fishPos = 24;

		infoPos = 9 * 5;
		closePos = 9 * 6 - 1;

		darkBlueSlots = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 18, 27, 13, 22, 31, 17, 26, 35, 36, 37, 38, 39, 40
				, 41, 42, 43, 44);
		lightBlueSlots = Arrays.asList(10, 11, 12, 14, 15, 16, 19, 21, 23, 25, 28, 29, 30, 32, 33, 34);

		rodButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {

			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.FISHING_ROD)
						.name("&bFeature Coming Soon!")
						.make();
			}
		};

		fishButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				new FishPaggedMenu().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.TROPICAL_FISH)
						.name("&9Catchable Fish")
						.lore("", "&7 View a list of all fish that you",
								"&7are able to catch!", "",
								"&3Click To Browse!", "")
						.make();
			}
		};

		infoButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				CompSound.VILLAGER_IDLE.play(player);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.NETHER_STAR)
						.name("&9Info")
						.lore("", "&7This is the main menu where you can",
								"&7access specialty rods and view all",
								"&7catchable fish!", "")
						.make();
			}
		};

		closeButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				player.closeInventory();
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.BARRIER)
						.name("&cClose Menu")
						.make();
			}
		};
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (slot == rodPos)
			return rodButton.getItem();
		if (slot == fishPos)
			return fishButton.getItem();
		if (slot == infoPos)
			return infoButton.getItem();
		if (slot == closePos)
			return closeButton.getItem();
		if (darkBlueSlots.contains(slot))
			return ItemCreator.of(CompMaterial.BLUE_STAINED_GLASS_PANE).name("").make();
		if (lightBlueSlots.contains(slot))
			return ItemCreator.of(CompMaterial.LIGHT_BLUE_STAINED_GLASS_PANE).name("").make();

		return null;
	}
}