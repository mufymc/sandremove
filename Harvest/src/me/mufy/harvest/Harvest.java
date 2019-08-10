package me.mufy.harvest;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
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

import net.milkbowl.vault.economy.Economy;

public class Harvest extends JavaPlugin implements Listener {

    private static Economy econ = null;
	
	@Override
	public void onEnable() {
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getLogger().info("Harvest has been enabled");
	}
	
	@Override
	public void onDisable() {
		getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
		Bukkit.getServer().getLogger().info("Harvest has been disabled");
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
	
	private void harvestRecurse(Block next, Faction pFac, Player player) {
		
		if (!next.getType().equals(Material.SUGAR_CANE_BLOCK))
			return;
		
		Faction lFac = Board.getInstance().getFactionAt(new FLocation(next.getLocation()));
		
		if (pFac == lFac || lFac.isWilderness()) {
			
			if (next.getLocation().add(0, 3, 0).getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
				next.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
				next.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
				next.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
				next.setType(Material.AIR);
				econ.depositPlayer(player, 20);
			} else if (next.getLocation().add(0, 2, 0).getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
				next.getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
				next.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
				next.setType(Material.AIR);
				econ.depositPlayer(player, 15);
			} else if (next.getLocation().add(0, 1, 0).getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
				next.getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
				next.setType(Material.AIR);
				econ.depositPlayer(player, 10);
			} else {
				econ.depositPlayer(player, 5);
				next.setType(Material.AIR);
			}
			
			harvestRecurse(next.getLocation().add(1, 0, 0).getBlock(), pFac, player);
			harvestRecurse(next.getLocation().add(-1, 0, 0).getBlock(), pFac, player);
			harvestRecurse(next.getLocation().add(0, 0, 1).getBlock(), pFac, player);
			harvestRecurse(next.getLocation().add(0, 0, -1).getBlock(), pFac, player);
			
		}
		
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		
		if (e.getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
		
			Player player = e.getPlayer();
			
			if (player.hasPermission("harvest.use")) {
				
				FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
				Faction pFac = fPlayer.getFaction();
				Faction lFac = Board.getInstance().getFactionAt(new FLocation(e.getBlock().getLocation()));
				
				if (pFac == lFac || lFac.isWilderness()) {
				
					if (player.hasPermission("havest.multiple") && player.isSneaking()) {
						
						if (e.getBlock().getLocation().add(0, 3, 0).getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
							e.getBlock().getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
							e.getBlock().getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
							e.getBlock().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
							e.getBlock().setType(Material.AIR);
							econ.depositPlayer(player, 20);
						} else if (e.getBlock().getLocation().add(0, 2, 0).getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
							e.getBlock().getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
							e.getBlock().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
							e.getBlock().setType(Material.AIR);
							econ.depositPlayer(player, 15);
						} else if (e.getBlock().getLocation().add(0, 1, 0).getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
							e.getBlock().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
							e.getBlock().setType(Material.AIR);
							econ.depositPlayer(player, 10);
						} else {
							econ.depositPlayer(player, 5);
							e.getBlock().setType(Material.AIR);
						}
						
						harvestRecurse(e.getBlock().getLocation().add(1, 0, 0).getBlock(), pFac, player);
						harvestRecurse(e.getBlock().getLocation().add(-1, 0, 0).getBlock(), pFac, player);
						harvestRecurse(e.getBlock().getLocation().add(0, 0, 1).getBlock(), pFac, player);
						harvestRecurse(e.getBlock().getLocation().add(0, 0, -1).getBlock(), pFac, player);
					
					} else {
							
						if (e.getBlock().getLocation().add(0, 3, 0).getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
							e.getBlock().getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
							e.getBlock().getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
							e.getBlock().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
							e.getBlock().setType(Material.AIR);
							econ.depositPlayer(player, 20);
						} else if (e.getBlock().getLocation().add(0, 2, 0).getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
							e.getBlock().getLocation().add(0, 2, 0).getBlock().setType(Material.AIR);
							e.getBlock().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
							e.getBlock().setType(Material.AIR);
							econ.depositPlayer(player, 15);
						} else if (e.getBlock().getLocation().add(0, 1, 0).getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)) {
							e.getBlock().getLocation().add(0, 1, 0).getBlock().setType(Material.AIR);
							e.getBlock().setType(Material.AIR);
							econ.depositPlayer(player, 10);
						} else {
							econ.depositPlayer(player, 5);
							e.getBlock().setType(Material.AIR);
						}
					
					}
				
				
				}
			
			}
		
		}	
	}
	
}
