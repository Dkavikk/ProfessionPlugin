package org.vac.professionplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.vac.professionplugin.commands.*;
import org.vac.professionplugin.custom_items.InteractionCustomItemsListener;
import org.vac.professionplugin.inventory.ProfessionInventoryController;
import org.vac.professionplugin.professions.Miner;
import org.vac.professionplugin.professions.Profession;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.vac.professionplugin.professions.UndergroundProtection;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ProfessionManager extends JavaPlugin implements Listener
{

    private static ProfessionManager instance;
    private ProfessionDataBase DataBase;
    private ProfessionInventoryController inventoryController;
    private Map<Player, UndergroundProtection> playerUndergroundProtectionMap;

    @Override
    public void onEnable()
    {
        instance = this;
        DataBase = new ProfessionDataBase();
        DataBase.connectToDatabase();
        inventoryController = new ProfessionInventoryController();
        inventoryController.createInventory();
        playerUndergroundProtectionMap = new HashMap<>();

        registerCommands();
        registerListeners();
    }

    @Override
    public void onDisable()
    {
        DataBase.disconnectFromDatabase();
    }

    private void registerCommands()
    {
        Objects.requireNonNull(getCommand("setprofesion")).setExecutor(new SetProfessionCommand());
        //Objects.requireNonNull(getCommand("setprofesion")).setTabCompleter(new SetProfessionCommandTabCompletation());
        Objects.requireNonNull(getCommand("getprofesion")).setExecutor(new GetProfessionCommand());
        Objects.requireNonNull(getCommand("salirprofesion")).setExecutor(new LeavingProfessionCommand());
        Objects.requireNonNull(getCommand("test")).setExecutor(new CommandsTest());
    }

    private void registerListeners()
    {
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new InteractionCustomItemsListener(), this);
        getServer().getPluginManager().registerEvents(inventoryController, this);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        Profession profession = DataBase.getPlayerProfession(player);

        if (profession instanceof Miner)
        {
            UndergroundProtection undergroundProtection = new UndergroundProtection(player);
            playerUndergroundProtectionMap.put(player, undergroundProtection);
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Profession profession = DataBase.getPlayerProfession(player);

        if (profession != null)
        {
            profession.onBlockBreak(event);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();

        if (player != null)
        {
            Profession profession = DataBase.getPlayerProfession(player);

            if (profession != null)
            {
                profession.onEntityDeath(event);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event)
    {

        // Verifica si el daño fue causado a un jugador
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            Profession profession = DataBase.getPlayerProfession(player);

            if (profession != null)
            {
                profession.onEntityDamage(event);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        // Verifica si el daño es causado por un jugador
        if (event.getDamager() instanceof Player)
        {
            Player player = (Player) event.getDamager();
            Profession profession = DataBase.getPlayerProfession(player);

            if (profession != null)
            {
                profession.onEntityDamage(event);
            }
        }
    }

    @EventHandler
    public void onPlayerShootBow(EntityShootBowEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            Profession profession = DataBase.getPlayerProfession(player);

            if (profession != null)
            {
                profession.onPlayerShootBow(event);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        Profession profession = DataBase.getPlayerProfession(player);

        if (profession != null)
        {
            profession.onPlayerMove(event);
        }
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event)
    {
        if (event.getEntity() instanceof Player)
        {
            Player player = (Player) event.getEntity();
            Profession profession = DataBase.getPlayerProfession(player);

            if (profession != null)
            {
                profession.onEntityExplode(event);
            }
        }
    }

    @EventHandler
    public void onEntityBreed(EntityBreedEvent event)
    {
        if (event.getMother().getType() == event.getFather().getType())
        {
            if (event.getBreeder() instanceof Player)
            {
                Player player = (Player) event.getBreeder();
                Profession profession = DataBase.getPlayerProfession(player);

                if (profession != null)
                {
                    profession.onEntityBreed(event);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        Profession profession = DataBase.getPlayerProfession(player);

        if (profession != null)
        {
            profession.onBlockPlace(event);
        }
    }

    @EventHandler
    public void onBlockGrow(BlockGrowEvent event)
    {
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[onBlockGrow] 0");
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[onBlockGrow] -" + event.getBlock().getType());
        Block block = event.getBlock();
        if (block.getType() == Material.WHEAT || block.getType() == Material.POTATOES || block.getType() == Material.CARROTS)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[onBlockGrow] 1");
            // Obtenemos el jugador que plantó el cultivo de los metadatos.
            if (block.hasMetadata("PlantOwner"))
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[onBlockGrow] 2");
                String plantOwner = block.getMetadata("PlantOwner").get(0).asString();

                // Verificamos si el jugador está en línea y obtenemos su nivel de crecimiento.
                Player player = Bukkit.getPlayerExact(plantOwner);
                if (player != null)
                {
                    Profession profession = DataBase.getPlayerProfession(player);

                    if (profession != null)
                    {
                        profession.onBlockGrow(event);
                    }
                }
            }
        }
    }

    public Map<Player, UndergroundProtection> getPlayerUndergroundProtectionMap()
    {
        return playerUndergroundProtectionMap;
    }

    public ProfessionDataBase getDataBase()
    {
        return DataBase;
    }

    public static ProfessionManager getInstance()
    {
        return instance;
    }

    public ProfessionInventoryController getInventoryController()
    {
        return inventoryController;
    }
}