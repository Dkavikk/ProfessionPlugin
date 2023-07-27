package org.vac.professionplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.vac.professionplugin.commands.*;
import org.vac.professionplugin.custom_items.CustomAnimalTrackerItem;
import org.vac.professionplugin.custom_items.InteractionCustomItemsListener;
import org.vac.professionplugin.inventory.ProfessionInventoryController;
import org.vac.professionplugin.professions.Profession;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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
        inventoryController.CreateAnimalTrackerInventory();

        Objects.requireNonNull(getCommand("setprofesion")).setExecutor(new SetProfessionCommand());
        //Objects.requireNonNull(getCommand("setprofesion")).setTabCompleter(new SetProfessionCommandTabCompletation());
        Objects.requireNonNull(getCommand("getprofesion")).setExecutor(new GetProfessionCommand());
        Objects.requireNonNull(getCommand("salirprofesion")).setExecutor(new LeavingProfessionCommand());
        Objects.requireNonNull(getCommand("test")).setExecutor(new CommandsTest());
        getServer().getPluginManager().registerEvents(this, this);
        getServer().getPluginManager().registerEvents(new InteractionCustomItemsListener(), this);
    }

    @Override
    public void onDisable()
    {
        DataBase.disconnectFromDatabase();
    }

    // Evento para manejar el uso del Animal Tracker
//    @EventHandler
//    public void onAnimalTrackerUse(PlayerInteractEvent event) {
//        Player player = event.getPlayer();
//        ItemStack item = player.getInventory().getItemInMainHand();
//
//        for (Entity entity : targetPlayer.getNearbyEntities(radius, radius, radius))
//        {
//            // Verifica si la entidad es un animal
//            if (entity instanceof Animals)
//            {
//                Animals animal = (Animals) entity;
//
//                if (animal.getType() == animalTracker)
//                {
//                    ScoreboardManager manager = Bukkit.getScoreboardManager();
//                    Scoreboard board = Objects.requireNonNull(manager).getNewScoreboard();
//                    Team team = board.registerNewTeam("animalTracker");
//                    team.setColor(ChatColor.GOLD);
//                    team.addEntry(animal.getUniqueId().toString());
//
//                    // Aplica el efecto "glowing"
//                    animal.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 20, 0, false, false));
//                }
//
//            }
//        }
//
//
//        // Comprueba si el jugador está usando el Animal Tracker
//        if (item != null && item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName().equals("Animal Tracker")) {
//            // Obtiene las entidades cercanas al jugador
//            for (Entity entity : player.getNearbyEntities(20, 20, 20)) { // Ajusta los valores para definir el rango de búsqueda
//                // Comprueba si la entidad es un animal
//                if (entity instanceof AnimalTamer) {
//                    // Muestra partículas sobre el animal
//                    entity.getWorld().spawnParticle(Particle.HEART, entity.getLocation().add(0, 1, 0), 10);
//                }
//            }
//            // Si quieres agregar algún mensaje al jugador cuando use el Animal Tracker, puedes usar el siguiente código:
//            // player.sendMessage("Animales cercanos detectados!");
//        }
//    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        if (event.getInventory() == inventoryController.getSetProfessioInventory())
        {
            event.setCancelled(true);
            inventoryController.OnSetProfessionInventory(event);
        }
        else if (event.getInventory() == inventoryController.getSetAnimalTrackerInventory())
        {
            event.setCancelled(true);
            inventoryController.onAnimalTrackerInventory(event);
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
        player.sendMessage("Has picado un bloque!");
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
            player.sendMessage("Has matado un entidad!");
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
            event.getDamager().sendMessage("Has atacado a un entidad!");
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
                player.sendMessage("Has has apareado a una entidad!");
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
