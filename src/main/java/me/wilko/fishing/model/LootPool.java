package me.wilko.fishing.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LootPool {

	/**
	 * @return A random fish from the loot pool
	 */
	public static Fish nextFish() {

		List<Fish> lootPool = new ArrayList<>();

		// Places each fish in the loot pool x amount of times where x is it's percentage probability
		for (Fish fish : Fish.getAll()) {
			for (int i = 0; i < fish.getRarity().getChance(); i++) {
				lootPool.add(fish);
			}
		}

		/*
		Gets a random item from the list. Fish with higher percentage probabilities will have more 'entries' in
		the list and thus are more likely to be chosen.

		The real percentage probability of each fish is actually (% probability / sum of all % probabilities) rather
		than the expected (% probability / 100) because users may setup their rarity probabilities so that they don't
		all add up to 100.
		 */
		return lootPool.get(new Random().nextInt(lootPool.size()));
	}
}
