package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
//        if (getExperienceByBlock(blockType) > -1)
//        {
//            increaseExperience(getExperienceByBlock(blockType));
//        }
//        else
//        {
//            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No se a ecotrado el material " + blockType.getData().getName());
//        }
//
//        Material material = getAdditionalOre(blockType);
//        if (material != null)
//        {
//            Random random = new Random();
//            if (random.nextDouble() <= getChanceAdditionalOre(material))
//            {
//                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(material));
//            }
//        }

        float xp = -1;
        boolean allowed_duplicate = false;
        String material_duplicate = "";
        double chance = 0;
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "____");

        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "1" + blockType.name());
        Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "_____");

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
//        if (getLevel() >= 5)
//        {
//            Level5Reward();
//        }
    }

    @Override
    public void Level5Reward()
    {
        getPlayer().setWalkSpeed(0.4f);

//        // Crea una nueva tarea programada para repetir el efecto cada 5 minutos
//        BukkitRunnable repeatTask = new BukkitRunnable()
//        {
//            @Override
//            public void run()
//            {
//                // Aplica el efecto de velocidad nuevamente
//                getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, EFFECT_DURATION, 1));
//            }
//        };
//
//        ProfessionManager professionManager = ProfessionManager.getInstance();
//
//        // agregar la tarea de repetición al professionManager
//        professionManager.addRepeatTasks(repeatTask);
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
        ItemStack pickaxeItemStack = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemStack netheriteItemStack = new ItemStack(Material.NETHERITE_SCRAP, 1);
        ItemStack netheriteUpgradeItemStack = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE, 1);

        getPlayer().getInventory().addItem(pickaxeItemStack);
        getPlayer().getInventory().addItem(netheriteItemStack);
        getPlayer().getInventory().addItem(netheriteUpgradeItemStack);
    }

    private float getExperienceByBlock(Material material)
    {
        if (material == Material.STONE)
                              { if (getLevel() < 3)             {  return 5.0f;   }
                                else                            {  return 2.5f;   }}
        else if (material == Material.COBBLESTONE)
                              { if (getLevel() < 5)             {  return 1.0f;   }
                                else                            {  return 0.5f;   }}
        else if (material == Material.NETHERRACK)
                              { if (getLevel() < 5)             {  return 1.0f;   }
                                else                            {  return 0.5f;   }}
        else if (material == Material.SANDSTONE)                {  return 2.0f;   }
        else if (material == Material.RED_SANDSTONE)            {  return 2.0f;   }
        else if (material == Material.END_STONE)                {  return 5.0f;   }
        else if (material == Material.DEEPSLATE)                {  return 2.0f;   }
        else if (material == Material.CALCITE)                  {  return 4.0f;   }
        else if (material == Material.TUFF)                     {  return 4.0f;   }
        else if (material == Material.BLACKSTONE)               {  return 3.0f;   }
        else if (material == Material.BASALT)                   {  return 1.0f;   }
        else if (material == Material.ANDESITE)                 {  return 2.0f;   }
        else if (material == Material.DIORITE)                  {  return 2.0f;   }
        else if (material == Material.GRANITE)                  {  return 2.0f;   }
        else if (material == Material.DRIPSTONE_BLOCK)          {  return 3.0f;   }
        else if (material == Material.AMETHYST_BLOCK)           {  return 8.0f;   }
        else if (material == Material.BUDDING_AMETHYST)         {  return 11.0f;  }
        else if (material == Material.SMALL_AMETHYST_BUD)       {  return 0.2f;   }
        else if (material == Material.MEDIUM_AMETHYST_BUD)      {  return 0.5f;   }
        else if (material == Material.LARGE_AMETHYST_BUD)       {  return 1.0f;   }
        else if (material == Material.AMETHYST_CLUSTER)         {  return 2.0f;   }
        else if (material == Material.COAL_ORE)                 {  return 3.0f;   }
        else if (material == Material.IRON_ORE)                 {  return 4.0f;   }
        else if (material == Material.COPPER_ORE)               {  return 4.0f;   }
        else if (material == Material.GOLD_ORE)                 {  return 7.0f;   }
        else if (material == Material.LAPIS_ORE)                {  return 6.0f;   }
        else if (material == Material.REDSTONE_ORE)             {  return 8.0f;   }
        else if (material == Material.EMERALD_ORE)              {  return 10.0f;  }
        else if (material == Material.DIAMOND_ORE)              {  return 9.0f;   }
        else if (material == Material.DEEPSLATE_COAL_ORE)       {  return 5.0f;   }
        else if (material == Material.DEEPSLATE_IRON_ORE)       {  return 5.0f;   }
        else if (material == Material.DEEPSLATE_COPPER_ORE)     {  return 9.0f;   }
        else if (material == Material.DEEPSLATE_GOLD_ORE)       {  return 6.0f;   }
        else if (material == Material.DEEPSLATE_LAPIS_ORE)      {  return 6.5f;   }
        else if (material == Material.DEEPSLATE_REDSTONE_ORE)   {  return 6.0f;   }
        else if (material == Material.DEEPSLATE_EMERALD_ORE)    {  return 15.0f;  }
        else if (material == Material.DEEPSLATE_DIAMOND_ORE)    {  return 5.0f;   }
        else if (material == Material.NETHER_GOLD_ORE)          {  return 9.0f;   }
        else if (material == Material.NETHER_QUARTZ_ORE)        {  return 6.0f;   }
        else if (material == Material.ANCIENT_DEBRIS)           {  return 20.0f;  }
        else if (material == Material.BEDROCK)                  {  return 100.0f; }
        return -1.0f;
    }

    private Material getAdditionalOre(Material material)
    {
        if (getLevel() >= 5)
        {
            if      (material == Material.COAL_ORE)     { return Material.COAL;     }
            else if (material == Material.IRON_ORE)     { return Material.RAW_IRON; }
            else if (material == Material.GOLD_ORE)     { return Material.RAW_GOLD; }
            else if (material == Material.COPPER_ORE)   { return Material.RAW_COPPER; }
        }

        if (getLevel() >= 10)
        {
            if      (material == Material.DIAMOND_ORE)            { return Material.DIAMOND;  }
            else if (material == Material.EMERALD_ORE)            { return Material.EMERALD;  }
            else if (material == Material.DEEPSLATE_COAL_ORE)     { return Material.COAL;     }
            else if (material == Material.DEEPSLATE_IRON_ORE)     { return Material.RAW_IRON; }
            else if (material == Material.DEEPSLATE_GOLD_ORE)     { return Material.RAW_GOLD; }
            else if (material == Material.DEEPSLATE_COPPER_ORE)   { return Material.RAW_GOLD; }
        }

        if (getLevel() >= 15)
        {
            if      (material == Material.DEEPSLATE_DIAMOND_ORE)   { return Material.DIAMOND;  }
            else if (material == Material.DEEPSLATE_EMERALD_ORE)   { return Material.EMERALD;  }
        }

        return null;
    }

    private double getChanceAdditionalOre(Material material)
    {
        // Chance = [0.0, 1.0]

        if (getLevel() == 5)
        {
            if      (material == Material.COAL_ORE)     { return 0.15; }
            else if (material == Material.IRON_ORE)     { return 0.15; }
            else if (material == Material.GOLD_ORE)     { return 0.15; }
            else if (material == Material.COPPER_ORE)   { return 0.15; }
        }

        if (getLevel() == 10)
        {
            if      (material == Material.COAL_ORE)               { return 0.20; }
            else if (material == Material.IRON_ORE)               { return 0.20; }
            else if (material == Material.GOLD_ORE)               { return 0.20; }
            else if (material == Material.COPPER_ORE)             { return 0.20; }
            else if (material == Material.DIAMOND_ORE)            { return 0.15; }
            else if (material == Material.EMERALD_ORE)            { return 0.15; }
            else if (material == Material.DEEPSLATE_COAL_ORE)     { return 0.15; }
            else if (material == Material.DEEPSLATE_IRON_ORE)     { return 0.15; }
            else if (material == Material.DEEPSLATE_GOLD_ORE)     { return 0.15; }
            else if (material == Material.DEEPSLATE_COPPER_ORE)   { return 0.15; }
        }

        if (getLevel() == 15)
        {
            if      (material == Material.COAL_ORE)               { return 0.25; }
            else if (material == Material.IRON_ORE)               { return 0.25; }
            else if (material == Material.GOLD_ORE)               { return 0.25; }
            else if (material == Material.COPPER_ORE)             { return 0.20; }
            else if (material == Material.DIAMOND_ORE)            { return 0.20; }
            else if (material == Material.EMERALD_ORE)            { return 0.20; }
            else if (material == Material.REDSTONE_ORE)           { return 0.20; }
            else if (material == Material.LAPIS_ORE)              { return 0.20; }
            else if (material == Material.DEEPSLATE_COAL_ORE)     { return 0.20; }
            else if (material == Material.DEEPSLATE_IRON_ORE)     { return 0.20; }
            else if (material == Material.DEEPSLATE_GOLD_ORE)     { return 0.20; }
            else if (material == Material.DEEPSLATE_COPPER_ORE)   { return 0.25; }
            else if (material == Material.DEEPSLATE_DIAMOND_ORE)  { return 0.15; }
            else if (material == Material.DEEPSLATE_EMERALD_ORE)  { return 0.18; }
            else if (material == Material.DEEPSLATE_REDSTONE_ORE) { return 0.20; }
            else if (material == Material.DEEPSLATE_LAPIS_ORE)    { return 0.20; }
        }

        if (getLevel() == 20)
        {
            if      (material == Material.COAL_ORE)               { return 0.30; }
            else if (material == Material.IRON_ORE)               { return 0.28; }
            else if (material == Material.GOLD_ORE)               { return 0.28; }
            else if (material == Material.COPPER_ORE)             { return 0.20; }
            else if (material == Material.DIAMOND_ORE)            { return 0.25; }
            else if (material == Material.EMERALD_ORE)            { return 0.25; }
            else if (material == Material.REDSTONE_ORE)           { return 0.20; }
            else if (material == Material.LAPIS_ORE)              { return 0.20; }
            else if (material == Material.DEEPSLATE_COAL_ORE)     { return 0.25; }
            else if (material == Material.DEEPSLATE_IRON_ORE)     { return 0.23; }
            else if (material == Material.DEEPSLATE_GOLD_ORE)     { return 0.23; }
            else if (material == Material.DEEPSLATE_COPPER_ORE)   { return 0.25; }
            else if (material == Material.DEEPSLATE_DIAMOND_ORE)  { return 0.20; }
            else if (material == Material.DEEPSLATE_EMERALD_ORE)  { return 0.20; }
            else if (material == Material.DEEPSLATE_REDSTONE_ORE) { return 0.20; }
            else if (material == Material.DEEPSLATE_LAPIS_ORE)    { return 0.20; }
        }

        return -1;
    }
}

