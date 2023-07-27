package org.vac.professionplugin.inventory;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.vac.professionplugin.ProfessionManager;

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

            if (!professionName.equals(""))
            {
                ProfessionManager.getInstance().getDataBase().setProfessionDB(targetPlayer, professionName);

                targetPlayer.closeInventory();
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
