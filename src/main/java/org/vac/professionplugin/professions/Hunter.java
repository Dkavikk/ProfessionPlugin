package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.vac.professionplugin.ProfessionManager;
import org.vac.professionplugin.custom_items.CustomAnimalTrackerItem;

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
                    if (Math.random() < 0.5)
                    {
                        event.getMother().getWorld().spawnEntity(event.getMother().getLocation(), event.getMother().getType());
                    }

                    increaseExperience(entityDataProfession.xpKill);
                    ProfessionManager.getInstance().getDataBase().UpdateProfessionInDB(getPlayer(), this);
                }
            }
        }
    }

    @Override
    public void newLevel()
    {
        super.newLevel();
    }

    @Override
    public void level5Reward()
    {
        // TODO Insignia de profesion lvl 5
        AttributeInstance maxHealthAttribute = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double increasedMaxHealth = maxHealthAttribute.getBaseValue() + (2.0);
        maxHealthAttribute.setBaseValue(increasedMaxHealth);
    }

    @Override
    public void level10Reward()
    {
        // TODO Insignia de profesion lvl 10
        getPlayer().sendMessage("Has conseguido la habilidad de Tiro Preciso");
    }

    @Override
    public void level15Reward()
    {
        // TODO Insignia de profesion lvl 15
        ItemStack customItem = new CustomAnimalTrackerItem();

        // Verifica si el inventario del jugador tiene espacio para el item
        if (getPlayer().getInventory().firstEmpty() != -1)
        {
            getPlayer().getInventory().addItem(customItem);
            getPlayer().sendMessage(ChatColor.YELLOW + "¡Has recibido un Rastreador de animales!");
        }
        else
        {
            // El inventario del jugador está lleno, deja caer el item al suelo en su posición actual
            getPlayer().getWorld().dropItem(getPlayer().getLocation(), customItem);
            getPlayer().sendMessage(ChatColor.YELLOW + "Tu inventario está lleno. El Rastreador de animales ha sido dejado en el suelo.");
        }
    }

    @Override
    public void level20Reward()
    {
        // TODO Insignia de profesion lvl 20
        AttributeInstance maxHealthAttribute = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double increasedMaxHealth = maxHealthAttribute.getBaseValue() + (2.0);
        maxHealthAttribute.setBaseValue(increasedMaxHealth);
    }

    @Override
    public void leaveProfession()
    {
        AttributeInstance maxHealthAttribute = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        maxHealthAttribute.setBaseValue(20);
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
