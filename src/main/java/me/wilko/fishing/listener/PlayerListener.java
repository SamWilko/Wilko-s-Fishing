package me.wilko.fishing.listener;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import me.wilko.fishing.model.Fish;
import me.wilko.fishing.model.LootPool;
import me.wilko.fishing.settings.Settings;
import me.wilko.fishing.util.MathUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.mineacademy.fo.annotation.AutoRegister;

@AutoRegister
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class PlayerListener implements Listener {

	private static final PlayerListener instance = new PlayerListener();

	@EventHandler
	public void onFish(PlayerFishEvent event) {

		if (event.getState() != PlayerFishEvent.State.CAUGHT_FISH)
			return;

		if (MathUtil.chance(Settings.VANILLA_FISH_THRESHOLD))
			return;

		if (Fish.getAll().isEmpty())
			return;

		Entity entity = event.getCaught();
		if (!(entity instanceof Item))
			return;

		Fish fish = LootPool.nextFish();

		Item item = (Item) entity;
		item.setItemStack(fish.getItem());
	}
}
