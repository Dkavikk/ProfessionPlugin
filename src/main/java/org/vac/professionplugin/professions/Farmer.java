package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.metadata.FixedMetadataValue;
import org.vac.professionplugin.ProfessionManager;

public class Farmer extends Profession
{
    public Farmer(int level, float exp, Player player)
    {
        super("Granjero", level, exp, player);
    }

    @Override
    public Inventory getInventoryProfessionData()
    {
        return null;
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event)
    {

    }

    @Override
    public void onBlockBreak(BlockBreakEvent event)
    {

    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (event.getBlock().getType() == Material.WHEAT || event.getBlock().getType() == Material.POTATOES || event.getBlock().getType() == Material.CARROTS)
        {
            event.getBlock().setMetadata("PlantOwner", new FixedMetadataValue(ProfessionManager.getInstance(), getPlayer().getName()));
        }
    }

    @Override
    public void onBlockGrow(BlockGrowEvent event)
    {
        Block block = event.getBlock();
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[onBlockGrow] 3");
        double growthSpeed = 5;

        // Cancelamos el evento de crecimiento y programamos el crecimiento nuevamente con la velocidad ajustada.
        event.setCancelled(true);
        int growthTicks = (int) Math.ceil(1 / growthSpeed); // Convertimos la velocidad a ticks.
        Bukkit.getScheduler().scheduleSyncDelayedTask(ProfessionManager.getInstance(), () ->
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[onBlockGrow] Rutina - 1");
            Ageable ageable = (Ageable) block.getBlockData();
            if (ageable.getAge() == ageable.getMaximumAge())
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[onBlockGrow] Rutina - 2");
                block.setType(block.getType()); // Establecemos el tipo del bloque para refrescar el crecimiento.
            }
            else
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "[onBlockGrow] Rutina - 3");
                if (ageable.getAge() + 2 >  ageable.getMaximumAge())
                {
                    ageable.setAge(ageable.getMaximumAge());
                    block.setBlockData(ageable, true);
                }
                else
                {
                    ageable.setAge(ageable.getAge() + 2);
                    block.setBlockData(ageable, true);
                }
            }
        }, growthTicks);
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event)
    {

    }

    @Override
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {

    }

    @Override
    public void onEntityDamage(EntityDamageEvent event)
    {

    }

    @Override
    public void onPlayerShootBow(EntityShootBowEvent event)
    {

    }

    @Override
    public void onEntityExplode(EntityExplodeEvent event)
    {

    }

    @Override
    public void onEntityBreed(EntityBreedEvent event)
    {

    }

    @Override
    public void level5Reward()
    {

    }

    @Override
    public void level10Reward()
    {

    }

    @Override
    public void level15Reward()
    {

    }

    @Override
    public void level20Reward()
    {

    }

    @Override
    public void leaveProfession()
    {

    }
}
