package org.vac.professionplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfessionInventoryController implements Listener
{
    private Inventory setProfessioInventory;
    private Inventory setAnimalTrackerInventory;

    private int globalCount;

    public void openSetProfessionInventory(Player player)
    {
        player.openInventory(setProfessioInventory);
    }

    public void OnSetProfessionInventory(InventoryClickEvent event)
    {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR)
        {
            Player targetPlayer = (Player) event.getWhoClicked();
            String professionName = "";

            if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + " "))
            {
                return;
            }

            if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Minero"))
            {
                professionName = "Minero";
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
                professionName = "Cazador";
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.GREEN + "Constructor"))
            {
                professionName = "Constructor";
            }

            if (!professionName.isEmpty())
            {
                ProfessionManager.getInstance().getDataBase().setProfessionDB(targetPlayer, professionName);

                targetPlayer.closeInventory();
            }


        }
    }

    public void onAnimalTrackerInventory(InventoryClickEvent event)
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


    public void CreateSetProfessionInventory()
    {
        setProfessioInventory = ProfessionManager.getInstance().getServer().createInventory(null, 9, ChatColor.BOLD + "Profesiones");

        ItemStack emptyItem = createProfessionTypeItem(Material.GRAY_STAINED_GLASS_PANE, " ", " ");

        ItemStack minerItem = createProfessionTypeItem(Material.IRON_PICKAXE, "Minero", LoreItemInventory.LORE_MINER_PROFESSION);
        ItemStack woodcutterItem = createProfessionTypeItem(Material.IRON_AXE, "Leñador", "");
        ItemStack farmerItem = createProfessionTypeItem(Material.IRON_HOE, "Granjero", "");
        ItemStack HunterItem = createProfessionTypeItem(Material.BOW, "Cazador", LoreItemInventory.LORE_HUNTER_PROFESSION);
        ItemStack BuilderItem = createProfessionTypeItem(Material.IRON_SHOVEL, "Constructor", "");

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

    public void CreateAnimalTrackerInventory()
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

        setAnimalTrackerInventory.setItem(0, createProfessionTypeItem(Material.COMPASS, "Abaja", ""));
        setAnimalTrackerInventory.setItem(1, createProfessionTypeItem(Material.COMPASS, "Ajolote", ""));
        setAnimalTrackerInventory.setItem(2, createProfessionTypeItem(Material.COMPASS, "Caballo", ""));
        setAnimalTrackerInventory.setItem(3, createProfessionTypeItem(Material.COMPASS, "Cabra", ""));
        setAnimalTrackerInventory.setItem(4, createProfessionTypeItem(Material.COMPASS, "Camello", ""));
        setAnimalTrackerInventory.setItem(5, createProfessionTypeItem(Material.COMPASS, "Cerdo", ""));
        setAnimalTrackerInventory.setItem(6, createProfessionTypeItem(Material.COMPASS, "Conejo", ""));
        setAnimalTrackerInventory.setItem(7, createProfessionTypeItem(Material.COMPASS, "Gallina", ""));
        setAnimalTrackerInventory.setItem(8, createProfessionTypeItem(Material.COMPASS, "Gato", ""));
        setAnimalTrackerInventory.setItem(9, createProfessionTypeItem(Material.COMPASS, "Lobo", ""));
        setAnimalTrackerInventory.setItem(10, createProfessionTypeItem(Material.COMPASS, "Loro", ""));
        setAnimalTrackerInventory.setItem(11, createProfessionTypeItem(Material.COMPASS, "Ocelote", ""));
        setAnimalTrackerInventory.setItem(12, createProfessionTypeItem(Material.COMPASS, "Oso polar", ""));
        setAnimalTrackerInventory.setItem(13, createProfessionTypeItem(Material.COMPASS, "Oveja", ""));
        setAnimalTrackerInventory.setItem(14, createProfessionTypeItem(Material.COMPASS, "Panda", ""));
        setAnimalTrackerInventory.setItem(15, createProfessionTypeItem(Material.COMPASS, "Rana", ""));
        setAnimalTrackerInventory.setItem(16, createProfessionTypeItem(Material.COMPASS, "Sniffer", ""));
        setAnimalTrackerInventory.setItem(17, createProfessionTypeItem(Material.COMPASS, "Tortuga", ""));
        setAnimalTrackerInventory.setItem(18, createProfessionTypeItem(Material.COMPASS, "Vaca", ""));
        setAnimalTrackerInventory.setItem(19, createProfessionTypeItem(Material.COMPASS, "Vaca seta", ""));
        setAnimalTrackerInventory.setItem(20, createProfessionTypeItem(Material.COMPASS, "Zorro", ""));

    }


    private ItemStack createProfessionTypeItem(Material material, String name, String description)
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(description);

        Objects.requireNonNull(meta).setDisplayName(ChatColor.GREEN + name);
        if (!description.isEmpty())
        {
            Objects.requireNonNull(meta).setLore(lore);
        }
        item.setItemMeta(meta);

        return item;
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
