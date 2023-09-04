package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.vac.professionplugin.ProfessionManager;

import java.util.ArrayList;
import java.util.List;

import static org.vac.professionplugin.inventory.ProfessionInventoryController.createProfessionTypeItem;

public class Farmer extends Profession
{
    public Farmer(int level, float exp, Player player)
    {
        super("Granjero", level, exp, player);
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
        ItemStack professionIcon = createProfessionTypeItem(Material.IRON_PICKAXE, ChatColor.DARK_PURPLE + "Granjero", Lore, 1);
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
    public void onPlayerMove(PlayerMoveEvent event)
    {

    }

    @Override
    public void onBlockBreak(BlockBreakEvent event)
    {

    }

    @Override
    public void onBlockPlace(BlockPlaceEvent event)
    {
        if (getLevel() >= 10)
        {
            if (event.getBlock().getBlockData() instanceof Ageable)
            {
                event.getBlock().setMetadata("PlantOwner", new FixedMetadataValue(ProfessionManager.getInstance(), getPlayer().getName()));
            }
        }
    }

    @Override
    public void onBlockGrow(BlockGrowEvent event)
    {
        Block block = event.getBlock();
        double growthSpeed = 5;

        // Cancelamos el evento de crecimiento y programamos el crecimiento nuevamente con la velocidad ajustada.
        event.setCancelled(true);
        int growthTicks = (int) Math.ceil(1 / growthSpeed); // Convertimos la velocidad a ticks.
        Bukkit.getScheduler().scheduleSyncDelayedTask(ProfessionManager.getInstance(), () ->
        {
            Ageable ageable = (Ageable) block.getBlockData();
            if (ageable.getAge() == ageable.getMaximumAge())
            {
                block.setType(block.getType()); // Establecemos el tipo del bloque para refrescar el crecimiento.
            }
            else
            {
                if (ageable.getAge() + 2 >  ageable.getMaximumAge())
                {
                    ageable.setAge(ageable.getMaximumAge());
                    block.setBlockData(ageable, true);
                }
                else
                {
                    ageable.setAge(ageable.getAge() + 2);
                    block.setBlockData(ageable, true);
                }
            }
        }, growthTicks);
    }

    @Override
    public void onEntityDeath(EntityDeathEvent event)
    {

    }

    @Override
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {

    }

    @Override
    public void onEntityDamage(EntityDamageEvent event)
    {

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
    public void level5Reward()
    {

    }

    @Override
    public void level10Reward()
    {

    }

    @Override
    public void level15Reward()
    {

    }

    @Override
    public void level20Reward()
    {

    }

    @Override
    public void leaveProfession()
    {

    }
}
