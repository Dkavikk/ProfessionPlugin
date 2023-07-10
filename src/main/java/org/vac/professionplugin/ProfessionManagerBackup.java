package org.vac.professionplugin;

/*import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.vac.professionplugin.commands.ProfessionCommands;
import org.vac.professionplugin.commands.ProfessionTabCompletation;
import org.vac.professionplugin.professions.Profession;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
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
import java.util.UUID;*/

public class ProfessionManagerBackup /*extends JavaPlugin implements Listener*/
{
    /*private static ProfessionManager instance;
    private static Connection connection;

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

        try
        {
            connection = DriverManager.getConnection(databaseURL, username, password);
            Bukkit.getConsoleSender().sendMessage("[ProfessionPlugin] "+ ChatColor.GREEN + "Connected to the database!");
            Objects.requireNonNull(getCommand("setprofesion")).setExecutor(new ProfessionCommands());
            Objects.requireNonNull(getCommand("setprofesion")).setTabCompleter(new ProfessionTabCompletation());
            Objects.requireNonNull(getCommand("getprofesion")).setExecutor(new ProfessionCommands());
            Objects.requireNonNull(getCommand("salirprofesion")).setExecutor(new ProfessionCommands());
            //Objects.requireNonNull(getCommand("test")).setExecutor(new CommandsTest());
            Objects.requireNonNull(getCommand("test")).setExecutor(this);

            getServer().getPluginManager().registerEvents(this, this);
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

    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("test"))
        {
            Player player = (Player) sender;

            if (!player.isOp())
            {
                sender.sendMessage(ChatColor.RED + "Que wea asi mierda metia!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Que wea asi mierda metia!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "El " + player.getName() + " trato de usar el comando /test");
                return true;
            }
            openProfessionInventory(player);

            return true;
        }

        return false;
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
        if (event.getInventory() == professioTypeInventory) {
            event.setCancelled(true);

            if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                Player targetPlayer = (Player) event.getWhoClicked();
                String professionName = "";

                if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + ""))
                {
                    return;
                }

                if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Minero"))
                {
                    targetPlayer.sendMessage(ChatColor.GREEN + "Se ha selecionado la professio Minero");
                    professionName = "Minero";
                }
                else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Leñador"))
                {
                    targetPlayer.sendMessage(ChatColor.GREEN + "Se ha selecionado la professio Leñador");
                    professionName = "Leñador";
                }
                else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Granjero"))
                {
                    targetPlayer.sendMessage(ChatColor.GREEN + "Se ha selecionado la professio Granjero");
                    professionName = "Granjero";
                }
                else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Cazador"))
                {
                    targetPlayer.sendMessage(ChatColor.GREEN + "Se ha selecionado la professio Cazador");
                    professionName = "Cazador";
                }
                else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Constructor"))
                {
                    targetPlayer.sendMessage(ChatColor.GREEN + "Se ha selecionado la professio Constructor");
                    professionName = "Constructor";
                }

                try
                {
                    // Guardar la profesión en la base de datos
                    PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO player_professions (player_uuid, profession_name, profession_level, profession_exp) VALUES (?, ?, ?, ?)"
                    );
                    statement.setString(1, targetPlayer.getUniqueId().toString());
                    statement.setString(2, professionName);
                    statement.setInt(3, 1);
                    statement.setFloat(4, 1);

                    statement.executeUpdate();
                    statement.close();

                    targetPlayer.sendMessage(ChatColor.GREEN + "Profesión establecida con éxito para el jugador " + targetPlayer.getName() +
                            ": " + professionName);
                }
                catch (SQLException e)
                {
                    targetPlayer.sendMessage(ChatColor.RED + "Failed to set profession: " + e.getMessage());
                }

                targetPlayer.closeInventory();

            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        player.sendMessage("Has picado un bloque!");

        Profession profession = getPlayerProfessionByDB(player);

        if (profession != null)
        {
            Material blockType = block.getType();
            profession.performProfessionAction(blockType);
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
                EntityType entityType = entity.getType();
                profession.performProfessionAction(entityType);
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


    public void openProfessionInventory(Player player)
    {
        professioTypeInventory = getServer().createInventory(null, 9, ChatColor.BOLD + "Profesiones");

        ItemStack emptyItem = createProfessionTypeItem(Material.GRAY_STAINED_GLASS_PANE, "");

        ItemStack minerItem = createProfessionTypeItem(Material.IRON_PICKAXE, "Minero");
        ItemStack woodcutterItem = createProfessionTypeItem(Material.IRON_AXE, "Leñador");
        ItemStack farmerItem = createProfessionTypeItem(Material.IRON_HOE, "Granjero");
        ItemStack HunterItem = createProfessionTypeItem(Material.BOW, "Cazador");
        ItemStack BuilderItem = createProfessionTypeItem(Material.IRON_SHOVEL, "Constructor");

        professioTypeInventory.setItem(0, emptyItem);
        professioTypeInventory.setItem(1, emptyItem);

        professioTypeInventory.setItem(2, minerItem);
        professioTypeInventory.setItem(3, woodcutterItem);
        professioTypeInventory.setItem(4, farmerItem);
        professioTypeInventory.setItem(5, HunterItem);
        professioTypeInventory.setItem(6, BuilderItem);

        professioTypeInventory.setItem(7, emptyItem);
        professioTypeInventory.setItem(8, emptyItem);


        player.openInventory(professioTypeInventory);
    }

    private ItemStack createProfessionTypeItem(Material material, String name)
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + name);
        item.setItemMeta(meta);

        return item;
    }
*/
}
