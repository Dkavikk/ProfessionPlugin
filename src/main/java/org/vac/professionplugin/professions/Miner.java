package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.vac.professionplugin.ProfessionManager;

import java.nio.DoubleBuffer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        boolean allowed_duplicate = false;
        String material_duplicate = "";
        double chance = 0;

        try
        {
            PreparedStatement statement = ProfessionManager.getConnection().prepareStatement(
                    "SELECT xp, allowed_duplicate, material_duplicate, chance_lvl5, chance_lvl10, chance_lvl15, chance_lvl20 FROM miner_profession WHERE material_name = ?"
            );
            statement.setString(1, blockType.name());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                xp = resultSet.getFloat("xp");
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
        AttributeInstance attribute = getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        Objects.requireNonNull(attribute).setBaseValue(4.5f);
    }

    @Override
    public void Level10Reward()
    {

    }

    @Override
    public void Level15Reward()
    {
        AttributeInstance attribute = getPlayer().getAttribute(Attribute.GENERIC_ATTACK_SPEED);
        Objects.requireNonNull(attribute).setBaseValue(5.5f);
    }

    @Override
    public void Level20Reward()
    {
        ItemStack pickaxeItemStack = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemStack netheriteItemStack = new ItemStack(Material.NETHERITE_SCRAP, 1);
        ItemStack netheriteUpgradeItemStack = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1);

        getPlayer().getInventory().addItem(pickaxeItemStack);
        getPlayer().getInventory().addItem(netheriteItemStack);
        getPlayer().getInventory().addItem(netheriteUpgradeItemStack);
    }
}

