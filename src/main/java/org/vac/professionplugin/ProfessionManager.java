package org.vac.professionplugin;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.vac.professionplugin.commands.*;
import org.vac.professionplugin.inventory.ProfessionInventoryController;
import org.vac.professionplugin.professions.Profession;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;


import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProfessionManager extends JavaPlugin implements Listener
{
    private static ProfessionManager instance;
    private ProfessionDataBase DataBase;

    private ProfessionInventoryController inventoryController;

    @Override
    public void onEnable()
    {
        instance = this;

        DataBase = new ProfessionDataBase();
        DataBase.connectToDatabase();

        inventoryController = new ProfessionInventoryController();
        inventoryController.CreateSetProfessionInventory();

        Objects.requireNonNull(getCommand("setprofesion")).setExecutor(new SetProfessionCommand());
        //Objects.requireNonNull(getCommand("setprofesion")).setTabCompleter(new SetProfessionCommandTabCompletation());
        Objects.requireNonNull(getCommand("getprofesion")).setExecutor(new GetProfessionCommand());
        Objects.requireNonNull(getCommand("salirprofesion")).setExecutor(new LeavingProfessionCommand());
        Objects.requireNonNull(getCommand("test")).setExecutor(new CommandsTest());
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable()
    {

    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory() == inventoryController.getSetProfessioInventory())
        {
            event.setCancelled(true);
            inventoryController.OnSetProfessioInventory(event);
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        player.sendMessage("Has picado un bloque!");

        Profession profession = DataBase.getPlayerProfession(player);

        if (profession != null)
        {
            profession.performProfessionAction(event);
//            DataBase.UpdateProfessionInDB(player, profession);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
        Player player = entity.getKiller();

        if (player != null)
        {
            player.sendMessage("Has matado un mob!");

            Profession profession = DataBase.getPlayerProfession(player);

            if (profession != null)
            {
                profession.performProfessionAction(event);
//                DataBase.UpdateProfessionInDB(player, profession);
            }
        }
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
