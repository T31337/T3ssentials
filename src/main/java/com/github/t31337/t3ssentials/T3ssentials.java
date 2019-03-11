package com.github.t31337.t3ssentials;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class T3ssentials extends JavaPlugin
{
	public CfgMgr  cfgmgr;
	public Cfg      cfg;
	private ItemStack[] PlayerItems;
	public static T3ssentials plugin;
	private Msgr msgr;
	public Logger log    =  Bukkit.getLogger();
	private PluginDescriptionFile pdfile = this.getDescription();
	
	public static Location DeathLocation;
	public boolean CreeperGreif=false;
	public boolean EnderManGreif=false;
	public boolean SpawnGifts=true;
	
	@Override
	public void onEnable()
	{
		plugin = this;
		PluginManager pm = Bukkit.getServer().getPluginManager();
		
		ShapedRecipe BoomStick = new ShapedRecipe(setItemName(new ItemStack(Material.STICK), ChatColor.LIGHT_PURPLE+"BoomStick!"))
				.shape( " ! ",
						"%$%",
						" % ").setIngredient('!',Material.ARROW).setIngredient('$',Material.STICK).setIngredient('%',Material.IRON_INGOT);
		
		this.getServer().addRecipe(BoomStick);
		
		//pm.registerEvents(new onBlockChange(), this);//Dependent On User Config
		pm.registerEvents(new PlayerDeath(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerLeave(),this);
		pm.registerEvents(new EntityExplode(), this);//For Creeper Control
		pm.registerEvents(new onBlockChange(), this);//For EnderMan Control
		pm.registerEvents(new PlayerInteract(), this);//For BoomStick Stuff
		pm.registerEvents(new FriendlyMobs(), this);
		
		cfgmgr = new CfgMgr(this);
		cfg = cfgmgr.getNewConfig("T3ssentials.yml", new String[]{"T3ssentias Configuration File","SuperBoats: Boats In Use By Players Won't Break!",":)"});
		cfg.saveConfig();
		
		if(!cfg.contains("SpawnGifts"))
		{
			cfg.set("SpawnGifts", SpawnGifts);
		}
		else
		{
			SpawnGifts=cfg.getBoolean("SpawnGifts",true);
		}
		if(!cfg.contains("CreeperGreif"))
		{
			cfg.createSection("CreeperGreif");
			cfg.set("CreeperGreif", false);
			cfg.saveConfig();
		}
		else
		{
			CreeperGreif = cfg.getBoolean("CreeperGreif",true);
		}
		
		if(!cfg.contains("EnderManGreif"))
		{
			cfg.createSection("EnderManGreif");
			cfg.set("EnderManGreif",EnderManGreif);
			cfg.saveConfig();
		}
		else
		{
			EnderManGreif = cfg.getBoolean("EnderManGerif",true);
		}
		
		if(!cfg.contains("Messages"))
		{
			cfg.createSection("Messages");
			cfg.set("Messages", false);
			cfg.saveConfig();
			msgr.runTaskTimer(this, 5, 30*20);
			//msgr.runTaskTimer(this, 0, 30*20/*ticks*/);//20 ticks = 1 second
		}
		else
		{
			if(cfg.getBoolean("Messages",true))
			{
				msgr.runTaskTimer(this, 5, 30*20/*ticks*/);//20 ticks = 1 second
			}
		}
		
		/*
		if(!cfg.contains("SuperBoats"))
		{
			cfg.createSection("SuperBoats");
			cfg.set("SuperBoats", true,"SuperBoats - Boats Will Not Break While In Use!");
			cfg.saveConfig();
			pm.registerEvents(new BoatControl(), this);
		}
		else
		{
			if(cfg.getBoolean("SuperBoats",true))
			{
				pm.registerEvents(new BoatControl(), this);
			}
		}
		*/
		
		if(!cfg.contains("FriendlyMobs"))
		{
			cfg.createSection("FriendlyMobs");
			cfg.set("Creepers", true);
			cfg.set("Skeleton", false);
			cfg.set("Spiders", false);
			cfg.set("Zombies", false);
			cfg.set("Blaze", false);
			cfg.set("Enderman", false);
			cfg.set("Endermite", false);
			cfg.set("EnderDragon", true);
			cfg.set("Withers",false);
			cfg.set("Witch", true);
			cfg.set("Wolf", false);
			cfg.set("Ghast", false);
			cfg.saveConfig();
			
		}
		
		//msgr = new Msgr(this);
		//msgr.runTaskTimer(this, 0, 30*20/*ticks*/);//20 ticks = 1 second//Dependent On User Config
		
		this.getCommand("setSpawn").setExecutor(new setSpawn(this));
		this.getCommand("Donkey").setExecutor(new DonkeyCmd(this));
		this.getCommand("heal").setExecutor(new Heal(this));
		this.getCommand("pvp").setExecutor(new TogglePVP(this));
		this.getCommand("gm").setExecutor(new gm(this));
		this.getCommand("respawn").setExecutor(new Respawn(this));
		this.getCommand("summon").setExecutor(new summon(this));
		this.getCommand("BoomStick").setExecutor(new BoomStick(this));
		this.getCommand("loc").setExecutor(new loc(this));
		this.getCommand("rl").setExecutor(new rl(this));
		this.getCommand("su").setExecutor(new su(this));
		/*
		this.getCommand("Creeper").setExecutor(new CreeperCmd(this));
		this.getCommand("PoweredCreeper").setExecutor(new PoweredCreeper(this));
		this.getCommand("oi").setExecutor(new oi(this));
		this.getCommand("pat").setExecutor(new pat(this));
		this.getCommand("ni").setExecutor(new ni(this));
		this.getCommand("day").sstExecutor(new Day(this));
		this.getCommand("feed").setExecutor(new Feed(this));
		*/
		
		
		log.info(pdfile.getName()+" Version: "+pdfile.getVersion()+" Enabled!");
	}
	
	@Override
	public void onDisable()
	{
		Bukkit.getServer().clearRecipes();
		Bukkit.getPluginManager().disablePlugin(this);
		log.info(pdfile.getName()+" Version: "+pdfile.getVersion()+" Disabled :(");
		plugin = null;
	}
	
	
	public ItemStack setItemName(ItemStack item,String name)
	{
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
}
