package me.mufy.sandremove;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

public class SandRemove extends JavaPlugin implements Listener {

private static Economy econ = null;
	
	@Override
	public void onEnable() {
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getLogger().info("SandRemove has been enabled");
	}
	
	@Override
	public void onDisable() {
		getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
		Bukkit.getServer().getLogger().info("SandRemove has been disabled");
	}
	
	private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		if (e.getBlock().getType().equals(Material.SAND)) {
		
			Player player = e.getPlayer();
			
			if (player.hasPermission("sandremove.use") && player.isSneaking() && player.getItemInHand().getAmount() == 0) {
				
				FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
				Faction pFac = fPlayer.getFaction();
				Faction lFac = Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation()));
				
				if (pFac == lFac || lFac.isWilderness()) {
					
					Location current = e.getBlock().getLocation();
					double amount = 0.00;
					
					do {
						
						current.getBlock().setType(Material.AIR);
						current.add(0, -1, 0);
						amount++;
						
					} while (current.getBlock().getType().equals(Material.SAND));
					
					player.sendMessage(ChatColor.GRAY + "You just broke a sand stack for $" + amount);
					econ.withdrawPlayer(player, amount);
				}
				
			}
			
		}
		
	}
	
}
