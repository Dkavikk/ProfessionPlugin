package org.vac.professionplugin.professions;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.vac.professionplugin.ProfessionManager;

public class Hunter extends Profession
{
    public Hunter(int level, float exp, Player player)
    {
        super("Cazador", level, exp, player);
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event)
    {}

    @Override
    public void onEntityDeath(EntityDeathEvent event)
    {
        EntityDataProfession entityDataProfession = ProfessionManager.getInstance().getDataBase().getEntityDataProfession(event.getEntity());

        if (entityDataProfession != null)
        {
            if (belongToProfession(entityDataProfession))
            {
//                Material original = Material.getMaterial(entityDataProfession.materialOriginal);
//                Material cooked = Material.getMaterial(entityDataProfession.materialCooked);

                // Cocina la carne despues de lvl 5 de profesion
//                if (entityDataProfession.allowedCooked && getLevel() >= 5)
//                {
//                    int amountOfRawMeat = 0;
//                    for (ItemStack drop : event.getDrops())
//                    {
//                        if (drop.getType() == original)
//                        {
//                            amountOfRawMeat += drop.getAmount();
//                        }
//                    }
//
//                    event.getDrops().removeIf(item -> item.getType() == original);
//                    ItemStack cookedMeat = new ItemStack(Objects.requireNonNull(cooked), amountOfRawMeat);
//                    event.getDrops().add(cookedMeat);
//                }

                increaseExperience(entityDataProfession.xpKill);
                ProfessionManager.getInstance().getDataBase().UpdateProfessionInDB(getPlayer(), this);
            }
        }
    }

    @Override
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        event.setDamage(event.getDamage() + getExtraDamageForLVL());
    }

    @Override
    public void onPlayerShootBow(EntityShootBowEvent event)
    {
        if (getLevel() >= 10)
        {
            event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(1.5));
        }
    }

    @Override
    public void onEntityBreed(EntityBreedEvent event)
    {
        EntityDataProfession entityDataProfession = ProfessionManager.getInstance().getDataBase().getEntityDataProfession(event.getEntity());

        if (entityDataProfession != null)
        {
            if (belongToProfession(entityDataProfession))
            {
                if (event.getEntity().getType() == EntityType.WOLF)
                {
                    // TODO Probabilidad de que salgo gemelos
                    increaseExperience(entityDataProfession.xpKill);
                    ProfessionManager.getInstance().getDataBase().UpdateProfessionInDB(getPlayer(), this);
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

        AttributeInstance maxHealthAttribute = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double increasedMaxHealth = maxHealthAttribute.getBaseValue() + (2.0);
        maxHealthAttribute.setBaseValue(increasedMaxHealth);
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

        AttributeInstance maxHealthAttribute = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double increasedMaxHealth = maxHealthAttribute.getBaseValue() + (2.0);
        maxHealthAttribute.setBaseValue(increasedMaxHealth);
    }

    @Override
    public void Level20Reward()
    {
        // TODO Insignia de profesion lvl 20
    }

    private float getExtraDamageForLVL()
    {
        if (getLevel() >= 20)
        {
            return 3;
        }
        else if (getLevel() >= 15)
        {
            return 2;
        }
        return 0;
    }
}
