package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.vac.professionplugin.MinerProfessionData;
import org.vac.professionplugin.ProfessionManager;

import java.nio.DoubleBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void performProfessionAction(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        Material blockType = block.getType();
        double chance = 0;

        MinerProfessionData minerProfessionData = ProfessionManager.getInstance().getDataBase().getMinerProfessionData(block);

        if (getLevel() >= 5)
        {
            chance = minerProfessionData.chanceLVL5;
        }
        else if (getLevel() >= 10)
        {
            chance = minerProfessionData.chanceLVL10;
        }
        else if (getLevel() >= 15)
        {
            chance = minerProfessionData.chanceLVL15;
        }
        else if (getLevel() >= 20)
        {
            chance = minerProfessionData.chanceLVL20;
        }

        if (minerProfessionData.xp > -1)
        {
            increaseExperience(minerProfessionData.xp);
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Block not found: " + blockType.getData().getName());
        }

        if (minerProfessionData.allowedLuminaritaElfica)
        {
            Random random = new Random();
            if (random.nextDouble() <= 0.0001f)
            {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), CreateLuminaritaElfica());
            }
        }

        if (minerProfessionData.allowedDuplicate)
        {
            Material material = Material.getMaterial(minerProfessionData.materialDuplicate);
            Random random = new Random();
            if (material != null)
            {
                if (random.nextDouble() <= chance)
                {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(material));
                }
            }
        }

        if (minerProfessionData.allowedExtraExperience)
        {
            getPlayer().giveExp(calculateExperienceByLVL());
        }

        super.performProfessionAction(event);
    }

    @Override
    public void performProfessionAction(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
        EntityType entityType = entity.getType();

        if (entityType == EntityType.BAT) { increaseExperience(0.5f); }
    }

    @Override
    public void newLevel()
    {
        // TODO Add money economy system

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

    private ItemStack CreateLuminaritaElfica()
    {
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta meta = item.getItemMeta();

        String description = "";
        List<String> lore = new ArrayList<>();
        lore.add(description);

        Objects.requireNonNull(meta).setDisplayName(ChatColor.DARK_PURPLE + "Luminarita Elfica");
        Objects.requireNonNull(meta).setLore(lore);
        Objects.requireNonNull(meta).setCustomModelData(1);

        item.setItemMeta(meta);

        return item;
    }
}

