package org.vac.professionplugin.inventory;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.vac.professionplugin.ProfessionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProfessionInventoryController implements Listener
{
    private Inventory setProfessioInventory;
    public Inventory getSetProfessioInventory()
    {
        return setProfessioInventory;
    }

    public void openSetProfessioInventory(Player player)
    {
        player.openInventory(setProfessioInventory);
    }

    public void OnSetProfessioInventory(InventoryClickEvent event)
    {
        if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR)
        {
            Player targetPlayer = (Player) event.getWhoClicked();
            String professionName = "";

            if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + " "))
            {
                return;
            }

            if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Minero"))
            {
                professionName = "Minero";
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Leñador"))
            {
                professionName = "Leñador";
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Granjero"))
            {
                professionName = "Granjero";
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Cazador"))
            {
                professionName = "Cazador";
            }
            else if (Objects.requireNonNull(event.getCurrentItem().getItemMeta()).getDisplayName().equals(ChatColor.YELLOW + "Constructor"))
            {
                professionName = "Constructor";
            }

            try
            {
                // Guardar la profesión en la base de datos
                PreparedStatement statement = ProfessionManager.getConnection().prepareStatement(
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

    public void CreateSetProfessionInventory()
    {
        setProfessioInventory = ProfessionManager.getInstance().getServer().createInventory(null, 9, ChatColor.BOLD + "Profesiones");

        ItemStack emptyItem = createProfessionTypeItem(Material.GRAY_STAINED_GLASS_PANE, "", "");

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


        //player.openInventory(setProfessioInventory);
    }



    private ItemStack createProfessionTypeItem(Material material, @NotNull String name, String description)
    {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        List<String> lore = new ArrayList<>();
        lore.add(description);

        Objects.requireNonNull(meta).setLore(lore);
        Objects.requireNonNull(meta).setDisplayName(ChatColor.GREEN + name);

        item.setItemMeta(meta);

        return item;
    }
}
