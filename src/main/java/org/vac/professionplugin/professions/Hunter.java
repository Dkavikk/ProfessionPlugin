package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.vac.professionplugin.HunterProfessionData;
import org.vac.professionplugin.ProfessionManager;

import java.util.Objects;

public class Hunter extends Profession
{
    public Hunter(int level, float exp, Player player)
    {
        super("Cazador", level, exp, player);
    }

    @Override
    public void performProfessionAction(BlockBreakEvent event)
    {
    }

    @Override
    public void performProfessionAction(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();

        EntityDataProfession entityDataProfession = ProfessionManager.getInstance().getDataBase().getHunterProfessionData(entity);

        if (entityDataProfession != null)
        {
            if (belongToProfession(entityDataProfession))
            {
                Material original = Material.getMaterial(entityDataProfession.materialOriginal);
                Material cooked = Material.getMaterial(entityDataProfession.materialCooked);

                // Cocina la carne despues de lvl 5 de profesion
                if (entityDataProfession.allowedCooked && getLevel() >= 5)
                {
                    int amountOfRawMeat = 0;
                    for (ItemStack drop : event.getDrops())
                    {
                        if (drop.getType() == original)
                        {
                            amountOfRawMeat += drop.getAmount();
                        }
                    }

                    event.getDrops().removeIf(item -> item.getType() == original);
                    ItemStack cookedMeat = new ItemStack(Objects.requireNonNull(cooked), amountOfRawMeat);
                    event.getDrops().add(cookedMeat);
                }

            }
        }
    }

    @Override
    public void newLevel()
    {
    }

    @Override
    public void Level5Reward()
    {
        // TODO Insignia de profesion lvl 5
    }

    @Override
    public void Level10Reward()
    {
        // TODO Insignia de profesion lvl 10
    }

    @Override
    public void Level15Reward()
    {
        // TODO Insignia de profesion lvl 15
    }

    @Override
    public void Level20Reward()
    {
        // TODO Insignia de profesion lvl 20
    }
}
