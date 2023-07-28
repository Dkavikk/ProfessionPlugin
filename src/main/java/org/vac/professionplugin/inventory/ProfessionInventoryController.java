package org.vac.professionplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.vac.professionplugin.ProfessionManager;
import org.vac.professionplugin.professions.Hunter;
import org.vac.professionplugin.professions.Miner;
import org.vac.professionplugin.professions.Profession;

import java.util.*;

public class ProfessionInventoryController implements Listener
{
    private Map<Player, Inventory> playerViewProfessionInventoryMap;
    private Inventory setProfessioInventory;
    private Inventory setAnimalTrackerInventory;

    private int globalCount;


    @EventHandler
    public void onInventoryClick(InventoryClickEvent event)
    {
        Player player = (Player) event.getWhoClicked();

        if (event.getInventory() == setProfessioInventory)
        {
            event.setCancelled(true);
            onSetProfessionInventory(event);
        }
        else if (event.getInventory() == setAnimalTrackerInventory)
        {
            event.setCancelled(true);
            onAnimalTrackerInventory(event);
        }
        else if (event.getInventory() == playerViewProfessionInventoryMap.get(player));
        {
            event.setCancelled(true);
            onInventoryProfessionData(event);
        }
    }

    public void openSetProfessionInventory(Player player)
    {
        player.openInventory(setProfessioInventory);
    }

    private void onSetProfessionInventory(InventoryClickEvent event)
    {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR)
        {
            Player player = (Player) event.getWhoClicked();
            String professionName = "";

            if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(" "))
            {
                return;
            }

