package me.egg82.hme.events;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerMoveEvent;

import ninja.egg82.patterns.ServiceLocator;
import ninja.egg82.plugin.commands.EventCommand;
import ninja.egg82.registry.interfaces.IRegistry;

import me.egg82.hme.enums.PluginServiceType;
import me.egg82.hme.util.interfaces.ILightHelper;

public class PlayerMoveEventCommand extends EventCommand {
	//vars
	private IRegistry glowRegistry = (IRegistry) ServiceLocator.getService(PluginServiceType.GLOW_REGISTRY);
	private ILightHelper lightHelper = (ILightHelper) ServiceLocator.getService(PluginServiceType.LIGHT_HELPER);
	
	//constructor
	public PlayerMoveEventCommand() {
		super();
	}
	
	//public
	
	//private
	protected void execute() {
		PlayerMoveEvent e = (PlayerMoveEvent) event;
		
		if (e.isCancelled()) {
			return;
		}
		
		if (!glowRegistry.contains(e.getPlayer().getUniqueId().toString())) {
			return;
		}
		
		Location from = e.getFrom().clone();
		from.setX(from.getBlockX());
		from.setY(from.getBlockY() + 1.0d);
		from.setZ(from.getBlockZ());
		
		Location to = e.getTo().clone();
		to.setX(to.getBlockX());
		to.setY(to.getBlockY() + 1.0d);
		to.setZ(to.getBlockZ());
		
		if (from.getX() == to.getX() && from.getY() == to.getY() && from.getZ() == to.getZ()) {
			return;
		}
		
		lightHelper.recreateLight(from, to, true);
	}
}
