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
import org.vac.professionplugin.ProfessionManager;

import java.nio.DoubleBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Miner extends Profession {
    public Miner(int level, float exp, Player player)
    {
        super("Minero", level, exp, player);
    }


    @Override
    public void performProfessionAction(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        Material blockType = block.getType();

        float xp = -1;
        boolean allowed_luminarita_elfica = false;
        boolean allowed_duplicate = false;
        String material_duplicate = "";
        double chance = 0;

        try
        {
            PreparedStatement statement = ProfessionManager.getConnection().prepareStatement(
                    "SELECT xp, allowed_luminarita_elfica ,allowed_duplicate, material_duplicate, chance_lvl5, chance_lvl10, chance_lvl15, chance_lvl20 FROM miner_profession WHERE material_name = ?"
            );
            statement.setString(1, blockType.name());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                xp = resultSet.getFloat("xp");
                allowed_luminarita_elfica = resultSet.getBoolean("allowed_luminarita_elfica");
                allowed_duplicate = resultSet.getBoolean("allowed_duplicate");
                //allowed_duplicate = resultSet.getInt("allowed_duplicate") == 1;
                material_duplicate = resultSet.getString("material_duplicate");

                if (getLevel() >= 5)
                {
                    chance = resultSet.getDouble("chance_lvl5");
                }
                else if (getLevel() >= 10)
                {
                    chance = resultSet.getDouble("chance_lvl10");
                }
                else if (getLevel() >= 15)
                {
                    chance = resultSet.getDouble("chance_lvl15");
                }
                else if (getLevel() >= 20)
                {
                    chance = resultSet.getDouble("chance_lvl20");
                }
            }
            else
            {
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Block not found: " + blockType.getData().getName());
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to get miner_profession: " + e.getMessage());
        }

        if (xp > -1)
        {
            increaseExperience(xp);
        }
        else
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Block not found: " + blockType.getData().getName());
        }

        if (allowed_luminarita_elfica)
        {
            Random random = new Random();
            if (random.nextDouble() <= 0.0001f)
            {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), CreateLuminaritaElfica());
            }
        }

        if (allowed_duplicate)
        {
            Material material = Material.getMaterial(material_duplicate);
            Random random = new Random();
            if (material != null)
            {
                if (random.nextDouble() <= chance)
                {
                    event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(material));
                }
            }
        }

        if (menirarAllowedForExtraExperience(blockType))
        {
            getPlayer().giveExp(calculateExperienceByLVL());
        }
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
    public void startRepeatTasks()
    {
    }

    @Override
    public void Level5Reward()
    {

    }

    @Override
    public void Level10Reward()
    {
        AttributeInstance attribute = getPlayer().getAttribute(Attribute.GENERIC_ARMOR);
        Objects.requireNonNull(attribute).setBaseValue(attribute.getValue() + 1);
    }

    @Override
    public void Level15Reward()
    {
    }

    @Override
    public void Level20Reward()
    {
    }

    private boolean menirarAllowedForExtraExperience(Material material) {
        // List of minerals that give experience when mined
        return material == Material.COAL_ORE ||
                material == Material.IRON_ORE ||
                material == Material.COPPER_ORE ||
                material == Material.GOLD_ORE ||
                material == Material.DIAMOND_ORE ||
                material == Material.ANCIENT_DEBRIS ||
                material == Material.EMERALD_ORE;
    }

    private int calculateExperienceByLVL()
    {
        if (getLevel() >= 5 && getLevel() < 10)
        {
            return 5;
        }
        else if (getLevel() >= 10 && getLevel() < 15)
        {
            return 8;
        }
        else if (getLevel() >= 15 && getLevel() < 20)
        {
            return 11;
        }
        else if (getLevel() >= 20)
        {
            return 13;
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

