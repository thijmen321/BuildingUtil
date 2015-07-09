package me.thijmen321.buildingutil;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

public class BlockInteractListener implements Listener
{
	private Plugin plugin;
	
	public BlockInteractListener(Plugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) 
	{
		Player p = e.getPlayer();
		PlayerInventory inv = p.getInventory();

		Material placed = e.getBlock().getType();
		
		if(plugin.getConfig().contains("banned-blocks"))
		{
			for(String s : plugin.getConfig().getStringList("banned-blocks"))
				if(Material.valueOf(s).equals(placed)); return;
		}
			
		
		if (p.getGameMode() != GameMode.CREATIVE && inv.getItemInHand().getAmount() == 1)
			for (int i = 0; i < inv.getContents().length; i++)
			{
				if (inv.getHeldItemSlot() == i) continue;
				
				ItemStack item = inv.getItem(i);
				if(item == null || item.getType() == null || item.getType().name() == null) continue;
				
				if (item.getType() == placed)
				{
					inv.setItemInHand(item);

					inv.clear(i);

					break;
				}
			}
	}

	//TODO(Thijmen) add more items
	@EventHandler
	public void onPlayerBlockInteract(PlayerInteractEvent e) 
	{
		if(e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			PlayerInventory pi = e.getPlayer().getInventory();
			Block b = e.getClickedBlock();
			switch(b.getType())
			{
			/*
			 * AXE
			 */
			case ACACIA_DOOR:															
			case ACACIA_FENCE:
			case ACACIA_FENCE_GATE:
			case ACACIA_STAIRS:

			case BIRCH_DOOR:
			case BIRCH_FENCE:
			case BIRCH_FENCE_GATE:
			case BIRCH_WOOD_STAIRS:

			case WOOD:
			case WOODEN_DOOR:
			case WOOD_STAIRS:

			case DARK_OAK_DOOR:
			case DARK_OAK_FENCE:
			case DARK_OAK_FENCE_GATE:
			case DARK_OAK_STAIRS:

			case BOOKSHELF:
			case CHEST:
			case PUMPKIN:
			case JUKEBOX:
			case DAYLIGHT_DETECTOR:
			case DAYLIGHT_DETECTOR_INVERTED:
			case NOTE_BLOCK:	
			case VINE:
			case SIGN:
			case COCOA: 
				getTool(pi, ToolEnum.AXE, pi.getHeldItemSlot());
				break;
			//TODO Oak wood, Dark wood items are missing! Crafting Table, Jack o'Lantern & Huge Mushrooms are missing

			/*
			 * SHOVEL
			 */
			case CLAY:
			case SOIL: //Farmland
			case GRASS:
			case GRAVEL:
			case MYCEL:
			case DIRT:
			case SAND:
			case SOUL_SAND:
			case SNOW: 
				getTool(pi, ToolEnum.SPADE, pi.getHeldItemSlot());
				break;

			default: 
				return;
			}
		}
	}

	//TODO(Thijmen) Add descriptions
	/**
	 * Set tool made out of the best material to a specific slot in player's inventory
	 * @param pi Add description
	 * @param tool Add description
	 * @param slot Add description
	 */
	private void getTool(PlayerInventory pi, ToolEnum tool, int slot) 
	{
		int materialInt = 0, backupSlot = 0;
		ItemStack it = null;

		for(int in = 0; in < pi.getContents().length; in++)
		{
			if (pi.getHeldItemSlot() == in) continue;
			ItemStack i = pi.getContents()[in];
			if(i == null || i.getType() == null || i.getType().name() == null) continue;

			String name = i.getType().name();

			if(name.contains("_") && name.split("_")[1].equals(tool.name())) {
				int temp = MaterialEnum.valueOf(name.split("_")[0]).getValue();
				if(temp > materialInt) {
					it = i;
					materialInt = temp;
					backupSlot = in;
				}
			}
		}

		if(materialInt  <= 0 || it == null) return;
		
		if(plugin.getConfig().contains("banned-blocks"))
		{
			for(String s : plugin.getConfig().getStringList("banned-tools"))
				if(Material.valueOf(s).equals(it)); return;
		}

		ItemStack backup = pi.getItem(slot);

		pi.remove(it);
		pi.clear(slot);
		pi.setItem(slot, it);
		pi.setItem(backupSlot, backup);
	}
}
