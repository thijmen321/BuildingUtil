package me.thijmen321.buildingutil;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class BlockInteractListener implements Listener
{
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
		PlayerInventory inv = e.getPlayer().getInventory();
		
		Material placed = e.getBlock().getType();
		
		if (inv.getItemInHand().getAmount() == 1)// == null || inv.getItemInHand().getType() == Material.AIR)
		{
			e.getPlayer().sendMessage("1");
			
			
			if (inv.contains(placed))
			{
				e.getPlayer().sendMessage("2");
				
				
				for (int i = 0; i < inv.getContents().length; i++)
				{
					ItemStack item = inv.getItem(i);
					
					if (item.getType() == placed)
					{
						inv.setItemInHand(item);
						
						inv.clear(i);
						
						e.getPlayer().sendMessage("3");
						
						continue;
					}						
				}
				
			
				//int sourceSlot = 9;
				
				//inv.setItemInHand(inv.getItem(sourceSlot));
				
				//inv.clear(sourceSlot);
				
				//e.getPlayer().sendMessage("3");
			}
		}
	}
	
	@EventHandler
	public void onPlayerBlockInteract(PlayerInteractEvent e) {

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
				case COCOA: getTool(pi, ToolEnum.AXE, pi.getHeldItemSlot());
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
				case SNOW: getTool(pi, ToolEnum.SPADE, pi.getHeldItemSlot());
	
				default: return;
			}
		}
	}

	private void getTool(Inventory pi, ToolEnum tool, int slot) {

		int materialInt = 0, backupSlot = 0;
		ItemStack it = null;

		for(int in = 0; in < pi.getContents().length; in++)
		{
			ItemStack i = pi.getContents()[in];
			if(i.getType().name() == null) return;
			
			String name = i.getType().name();

			if(name.contains("_") && name.split("_")[1].equals(tool.name())) {
				int temp = MaterialEnum.valueOf(name.split("_")[0]).getValue();
				if(temp > materialInt) {
					it = i;
					materialInt = temp;
					backupSlot = in;
				}
			}
			else continue;
		}

		if(materialInt  <= 0 || it == null) return;

		ItemStack backup = pi.getItem(slot);

		pi.remove(it);
		pi.clear(slot);
		pi.setItem(slot, new ItemStack(Material.valueOf(MaterialEnum.findByValue(materialInt).name() + "_" + tool.name())));
		pi.setItem(backupSlot, backup);

	}

}
