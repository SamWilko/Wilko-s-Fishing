package me.wilko.fishing.command;

import me.wilko.fishing.menu.FishMenu;
import org.mineacademy.fo.annotation.AutoRegister;
import org.mineacademy.fo.command.SimpleCommand;

@AutoRegister
public final class FishCommand extends SimpleCommand {

	public FishCommand() {
		super("wilkofish|wf");

		setDescription("Opens the main menu for Wilko fish!");

		setPermission("wilkofish.mainmenu");
		setPermissionMessage("You do not have permission to use this command!");
	}

	@Override
	protected void onCommand() {

		// Displays the main menu
		new FishMenu().displayTo(getPlayer());
	}
}
