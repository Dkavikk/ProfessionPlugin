package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.vac.professionplugin.ProfessionManager;

import java.util.*;

import static org.vac.professionplugin.inventory.ProfessionInventoryController.createProfessionTypeItem;

public class Miner extends Profession
{
    public Miner(int level, float exp, Player player)
    {
        super("Minero", level, exp, player);
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event)
    {
        //        if (getPlayer().getLocation().getBlock().getType() == Material.LAVA)
        //        {
        //            UndergroundProtection protection = ProfessionManager.getInstance().getPlayerUndergroundProtectionMap().get(getPlayer());
        //            protection.activate();
        //        }
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event)
    {
        Block block = event.getBlock();
        BlockDataProfession blockDataProfession = ProfessionManager.getInstance().getDataBase().getBlockDataForBlockName(block.getType().name());

        if (blockDataProfession != null && belongToProfession(blockDataProfession))
        {
            increaseExperience(blockDataProfession.xpBreak);

            if (getLevel() >= 5)
            {
                duplicateItem(event, blockDataProfession);
            }

            if (getLevel() >= 10 && blockDataProfession.hiddenRiches)
            {
                double chanceOfTreasure = getAdjustedChanceOfTreasure(getPlayer().getInventory().getItemInMainHand());
                if (Math.random() <= chanceOfTreasure)
                {
                    Material treasure = getMaterial();
                    block.setType(treasure);
                    event.getPlayer().sendMessage("¡Has encontrado un tesoro enterrado de " + treasure.name() + "!");
                }
            }

            if (blockDataProfession.allowedExtraExperience)
            {
                getPlayer().giveExp(calculateExperienceByLVL());
            }

            ProfessionManager.getInstance().getDataBase().UpdateProfessionInDB(getPlayer(), this);
        }
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event)
    {
        LivingEntity entity = event.getEntity();
        EntityDataProfession entityDataProfession = ProfessionManager.getInstance().getDataBase().getEntityDataProfession(entity);

        if (entityDataProfession != null && belongToProfession(entityDataProfession))
        {
            increaseExperience(entityDataProfession.xpKill);
            ProfessionManager.getInstance().getDataBase().UpdateProfessionInDB(getPlayer(), this);
        }
    }

