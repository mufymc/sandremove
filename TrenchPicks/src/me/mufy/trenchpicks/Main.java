package me.mufy.trenchpicks;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import me.mufy.trenchpicks.PlayerEvents;
import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	public void onEnable() {
		
		Bukkit.getPluginManager().registerEvents(new PlayerEvents(this), this);
		Bukkit.getServer().getLogger().info("TrenchPicks by Mufy has been enabled");
		
	}
	
	public void onDisable() {
		
		Bukkit.getServer().getLogger().info("TrenchPicks by Mufy has been disabled");
		
	}
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equals("trenchpick")) {
			
			if (!sender.hasPermission("trenchpick.give")) {
				
				sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
				return true;
				
			}
			
			if (args.length != 2) {
				
				sender.sendMessage(ChatColor.RED + "You must specify a player and trench level");
				return true;
				
			}
			
			Player target = Bukkit.getServer().getPlayer(args[0]);
			
			if (target == null) {
				
				sender.sendMessage(ChatColor.RED + "Player not found!");
				return true;
				
			}
			
			int level = Integer.parseInt(args[1]);	
			
			if (level > 3 || level < 1) {
				
				sender.sendMessage(ChatColor.RED + "You must specify Trench level 1-3");
				return true;
				
			}
			
			ItemStack trenchPick = new ItemStack(Material.GOLD_PICKAXE);
			
			if (level == 1) {
				
				trenchPick.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
				ItemMeta meta = trenchPick.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l[&e&lTrench&f&lPickaxe&8&l]"));
				meta.setLore(Arrays.asList(ChatColor.GRAY + "Trench " + "I"));
				trenchPick.setItemMeta(meta);
				target.getInventory().addItem(trenchPick);
				return true;
				
			}
			
			if (level == 2) {

				trenchPick.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
				ItemMeta meta = trenchPick.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l[&e&lTrench&f&lPickaxe&8&l]"));
				meta.setLore(Arrays.asList(ChatColor.GRAY + "Trench " + "II"));
				trenchPick.setItemMeta(meta);
				target.getInventory().addItem(trenchPick);
				return true;
				
			}
			
			if (level == 3) {
				
				trenchPick.addUnsafeEnchantment(Enchantment.DURABILITY, 10);
				ItemMeta meta = trenchPick.getItemMeta();
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&8&l[&e&lTrench&f&lPickaxe&8&l]"));
				meta.setLore(Arrays.asList(ChatColor.GRAY + "Trench " + "III"));
				trenchPick.setItemMeta(meta);
				target.getInventory().addItem(trenchPick);
				return true;
				
			}
			
		}
		
		return true;
		
	}
	
}
