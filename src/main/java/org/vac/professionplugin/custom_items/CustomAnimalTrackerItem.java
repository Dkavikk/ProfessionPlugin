package org.vac.professionplugin.custom_items;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
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

public class CustomAnimalTrackerItem extends ItemStack
{
    public CustomAnimalTrackerItem()
    {
        super(Material.COMPASS); // Establece el material del item (brújula en este caso)

        ItemMeta meta = getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Este Item permite selecionar un animal \npara buscar durante 20 segudos");

        Objects.requireNonNull(meta).setDisplayName(ChatColor.DARK_PURPLE + "Rastreador de animales"); // Nombre del item que se mostrará en el inventario
        Objects.requireNonNull(meta).setLore(lore); // Descripcion del item que se mostrará en el inventario
        // Objects.requireNonNull(meta).setCustomModelData(°n); // Modelo custom del Item

        setItemMeta(meta);
    }

    public static void trackAnimals(Player player)
    {
        Inventory AnimalTrackerInventory = ProfessionManager.getInstance().getInventoryController().getSetAnimalTrackerInventory();
        player.openInventory(AnimalTrackerInventory);
    }


}
