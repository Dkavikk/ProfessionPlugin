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


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ProfessionManager extends JavaPlugin implements Listener
{
    private static ProfessionManager instance;
    private static Connection connection;

    private ProfessionInventoryController inventoryController;
    private List<BukkitRunnable>repeatTasks;

    private Inventory professioTypeInventory;


    public static Connection getConnection()
    {
        return connection;
    }
    public static ProfessionManager getInstance()
    {
        return instance;
    }
    public ProfessionInventoryController getInventoryController()
    {
        return inventoryController;
    }
    public void addRepeatTasks(BukkitRunnable task)
    {
        repeatTasks.add(task);
        task.runTaskTimer(this, 20 * 30, 20 * 30);
    }

    @Override
    public void onEnable()
    {
        instance = this;

        // Establecer la conexión a la base de datos
        String databaseURL = "jdbc:mysql://localhost:3306/minecraft";
        String username = "root";
        String password = "Indiopicaro.1";

        repeatTasks = new ArrayList<>();

        inventoryController = new ProfessionInventoryController();
        inventoryController.CreateSetProfessionInventory();

        try
        {
            connection = DriverManager.getConnection(databaseURL, username, password);
            Objects.requireNonNull(getCommand("setprofesion")).setExecutor(new SetProfessionCommand());
            //Objects.requireNonNull(getCommand("setprofesion")).setTabCompleter(new SetProfessionCommandTabCompletation());
            Objects.requireNonNull(getCommand("getprofesion")).setExecutor(new GetProfessionCommand());
            Objects.requireNonNull(getCommand("salirprofesion")).setExecutor(new LeavingProfessionCommand());
            Objects.requireNonNull(getCommand("test")).setExecutor(new CommandsTest());
            //Objects.requireNonNull(getCommand("test")).setExecutor(this);

            getServer().getPluginManager().registerEvents(this, this);
            Bukkit.getConsoleSender().sendMessage("[ProfessionPlugin] "+ ChatColor.GREEN + "Connected to the database!");
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to connect to the database: " + e.getMessage());
            this.setEnabled(false);
        }
    }

    @Override
    public void onDisable()
    {
        // Cerrar la conexión a la base de datos
        try
        {
            if (connection != null && !connection.isClosed())
            {
                connection.close();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Disconnected from the database!");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        try
        {
            String professionName;
            int level;
            float exp;
            Profession profession = null;

            // Obtener la profesión del jugador desde la base de datos
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT profession_name, profession_level, profession_exp FROM player_professions WHERE player_uuid = ?"
            );
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                professionName = resultSet.getString("profession_name");
                level = resultSet.getInt("profession_level");
                exp = resultSet.getInt("profession_exp");
                profession = Profession.getProfessionByName(professionName, level, exp, player);
            }

            if (profession != null)
            {
                profession.startRepeatTasks();
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to get profession: " + e.getMessage());
        }

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

        Profession profession = getPlayerProfessionByDB(player);

        if (profession != null)
        {
            profession.performProfessionAction(event);
            UpdateProfessionInDB(player, profession);
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

            Profession profession = getPlayerProfessionByDB(player);

            if (profession != null)
            {
                profession.performProfessionAction(event);
                UpdateProfessionInDB(player, profession);
            }
        }
    }

    private void UpdateProfessionInDB(Player player, Profession profession)
    {
        // Obtén el nivel actual de la profesión del jugador desde la base de datos
        //int currentLevel = getPlayerProfessionLevel(player, profession);

        int currentLevel = profession.getLevel();
        float currentExp = profession.getExp();

        try
        {
            // Actualiza el nivel de la profesión en la base de datos
            PreparedStatement statement = connection.prepareStatement(
                    "UPDATE player_professions SET profession_level = ?, profession_exp = ? WHERE player_uuid = ? AND profession_name = ?"
            );
            statement.setInt(1, currentLevel);
            statement.setFloat(2, currentExp);
            statement.setString(3, player.getUniqueId().toString());
            statement.setString(4, profession.getName());

            if (profession.getLevelUp())
            {
                player.sendMessage(ChatColor.GREEN + "Has subido de nivel ");
                player.sendMessage(ChatColor.GREEN + "Ahora eres nivel: " + currentLevel);
            }

            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e)
        {
            player.sendMessage(ChatColor.RED + "No se pudo aumentar el nivel de profesión o exp: " + e.getMessage());
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to increase profession level or exp: " + e.getMessage());
        }
    }

    private Profession getPlayerProfessionByDB(Player player)
    {
        try
        {
            String professionName;
            int level;
            float exp;
            Profession profession = null;

            // Obtener la profesión del jugador desde la base de datos
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT profession_name, profession_level, profession_exp FROM player_professions WHERE player_uuid = ?"
            );
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                professionName = resultSet.getString("profession_name");
                level = resultSet.getInt("profession_level");
                exp = resultSet.getInt("profession_exp");
                profession = Profession.getProfessionByName(professionName, level, exp, player);

                return profession;
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to get profession: " + e.getMessage());
        }
        return null;
    }
}
