package org.vac.professionplugin.professions;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class Hunter extends Profession
{
    public Hunter(int level, float exp, Player player)
    {
        super("Cazador", level, exp, player);
    }

    @Override
    public void performProfessionAction(BlockBreakEvent event)
    {
        //Block block = event.getBlock();
        //Material blockType = block.getType();
    }

    @Override
    public void performProfessionAction(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
        EntityType entityType = entity.getType();

        if (entityType == EntityType.COW)
        {
            increaseExperience(5.0f);
        }
    }

    @Override
    public void newLevel()
    {
        if (getLevel() == 5)
        {
            Level5Reward();
        }

        if (getLevel() == 10)
        {
            Level10Reward();
        }

        if (getLevel() == 15)
        {
            Level15Reward();
        }

        if (getLevel() == 20)
        {
            Level20Reward();
        }
    }

    @Override
    public void startRepeatTasks()
    {
        if (getLevel() >= 5)
        {
            Level5Reward();
        }

        if (getLevel() >= 10)
        {
            Level10Reward();
        }

        if (getLevel() >= 15)
        {
            Level15Reward();
        }

        if (getLevel() >= 20)
        {
            Level20Reward();
        }
    }

    @Override
    public void Level5Reward()
    {

    }

    @Override
    public void Level10Reward()
    {

    }

    @Override
    public void Level15Reward()
    {

    }

    @Override
    public void Level20Reward()
    {

    }
}
