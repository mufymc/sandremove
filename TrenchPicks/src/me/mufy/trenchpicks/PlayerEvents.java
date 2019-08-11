package me.mufy.trenchpicks;

import java.util.Arrays;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

public class PlayerEvents implements Listener {

	Plugin plugin;
	public static ItemStack trenchPick1;
	public static ItemStack trenchPick2;
	public static ItemStack trenchPick3;

	public PlayerEvents(Plugin plugin) {

		this.plugin = plugin;
		setupPicks();

	}

	public void setupPicks() {
		
		trenchPick1 = new ItemStack(Material.DIAMOND_PICKAXE);
		trenchPick1.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemMeta meta = trenchPick1.getItemMeta();
		meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l[&e&lTrench&f&lPickaxe&8&l]"));
		meta.setLore(Arrays.asList(ChatColor.GRAY + "Trench " + "I"));
		trenchPick1.setItemMeta(meta);

		trenchPick2 = new ItemStack(Material.DIAMOND_PICKAXE);
		trenchPick2.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemMeta meta2 = trenchPick2.getItemMeta();
		meta2.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l[&e&lTrench&f&lPickaxe&8&l]"));
		meta2.setLore(Arrays.asList(ChatColor.GRAY + "Trench " + "II"));
		trenchPick2.setItemMeta(meta);

		trenchPick3 = new ItemStack(Material.DIAMOND_PICKAXE);
		trenchPick3.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
		ItemMeta meta3 = trenchPick3.getItemMeta();
		meta3.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l[&e&lTrench&f&lPickaxe&8&l]"));
		meta3.setLore(Arrays.asList(ChatColor.GRAY + "Trench " + "III"));
		trenchPick3.setItemMeta(meta);

	}
	
	public void trench(Block block, int r, Player player) {
		
		for (int x = -r; x <= r; x++) {
			
			for (int z = -r; z <= r; z++) {
				
				for (int y = -r; y <= r; y++) {
					
					FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
					Faction pFac = fPlayer.getFaction();
					Faction lFac = Board.getInstance().getFactionAt(new FLocation(block.getLocation().add(x, y, z)));
					
					if (pFac == lFac || lFac.isWilderness()) {
					
						if (!block.getLocation().add(x, y, z).getBlock().getType().equals(Material.BEDROCK)
							&& !block.getLocation().add(x, y, z).getBlock().getType().equals(Material.MOB_SPAWNER)
							&& !block.getLocation().add(x, y, z).getBlock().getType().equals(Material.HOPPER)
							&& !block.getLocation().add(x, y, z).getBlock().getType().equals(Material.BEACON)) {
							
							block.getLocation().add(x, y, z).getBlock().setType(Material.AIR);
							
						}
						
					}
					
				}
				
			}
			
		}
		
	}
	
	@EventHandler
	public void onTrench(PlayerInteractEvent e) {
		
		if (e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
			
			Player player = e.getPlayer();
			
			if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Trench I")) {
				trench(e.getClickedBlock(), 3, player);
			}
			
			if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Trench II")) {
				trench(e.getClickedBlock(), 4, player);
			}
			
			if (player.getItemInHand().getItemMeta().getLore().contains(ChatColor.GRAY + "Trench III")) {
				trench(e.getClickedBlock(), 5, player);
			}
			
		}
		
	}
	
}
