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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.vac.professionplugin.ProfessionManager;
import org.vac.professionplugin.inventory.LoreItemInventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.vac.professionplugin.inventory.ProfessionInventoryController.createProfessionTypeItem;

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

                if (getLevel() >= 5)
                {
                    duplicateItem(event, blockDataProfession);
                }

                if (blockDataProfession.allowedExtraExperience)
                {
                    getPlayer().giveExp(calculateExperienceByLVL());
                }

                ProfessionManager.getInstance().getDataBase().UpdateProfessionInDB(getPlayer(), this);
            }
        }
    }

    private void duplicateItem(BlockBreakEvent event, BlockDataProfession blockDataProfession)
    {
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
    public Inventory getInventoryProfessionData()
    {
        List<String> Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "Se rumorea que hace siglos,",
                ChatColor.WHITE + "un minero excepcional descubrió un mineral extremadamente raro y poderoso" ,
                ChatColor.WHITE + "en las profundidades de las minas." ,
                ChatColor.WHITE + "Desde entonces," ,
                ChatColor.WHITE + "los mineros aspiran a encontrar ese mineral legendario" ,
                ChatColor.WHITE + "para desbloquear su verdadero potencial." ,
                " ",
                " ",
                ChatColor.GREEN + "Beneficios pasivos:" ,
                ChatColor.GREEN + "Experiecia adicional al picar minerales," ,
                ChatColor.GREEN + "Probabilidad de obtener minerales extras"
        ));

        List<String> descriptionLore = new ArrayList<>(List.of(
                ChatColor.WHITE + "Para poder subir de nivcel y obtener experiencia",
                ChatColor.WHITE + "tendras que picar todo tipo de piedras y menerales"
        ));

        List<String> rewardLvL5Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "Minería Afortunada: Probabilidad de duplicar minerales"
        ));

        List<String> rewardLvL10Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "1 Corazon adicional",
                ChatColor.WHITE + "Riquezas Ocultas: probabilidad de encontrar minerales al picar piedra"
        ));

        List<String> rewardLvL15Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + ""
        ));

        List<String> rewardLvL20Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "Protección Subterránea: proporciona inmunidad temporal a daños de lava y explosiones mientras se está en minas."
        ));
        Inventory inventory = ProfessionManager.getInstance().getServer().createInventory(null, 9*3, ChatColor.BOLD + getName());

        ItemStack emptyItem = createProfessionTypeItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<>());

        ItemStack professionIcon = createProfessionTypeItem(Material.IRON_PICKAXE, ChatColor.DARK_PURPLE + "Minero", Lore);
        ItemStack description = createProfessionTypeItem(Material.IRON_PICKAXE, ChatColor.WHITE + "Descripcion", descriptionLore);
        ItemStack rewardLvL5 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 5", rewardLvL5Lore);
        ItemStack rewardLvL10 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 10", rewardLvL10Lore);
        ItemStack rewardLvL15 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 15", rewardLvL15Lore);
        ItemStack rewardLvL20 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 20", rewardLvL20Lore);
        // ItemStack passiveRewardLvL5 = createProfessionTypeItem(Material.FIREWORK_STAR, ChatColor.DARK_AQUA + "Recompensa pasiva de LvL 5", new ArrayList<>());
        // ItemStack passiveRewardLvL10 = createProfessionTypeItem(Material.FIREWORK_STAR, ChatColor.DARK_AQUA + "Recompensa pasiva de LvL 10", new ArrayList<>());
        // ItemStack passiveRewardLvL15 = createProfessionTypeItem(Material.FIREWORK_STAR, ChatColor.DARK_AQUA + "Recompensa pasiva de LvL 15", new ArrayList<>());
        // ItemStack passiveRewardLvL20 = createProfessionTypeItem(Material.FIREWORK_STAR, ChatColor.DARK_AQUA + "Recompensa pasiva de LvL 20", new ArrayList<>());
        ItemStack buttonAccept = createProfessionTypeItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN + "Aceptar", getName());
        ItemStack buttonCancel = createProfessionTypeItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED + "Cancelar", new ArrayList<>());

        inventory.setItem( 0, professionIcon);
        inventory.setItem( 1, emptyItem);
        inventory.setItem( 2, emptyItem);
        inventory.setItem( 3, rewardLvL5);
        inventory.setItem( 4, rewardLvL10);
        inventory.setItem( 5, rewardLvL15);
        inventory.setItem( 6, rewardLvL20);
        inventory.setItem( 7, emptyItem);
        inventory.setItem( 8, emptyItem);
        inventory.setItem( 9, description);
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
        // inventory.setItem(27, buttonCacel);
        // inventory.setItem(28, emptyItem);
        // inventory.setItem(29, emptyItem);
        // inventory.setItem(30, emptyItem);
        // inventory.setItem(31, emptyItem);
        // inventory.setItem(32, emptyItem);
        // inventory.setItem(33, emptyItem);
        // inventory.setItem(34, emptyItem);
        // inventory.setItem(35, buttonAccept);

        return inventory;
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

