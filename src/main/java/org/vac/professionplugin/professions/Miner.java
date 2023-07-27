package org.vac.professionplugin.professions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.vac.professionplugin.ProfessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Miner extends Profession
{
    public Miner(int level, float exp, Player player)
    {
        super("Minero", level, exp, player);
    }


    @Override
    public void onBlockBreak(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        BlockDataProfession blockDataProfession = ProfessionManager.getInstance().getDataBase().getBlockDataForBlockName(block.getType().name());

        if (blockDataProfession != null)
        {
            if (belongToProfession(blockDataProfession))
            {
                increaseExperience(blockDataProfession.xpBreak);
                // TODO Cambiar metodo para identificar cada recompensa rara de profession
                //            if (blockDataProfession.allowedLuminaritaElfica)
                //            {
                //                Random random = new Random();
                //                if (random.nextDouble() <= 0.0001f)
                //                {
                //                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), CreateLuminaritaElfica());
                //                }
                //            }

                if (blockDataProfession.allowedDuplicate)
                {
                    Material material = Material.getMaterial(blockDataProfession.materialDuplicate);
                    Random random = new Random();
                    if (material != null)
                    {
                        if (random.nextDouble() <= getChance(blockDataProfession))
                        {
                            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(material));
                        }
                    }
                }

                if (blockDataProfession.allowedExtraExperience)
                {
                    getPlayer().giveExp(calculateExperienceByLVL());
                }

                ProfessionManager.getInstance().getDataBase().UpdateProfessionInDB(getPlayer(), this);
            }
        }
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();

        EntityDataProfession entityDataProfession = ProfessionManager.getInstance().getDataBase().getEntityDataProfession(entity);

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
    {}

    @Override
    public void onPlayerShootBow(EntityShootBowEvent event)
    {}

    @Override
    public void onEntityBreed(EntityBreedEvent event)
    {}

    @Override
    public void newLevel()
    {
        // TODO quisas quitar, al hacer override no es nesesario hacer un super(), ya se hace solo (Desconosco esto de JAVA)
    }

    @Override
    public void level5Reward()
    {
        // TODO Insignia de profesion lvl 5
    }

    @Override
    public void level10Reward()
    {
        // TODO Insignia de profesion lvl 10
    }

    @Override
    public void level15Reward()
    {
        // TODO Insignia de profesion lvl 15
    }

    @Override
    public void level20Reward()
    {
        // TODO Insignia de profesion lvl 20
    }

    @Override
    public void leaveProfession()
    {

    }

    private int calculateExperienceByLVL()
    {
        if (getLevel() >= 5 && getLevel() < 10)
        {
            return 3;
        }
        else if (getLevel() >= 10 && getLevel() < 15)
        {
            return 5;
        }
        else if (getLevel() >= 15 && getLevel() < 20)
        {
            return 8;
        }
        else if (getLevel() >= 20)
        {
            return 10;
        }

        return 0;
    }

    private double getChance(BlockDataProfession blockDataProfession)
    {
        if (getLevel() >= 20)
        {
            return blockDataProfession.chanceLVL20;
        }
        else if (getLevel() >= 15)
        {
            return blockDataProfession.chanceLVL15;
        }
        else if (getLevel() >= 10)
        {
            return blockDataProfession.chanceLVL10;
        }
        else if (getLevel() >= 5)
        {
            return blockDataProfession.chanceLVL5;
        }

        return 0;
    }

    private ItemStack CreateLuminaritaElfica()
    {
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta meta = item.getItemMeta();

        String description =
                "Mineral extremadamente raro y poderoso" +
                "se deconose su uso, pero se siente su poder";
        List<String> lore = new ArrayList<>();
        lore.add(description);

        Objects.requireNonNull(meta).setDisplayName(ChatColor.DARK_PURPLE + "Luminarita Elfica");
        Objects.requireNonNull(meta).setLore(lore);
        Objects.requireNonNull(meta).setCustomModelData(1);

        item.setItemMeta(meta);

        return item;
    }
}

