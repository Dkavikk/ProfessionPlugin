package org.vac.professionplugin.professions;

import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;

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