            if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Minero"))
            {
                Profession profession = new Miner(1, 1, player);
                playerViewProfessionInventoryMap.put(player, profession.getInventoryProfessionData());
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Leñador"))
            {
                professionName = "Leñador";
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Granjero"))
            {
                professionName = "Granjero";
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Cazador"))
            {
                Profession profession = new Hunter(1, 1, player);
                playerViewProfessionInventoryMap.put(player, profession.getInventoryProfessionData());
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Constructor"))
            {
                professionName = "Constructor";
            }



           if (!playerViewProfessionInventoryMap.get(player).isEmpty())
           {
               // ProfessionManager.getInstance().getDataBase().setProfessionDB(player, professionName);
               // player.closeInventory();

               player.closeInventory();
               player.openInventory(playerViewProfessionInventoryMap.get(player));
           }


        }
    }

    private void onInventoryProfessionData(InventoryClickEvent event)
    {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR)
        {
            Player player = (Player) event.getWhoClicked();

            String ItemName = Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName();

            if (ItemName.equals(" "))
            {
                return;
            }

            if (ItemName.equals(ChatColor.GREEN + "Aceptar"))
            {
                List<String> professionName = event.getCurrentItem().getItemMeta().getLore();
                ProfessionManager.getInstance().getDataBase().setProfessionDB(player, Objects.requireNonNull(professionName).get(0));
                player.closeInventory();
                playerViewProfessionInventoryMap.remove(player);
            }
            else if (ItemName.equals(ChatColor.RED + "Cancelar"))
            {
                player.closeInventory();
                playerViewProfessionInventoryMap.remove(player);
                openSetProfessionInventory(player);
            }
        }
    }

    private void onAnimalTrackerInventory(InventoryClickEvent event)
    {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR)
        {
            Player targetPlayer = (Player) event.getWhoClicked();
            EntityType animalTracker = null;
            String animalName = Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName();

            if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Abaja"))
            {
                animalTracker = EntityType.BEE;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Ajolote"))
            {
                animalTracker = EntityType.AXOLOTL;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Caballo"))
            {
                animalTracker = EntityType.HORSE;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Cabra"))
            {
                animalTracker = EntityType.GOAT;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Camello"))
            {
                animalTracker = EntityType.CAMEL;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Cerdo"))
            {
                animalTracker = EntityType.PIG;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Conejo"))
            {
                animalTracker = EntityType.RABBIT;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Gallina"))
            {
                animalTracker = EntityType.CHICKEN;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Gato"))
            {
                animalTracker = EntityType.CAT;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Lobo"))
            {
                animalTracker = EntityType.WOLF;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Loro"))
            {
                animalTracker = EntityType.PARROT;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Ocelote"))
            {
                animalTracker = EntityType.OCELOT;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Oso polar"))
            {
                animalTracker = EntityType.POLAR_BEAR;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Oveja"))
            {
                animalTracker = EntityType.SHEEP;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Panda"))
            {
                animalTracker = EntityType.PANDA;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Rana"))
            {
                animalTracker = EntityType.FROG;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Sniffer"))
            {
                animalTracker = EntityType.SNIFFER;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Tortuga"))
            {
                animalTracker = EntityType.TURTLE;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Vaca"))
            {
                animalTracker = EntityType.COW;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Vaca seta"))
            {
                animalTracker = EntityType.MUSHROOM_COW;
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Zorro"))
            {
                animalTracker = EntityType.FOX;
            }

            int animalCount = 0;

            // Obtén todas las entidades en el radio especificado alrededor del jugador
            int radius = 25;
            Scoreboard scoreboard = Objects.requireNonNull(Bukkit.getScoreboardManager()).getMainScoreboard();
            Team team = scoreboard.registerNewTeam(ChatColor.GOLD + "animalTracker" + animalName + targetPlayer.getDisplayName() + globalCount);
            team.setColor(ChatColor.GOLD);
            globalCount++;
            for (Entity entity : targetPlayer.getNearbyEntities(radius, radius, radius))
            {
                // Verifica si la entidad es un animal
                if (entity instanceof Animals)
                {
                    Animals animal = (Animals) entity;

                    if (animal.getType() == animalTracker)
                    {
                        team.addEntry(animal.getUniqueId().toString());
                        //entity.setGlowing(true);
                        animal.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 20 * 20, 0, false,false));

                        animalCount++;
                    }
                }
            }

            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    team.unregister();
                    globalCount--;
                }
            }.runTaskLater(ProfessionManager.getInstance(), 20 * 20);

            if (animalCount > 0)
            {
                targetPlayer.sendMessage(ChatColor.GREEN + "Se encontro " + animalCount + " " + animalName);
                targetPlayer.closeInventory();
            }
            else
            {
                targetPlayer.sendMessage(ChatColor.RED + "No se a encontrado ningun " +  animalName);
            }
        }
    }


    private void CreateSetProfessionInventory()
    {
        setProfessioInventory = ProfessionManager.getInstance().getServer().createInventory(null, 9, ChatColor.BOLD + "Profesiones");

        ItemStack emptyItem = createProfessionTypeItem(Material.GRAY_STAINED_GLASS_PANE, " ", new ArrayList<>());

        ItemStack minerItem = createProfessionTypeItem(Material.IRON_PICKAXE, ChatColor.GREEN + "Minero", LoreItemInventory.LORE_MINER_PROFESSION);
        ItemStack woodcutterItem = createProfessionTypeItem(Material.IRON_AXE, ChatColor.GREEN + "Leñador", new ArrayList<>());
        ItemStack farmerItem = createProfessionTypeItem(Material.IRON_HOE, ChatColor.GREEN + "Granjero", new ArrayList<>());
        ItemStack HunterItem = createProfessionTypeItem(Material.BOW, ChatColor.GREEN + "Cazador", LoreItemInventory.LORE_MINER_PROFESSION);
        ItemStack BuilderItem = createProfessionTypeItem(Material.IRON_SHOVEL, ChatColor.GREEN + "Constructor", new ArrayList<>());

        setProfessioInventory.setItem(0, emptyItem);
        setProfessioInventory.setItem(1, emptyItem);

        setProfessioInventory.setItem(2, minerItem);
        setProfessioInventory.setItem(3, woodcutterItem);
        setProfessioInventory.setItem(4, farmerItem);
        setProfessioInventory.setItem(5, HunterItem);
        setProfessioInventory.setItem(6, BuilderItem);

        setProfessioInventory.setItem(7, emptyItem);
        setProfessioInventory.setItem(8, emptyItem);
    }

    private void CreateAnimalTrackerInventory()
    {
        setAnimalTrackerInventory = ProfessionManager.getInstance().getServer().createInventory(null, 27, ChatColor.BOLD + "Profesiones");
        //Abaja
        //Ajolote
        //Caballo
        //Cabra
        //Camello
        //Cerdo
        //Conejo
        //Gallina
        //Gato
        //Lobo
        //Loro
        //Ocelote
        //Oso polar
        //Oveja
        //Panda
        //Rana
        //Sniffer
        //Tortuga
        //Vaca
        //Vaca seta
        //Zorro

        setAnimalTrackerInventory.setItem(0, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Abaja", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(1, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Ajolote", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(2, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Caballo", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(3, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Cabra", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(4, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Camello", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(5, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Cerdo", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(6, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Conejo", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(7, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Gallina", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(8, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Gato", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(9, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Lobo", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(10, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Loro", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(11, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Ocelote", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(12, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Oso polar", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(13, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Oveja", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(14, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Panda", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(15, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Rana", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(16, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Sniffer", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(17, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Tortuga", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(18, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Vaca", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(19, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Vaca seta", new ArrayList<>()));
        setAnimalTrackerInventory.setItem(20, createProfessionTypeItem(Material.COMPASS, ChatColor.GREEN + "Zorro", new ArrayList<>()));

    }


    public static ItemStack createProfessionTypeItem(Material material, String name, List<String> description)
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(name);
        if (!description.isEmpty())
        {
            Objects.requireNonNull(meta).setLore(description);
        }
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createProfessionTypeItem(Material material, String name, String description)
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(name);

        List<String> lore = new ArrayList<>();
        lore.add(description);

        if (!description.isEmpty())
        {
            Objects.requireNonNull(meta).setLore(lore);
        }
        item.setItemMeta(meta);

        return item;
    }

    public void createInventory()
    {
        playerViewProfessionInventoryMap = new HashMap<>();

        CreateSetProfessionInventory();
        CreateAnimalTrackerInventory();
    }

    public Inventory getSetProfessioInventory()
    {
        return setProfessioInventory;
    }

    public Inventory getSetAnimalTrackerInventory()
    {
        return setAnimalTrackerInventory;
    }
}
