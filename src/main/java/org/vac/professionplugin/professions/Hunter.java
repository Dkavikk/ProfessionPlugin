package org.vac.professionplugin.professions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.vac.professionplugin.ProfessionManager;
import org.vac.professionplugin.custom_items.CustomAnimalTrackerItem;

import java.util.ArrayList;
import java.util.List;

import static org.vac.professionplugin.inventory.ProfessionInventoryController.createProfessionTypeItem;

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
    public void onBlockPlace(BlockPlaceEvent event)
    {

    }

    @Override
    public void onBlockGrow(BlockGrowEvent event)
    {

    }

    @Override
    public void onEntityDeath(EntityDeathEvent event)
    {
        // Obtener los datos de la entidad para la profesión
        EntityDataProfession entityDataProfession = ProfessionManager.getInstance().getDataBase().getEntityDataProfession(event.getEntity());

        // Verificar si la entidad pertenece a esta profesión y otorgar experiencia si es así
        if (entityDataProfession != null && belongToProfession(entityDataProfession))
        {
            increaseExperience(entityDataProfession.xpKill);
            ProfessionManager.getInstance().getDataBase().UpdateProfessionInDB(getPlayer(), this);
        }
    }

    @Override
    public void onEntityDamage(EntityDamageByEntityEvent event)
    {
        // Incrementar el daño de la entidad al ser dañada por otra entidad si el nivel es suficiente
        event.setDamage(event.getDamage() + getExtraDamageForLVL());
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event)
    {

    }

    @Override
    public void onPlayerShootBow(EntityShootBowEvent event)
    {
        // Aumentar la velocidad de las flechas disparadas por el jugador si el nivel es suficiente
        if (getLevel() >= 10)
        {
            event.getProjectile().setVelocity(event.getProjectile().getVelocity().multiply(1.5));
        }
    }

    @Override
    public void onEntityExplode(EntityExplodeEvent event)
    {}

    @Override
    public void onEntityBreed(EntityBreedEvent event)
    {
        // Obtener los datos de la entidad para la profesión
        EntityDataProfession entityDataProfession = ProfessionManager.getInstance().getDataBase().getEntityDataProfession(event.getEntity());

        // Verificar si la entidad pertenece a esta profesión y otorgar experiencia si es así
        if (entityDataProfession != null && belongToProfession(entityDataProfession) && event.getEntity().getType() == EntityType.WOLF)
        {
            // Posibilidad de que ocurra una cría adicional con el lobo
            if (Math.random() < 0.5)
            {
                event.getMother().getWorld().spawnEntity(event.getMother().getLocation(), event.getMother().getType());
            }

            increaseExperience(entityDataProfession.xpKill);
            ProfessionManager.getInstance().getDataBase().UpdateProfessionInDB(getPlayer(), this);
        }
    }

    @Override
    public void onCraftItem(CraftItemEvent event)
    {

    }

    @Override
    public Inventory getInventoryProfessionData()
    {
        // Descripción de la profesión
        List<String> lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "En las profundidades de los bosques ancestrales y las vastas llanuras,",
                ChatColor.WHITE + "se alza la figura del Cazador,",
                ChatColor.WHITE + "un maestro en el arte de la supervivencia y la caza.",
                ChatColor.WHITE + "Forjado en la soledad de la naturaleza y dotado",
                ChatColor.WHITE + "con la sabiduría ancestral de los guardianes de la tierra,",
                ChatColor.WHITE + "el Cazador se ha convertido en un ser letal y sigiloso"
        ));

        // Descripción de la recompensa
        List<String> descriptionLore = new ArrayList<>(List.of(
                ChatColor.WHITE + "Para poder subir de nivel y obtener experiencia,",
                ChatColor.WHITE + "tendrás que matar Mobs"
        ));

        // Recompensas para cada nivel
        List<String> rewardLvL5Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- 1 Corazón adicional."
        ));

        List<String> rewardLvL10Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- Tiro Preciso: aumenta la precisión de tus ataques a distancia."
        ));

        List<String> rewardLvL15Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- Item Rastreador de animales: este Item permite seleccionar un animal",
                ChatColor.WHITE + "para rastrearlo."
        ));

        List<String> rewardLvL20Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- 1 Corazón adicional."
        ));

        Inventory inventory = ProfessionManager.getInstance().getServer().createInventory(null, 9 * 3, ChatColor.GREEN + "" + ChatColor.BOLD + getName());

        ItemStack emptyItem = createProfessionTypeItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<>(), 1);
        ItemStack professionIcon = createProfessionTypeItem(Material.BOW, ChatColor.DARK_PURPLE + getName(), lore, 1);
        ItemStack description = createProfessionTypeItem(Material.ZOMBIE_HEAD, ChatColor.DARK_GREEN + "Descripción", descriptionLore, 1);
        ItemStack rewardLvL5 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 5", rewardLvL5Lore, 5);
        ItemStack rewardLvL10 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 10", rewardLvL10Lore, 10);
        ItemStack rewardLvL15 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 15", rewardLvL15Lore, 15);
        ItemStack rewardLvL20 = createProfessionTypeItem(Material.EXPERIENCE_BOTTLE, ChatColor.AQUA + "Recompensa de LvL 20", rewardLvL20Lore, 20);
        // ItemStack passiveRewardLvL5 = createProfessionTypeItem(Material.FIREWORK_STAR, ChatColor.DARK_AQUA + "Recompensa pasiva de LvL 5", new ArrayList<>());
        // ItemStack passiveRewardLvL10 = createProfessionTypeItem(Material.FIREWORK_STAR, ChatColor.DARK_AQUA + "Recompensa pasiva de LvL 10", new ArrayList<>());
        // ItemStack passiveRewardLvL15 = createProfessionTypeItem(Material.FIREWORK_STAR, ChatColor.DARK_AQUA + "Recompensa pasiva de LvL 15", new ArrayList<>());
        // ItemStack passiveRewardLvL20 = createProfessionTypeItem(Material.FIREWORK_STAR, ChatColor.DARK_AQUA + "Recompensa pasiva de LvL 20", new ArrayList<>());
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
        AttributeInstance maxHealthAttribute = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double increasedMaxHealth = maxHealthAttribute.getBaseValue() + 2.0;
        maxHealthAttribute.setBaseValue(increasedMaxHealth);
    }

    @Override
    public void level10Reward()
    {
        // TODO Insignia de profesión lvl 10
        getPlayer().sendMessage("Has conseguido la habilidad de Tiro Preciso");
    }

    @Override
    public void level15Reward()
    {
        // TODO Insignia de profesión lvl 15
        ItemStack customItem = new CustomAnimalTrackerItem();

        // Verificar si el inventario del jugador tiene espacio para el item
        if (getPlayer().getInventory().firstEmpty() != -1)
        {
            getPlayer().getInventory().addItem(customItem);
            getPlayer().sendMessage(ChatColor.YELLOW + "¡Has recibido un Rastreador de animales!");
        }
        else
        {
            // El inventario del jugador está lleno, dejar caer el item al suelo en su posición actual
            getPlayer().getWorld().dropItem(getPlayer().getLocation(), customItem);
            getPlayer().sendMessage(ChatColor.YELLOW + "Tu inventario está lleno. El Rastreador de animales ha sido dejado en el suelo.");
        }
    }

    @Override
    public void level20Reward()
    {
        // TODO Insignia de profesión lvl 20
        AttributeInstance maxHealthAttribute = getPlayer().getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double increasedMaxHealth = maxHealthAttribute.getBaseValue() + 2.0;
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