    @Override
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event)
    {
        if (getLevel() >= 20)
        {
            if ((event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) ||
                    (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) ||
                    (event.getCause() == EntityDamageEvent.DamageCause.FIRE) ||
                    (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK))
            {
                UndergroundProtection protection = ProfessionManager.getInstance().getPlayerUndergroundProtectionMap().get(getPlayer());
                protection.activate();
                if (protection.isActive())
                {
                    event.setCancelled(true);
                }
            }
        }
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
    public Inventory getInventoryProfessionData()
    {
        List<String> Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "Se rumorea que hace siglos,",
                ChatColor.WHITE + "un minero excepcional descubrió un mineral extremadamente raro y poderoso",
                ChatColor.WHITE + "en las profundidades de las minas.",
                ChatColor.WHITE + "Desde entonces,",
                ChatColor.WHITE + "los mineros aspiran a encontrar ese mineral legendario",
                ChatColor.WHITE + "para desbloquear su verdadero potencial."
        ));

        List<String> descriptionLore = new ArrayList<>(List.of(
                ChatColor.WHITE + "Para poder subir de nivel y obtener experiencia",
                ChatColor.WHITE + "tendrás que picar todo tipo de piedras y minerales"
        ));

        List<String> rewardLvL5Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- Minería Afortunada: Probabilidad de duplicar minerales."
        ));

        List<String> rewardLvL10Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- 1 Corazón adicional.",
                ChatColor.WHITE + "- Riquezas Ocultas: probabilidad de encontrar minerales al picar piedra."
        ));

        List<String> rewardLvL15Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "Ninguna"
        ));

        List<String> rewardLvL20Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- Protección Subterránea: proporciona inmunidad temporal a daños de ",
                ChatColor.WHITE + "lava y explosiones mientras."
        ));
        Inventory inventory = ProfessionManager.getInstance().getServer().createInventory(null, 9 * 3, ChatColor.GREEN + "" + ChatColor.BOLD + getName());

        ItemStack emptyItem = createProfessionTypeItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<>(), 1);
        ItemStack professionIcon = createProfessionTypeItem(Material.IRON_PICKAXE, ChatColor.DARK_PURPLE + "Minero", Lore, 1);
        ItemStack description = createProfessionTypeItem(Material.STONE, ChatColor.WHITE + "Descripción", descriptionLore, 1);
        ItemStack rewardLvL5 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 5", rewardLvL5Lore, 5);
        ItemStack rewardLvL10 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 10", rewardLvL10Lore, 10);
        ItemStack rewardLvL15 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 15", rewardLvL15Lore, 15);
        ItemStack rewardLvL20 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 20", rewardLvL20Lore, 20);
        ItemStack buttonAccept = createProfessionTypeItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Aceptar", getName(), 1);
        ItemStack buttonCancel = createProfessionTypeItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Cancelar", new ArrayList<>(), 1);

        inventory.setItem(0, professionIcon);
        inventory.setItem(1, emptyItem);
        inventory.setItem(2, emptyItem);
        inventory.setItem(3, rewardLvL5);
        inventory.setItem(4, rewardLvL10);
        inventory.setItem(5, rewardLvL15);
        inventory.setItem(6, rewardLvL20);
        inventory.setItem(7, emptyItem);
        inventory.setItem(8, emptyItem);
        inventory.setItem(9, description);
        inventory.setItem(10, emptyItem);
        inventory.setItem(11, emptyItem);
        inventory.setItem(12, emptyItem);
        inventory.setItem(13, emptyItem);
        inventory.setItem(14, emptyItem);
        inventory.setItem(15, emptyItem);
        inventory.setItem(16, emptyItem);
        inventory.setItem(17, emptyItem);
        inventory.setItem(18, buttonCancel);
        inventory.setItem(19, emptyItem);
        inventory.setItem(20, emptyItem);
        inventory.setItem(21, emptyItem);
        inventory.setItem(22, emptyItem);
        inventory.setItem(23, emptyItem);
        inventory.setItem(24, emptyItem);
        inventory.setItem(25, emptyItem);
        inventory.setItem(26, buttonAccept);

        return inventory;
    }

    @Override
    public void level5Reward()
    {
        // TODO Insignia de profesión lvl 5
    }

    @Override
    public void level10Reward()
    {
        // TODO Insignia de profesión lvl 10
    }

    @Override
    public void level15Reward()
    {
        // TODO Insignia de profesión lvl 15
    }

    @Override
    public void level20Reward()
    {
        // TODO Insignia de profesión lvl 20
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

    private static Material getMaterial()
    {
        Map<Material, Double> TREASURE_PROBABILITIES = new HashMap<>();
        TREASURE_PROBABILITIES.put(Material.DIAMOND_ORE, 0.3);  // Probabilidad del 30% de diamantes
        TREASURE_PROBABILITIES.put(Material.GOLD_ORE, 0.2);     // Probabilidad del 20% de oro
        TREASURE_PROBABILITIES.put(Material.IRON_ORE, 0.25);    // Probabilidad del 25% de hierro
        TREASURE_PROBABILITIES.put(Material.EMERALD_ORE, 0.15); // Probabilidad del 15% de esmeraldas
        TREASURE_PROBABILITIES.put(Material.LAPIS_ORE, 0.1);    // Probabilidad del 10% de lapislázuli

        Random random = new Random();
        double r = random.nextDouble();
        double cumulativeProbability = 0.0;

        for (Map.Entry<Material, Double> entry : TREASURE_PROBABILITIES.entrySet())
        {
            cumulativeProbability += entry.getValue();
            if (r <= cumulativeProbability)
            {
                return entry.getKey();
            }
        }

        return Material.IRON_ORE;
    }

    private double getAdjustedChanceOfTreasure(ItemStack item)
    {
        int fortuneLevel = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);

        // La probabilidad aumenta en un 10% por cada nivel de "Fortuna"
        double chanceIncreasePerFortuneLevel = 0.1;

        double adjustedChance = 0.1 + (fortuneLevel * chanceIncreasePerFortuneLevel);

        // Limitar la probabilidad máxima a 100%
        if (adjustedChance > 1.0)
        {
            adjustedChance = 1.0;
        }

        return adjustedChance;
    }

    private void duplicateItem(BlockBreakEvent event, BlockDataProfession blockDataProfession)
    {
        if (blockDataProfession.allowedDuplicate)
        {
            Material material = Material.getMaterial(blockDataProfession.materialDuplicate);
            Random random = new Random();
            if (material != null && random.nextDouble() <= getChance(blockDataProfession))
            {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(material));
            }
        }
    }

    private void UndergroundProtection()
    {
    }

    private ItemStack CreateLuminaritaElfica()
    {
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta meta = item.getItemMeta();

        String description = "Mineral extremadamente raro y poderoso" + "se desconoce su uso, pero se siente su poder";
        List<String> lore = new ArrayList<>();
        lore.add(description);

        Objects.requireNonNull(meta).setDisplayName(ChatColor.DARK_PURPLE + "Luminarita Elfica");
        Objects.requireNonNull(meta).setLore(lore);
        Objects.requireNonNull(meta).setCustomModelData(1);

        item.setItemMeta(meta);

        return item;
    }
}