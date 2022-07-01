package me.wilko.fishing.menu;

import me.wilko.fishing.model.Fish;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.mineacademy.fo.Common;
import org.mineacademy.fo.PlayerUtil;
import org.mineacademy.fo.menu.Menu;
import org.mineacademy.fo.menu.button.Button;
import org.mineacademy.fo.menu.model.ItemCreator;
import org.mineacademy.fo.remain.CompMaterial;
import org.mineacademy.fo.remain.CompSound;

import java.util.*;

public class FishPaggedMenu extends Menu {

	private final Map<Fish, Boolean> clickedItems = new HashMap<>();
	private final Map<Fish, BukkitTask> runningTasks = new HashMap<>();

	private final Map<Integer, Button> buttons = new HashMap<>();

	private final int nextSlot;
	private final int backSlot;
	private final int returnSlot;

	private final List<Integer> lightBlueSlots;
	private final List<Integer> darkBlueSlots;

	private final Button backButton;
	private final Button nextButton;
	private final Button returnButton;

	public FishPaggedMenu() {
		this(1, calculatePages());
	}

	public FishPaggedMenu(int currentPage, Map<Integer, List<Fish>> pages) {
		setTitle("&8Catchable Fish (Page " + currentPage + " of " + pages.size() + ")");
		setSize(9 * 5);

		nextSlot = 32;
		backSlot = 30;
		returnSlot = 31;

		lightBlueSlots = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 9, 17, 18, 26, 27, 35, 37, 38, 39, 40, 41, 42, 43);
		darkBlueSlots = Arrays.asList(0, 8, 36, 44);

		int currentIndex = 0;
		for (Fish fish : pages.get(currentPage)) {
			List<Integer> fishSlots = Arrays.asList(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25);

			buttons.put(fishSlots.get(currentIndex), new Button() {

				final Fish buttonFish = fish;

				@Override
				public void onClickedInMenu(Player player, Menu menu, ClickType click) {

					boolean hasPerm = PlayerUtil.hasPerm(player, "wilkofish.fishmenu.take");

					if (hasPerm) {
						CompSound.ITEM_PICKUP.play(player);
						player.getInventory().addItem(buttonFish.getItem());
					} else
						CompSound.VILLAGER_NO.play(player);

					// If they are like rapid clicking then we stop and take out the task that's already running and put in a
					// fresh one
					if (clickedItems.containsKey(buttonFish)) {
						clickedItems.remove(buttonFish);
						runningTasks.get(buttonFish).cancel();
					}

					clickedItems.put(buttonFish, hasPerm);
					restartMenu();

					runningTasks.put(buttonFish, Common.runLater(20, () -> {
						clickedItems.remove(buttonFish);
						restartMenu();
					}));
				}

				@Override
				public ItemStack getItem() {

					if (clickedItems.containsKey(buttonFish)) {

						return ItemCreator.of(fish.getItem()).clearLore().lore(
										PlayerUtil.hasPerm(getViewer(), "wilkofish.fishmenu.take") ?
												"&bAdded To Inventory!" : "&cNo Permission!")
								.make();
					}

					List<String> lore = fish.getItem().getItemMeta().getLore();

					if (PlayerUtil.hasPerm(getViewer(), "wilkofish.fishmenu.take")) {
						lore.add(lore.size() - 1, "&bClick To Add To Inventory!");
					}

					return ItemCreator.of(fish.getItem()).clearLore().lore(lore).make();
				}
			});

			currentIndex++;
		}

		backButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {

				// At the first page
				if (currentPage == 1)
					return;

				new FishPaggedMenu(currentPage - 1, pages).displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.ARROW)
						.name("&9Previous Page")
						.lore(currentPage == 1 ? "&7First Page" : "&3Page " + (currentPage - 1))
						.make();
			}
		};

		nextButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {

				// At the last page
				if (currentPage == pages.size()) {
					return;
				}

				new FishPaggedMenu(currentPage + 1, pages).displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.ARROW)
						.name("&9Next Page")
						.lore(currentPage == pages.size() ? "&7Last Page" : "&3Page " + (currentPage + 1))
						.make();
			}
		};

		returnButton = new Button() {
			@Override
			public void onClickedInMenu(Player player, Menu menu, ClickType click) {
				new FishMenu().displayTo(player);
			}

			@Override
			public ItemStack getItem() {
				return ItemCreator.of(CompMaterial.SPRUCE_DOOR)
						.name("&9Return")
						.lore("&7To Main Menu")
						.make();
			}
		};
	}

	@Override
	public ItemStack getItemAt(int slot) {
		if (darkBlueSlots.contains(slot))
			return ItemCreator.of(CompMaterial.BLUE_STAINED_GLASS_PANE).name("").make();
		if (lightBlueSlots.contains(slot))
			return ItemCreator.of(CompMaterial.LIGHT_BLUE_STAINED_GLASS_PANE).name("").make();
		if (slot == backSlot)
			return backButton.getItem();
		if (slot == returnSlot)
			return returnButton.getItem();
		if (slot == nextSlot)
			return nextButton.getItem();
		if (buttons.containsKey(slot))
			return buttons.get(slot).getItem();

		return null;
	}

	@Override
	protected void onMenuClose(Player player, Inventory inventory) {
		for (BukkitTask task : runningTasks.values()) {
			task.cancel();
		}
	}

	@Override
	protected List<Button> getButtonsToAutoRegister() {
		return new ArrayList<>(buttons.values());
	}

	private static Map<Integer, List<Fish>> calculatePages() {

		Map<Integer, List<Fish>> pages = new HashMap<>();

		if (Fish.getAll().isEmpty()) {
			pages.put(1, new ArrayList<>());
			return pages;
		}

		List<Fish> thisPage = new ArrayList<>();
		for (Fish fish : Fish.getAll()) {
			thisPage.add(fish);

			if (thisPage.size() >= 14) {
				pages.put(pages.size() + 1, new ArrayList<>(thisPage));

				thisPage.clear();
			}
		}
		if (!thisPage.isEmpty())
			pages.put(pages.size() + 1, thisPage);

		return pages;
	}
}