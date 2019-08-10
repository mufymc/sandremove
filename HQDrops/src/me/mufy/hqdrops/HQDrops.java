package me.mufy.hqdrops;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;
import net.milkbowl.vault.economy.Economy;

// blazes not dropping just paying
// eyes of ender
// gold
// 


public class HQDrops extends JavaPlugin implements Listener {
	
	private static Economy econ = null;
	private Random rand = new Random();
	private ArrayList<Player> blazeUsers = new ArrayList<Player>();
	
	@Override
	public void onEnable() {
		if (!setupEconomy() ) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
		Bukkit.getServer().getPluginManager().registerEvents(this, this);
		Bukkit.getServer().getLogger().info("HQDrops has been enabled");
	}
	
	@Override
	public void onDisable() {
		getLogger().info(String.format("[%s] Disabled Version %s", getDescription().getName(), getDescription().getVersion()));
		Bukkit.getServer().getLogger().info("HQDrops has been disabled");
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
	public void customDrops(EntityDeathEvent e) {
		
		EntityType entity = e.getEntityType();
		
		// Blazes don't drop
		
		if (entity == EntityType.BLAZE && e.getEntity().getKiller() instanceof Player) {
			
			Player player = e.getEntity().getKiller();
			
			if (!blazeUsers.contains(player)) {
				player.sendMessage(ChatColor.GRAY + "Blaze rods are automatically sold and added to your balance.");
				blazeUsers.add(player);
			}
			
			econ.depositPlayer(player, 20 * e.getDrops().size());
			player.giveExp(e.getDroppedExp());
			e.getDrops().clear();
			e.getEntity().remove();
		}
		
		if (entity == EntityType.CREEPER) {
			
			ItemStack toAdd = new ItemStack(Material.TNT, rand.nextInt(2));
			e.getDrops().clear();
			e.getDrops().add(toAdd);
			
		}
		
		if (entity == EntityType.VILLAGER) {
			
			ItemStack toAdd = new ItemStack(Material.EMERALD, 1);
			e.getDrops().add(toAdd);
			
		}
		
		if (entity == EntityType.SILVERFISH && e.getEntity().getKiller() instanceof Player) {
			
			ItemStack toAdd = new ItemStack(Material.NETHER_STAR, 1);
			e.getDrops().add(toAdd);
			
		}
		
		if (entity == EntityType.WITCH) {
			
			ItemStack toAdd = new ItemStack(Material.EYE_OF_ENDER, 1);
			e.getDrops().clear();
			e.getDrops().add(toAdd);
			
		}
		
		if (entity == EntityType.PIG_ZOMBIE) {
			
			ItemStack toAdd = new ItemStack(Material.GOLD_INGOT, 1);
			e.getDrops().clear();
			e.getDrops().add(toAdd);
			
		}
		
	}
	
	@EventHandler
	public void setHealth(EntitySpawnEvent e) {
		
		EntityType entity = e.getEntityType();
		Entity mob = e.getEntity();
		
		if (entity == EntityType.WITCH) {
			
			mob.setFallDistance(24);
			
		}
		
		if (entity == EntityType.VILLAGER) {
			
			mob.setFallDistance(24);
			
		}
		
		if (entity == EntityType.BLAZE) {
			
			LivingEntity le = (LivingEntity) mob;
			
			le.setHealth(9);
			
		}
		
	}
	
	@EventHandler
	public void onBlazeHit(EntityDamageByEntityEvent e) {
		
		
		if (e.getEntityType() == EntityType.BLAZE && e.getDamager() instanceof Player) {
			
			Entity blaze = e.getEntity();
			
			final Vector vec = new Vector();
		    blaze.setVelocity(vec);
			Bukkit.getScheduler().runTaskLater(this, () -> blaze.setVelocity(vec), 1l);
			
		}
		
	}
	
	
}
