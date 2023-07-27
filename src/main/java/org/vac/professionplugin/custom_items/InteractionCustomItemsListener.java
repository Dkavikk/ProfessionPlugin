package org.vac.professionplugin.custom_items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.vac.professionplugin.ProfessionManager;
import org.vac.professionplugin.professions.Profession;

import java.util.Objects;

public class InteractionCustomItemsListener implements Listener
{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        // Verifica si el jugador está usando el Animal Tracker
        if (item != null && item.getType() == Material.COMPASS && item.getItemMeta().getDisplayName().equals(ChatColor.DARK_PURPLE + "Rastreador de animales"))
        {
            Profession profession = ProfessionManager.getInstance().getDataBase().getPlayerProfession(player);
            // TODO Verificar profecion y nivel valido
            
            CustomAnimalTrackerItem.trackAnimals(player);
        }
    }
}
