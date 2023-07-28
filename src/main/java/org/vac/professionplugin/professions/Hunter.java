package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.*;
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
    public Inventory getInventoryProfessionData()
    {
        List<String> Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "En las profundidades de los bosques ancestrales y las vastas llanuras,",
                ChatColor.WHITE + "se alza la figura del Cazador,",
                ChatColor.WHITE + "un maestro en el arte de la supervivencia y la caza.",
                ChatColor.WHITE + "Forjado en la soledad de la naturaleza y dotado",
                ChatColor.WHITE + "con la sabiduría ancestral de los guardianes de la tierra,",
                ChatColor.WHITE + "el Cazador se ha convertido en un ser letal y sigiloso"
        ));

        List<String> descriptionLore = new ArrayList<>(List.of(
                ChatColor.WHITE + "Para poder subir de nivcel y obtener experiencia",
                ChatColor.WHITE + "tendras que matar Mobs"
        ));

        List<String> rewardLvL5Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- 1 Corazon adicional."
        ));

        List<String> rewardLvL10Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- Tiro Preciso: aumenta la precisión de tus ataques a distancia."
        ));

        List<String> rewardLvL15Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- Item Rastreador de animales: este Item permite selecionar un animal",
                ChatColor.WHITE + "para rastrearlo."
        ));

        List<String> rewardLvL20Lore = new ArrayList<>(List.of(
                ChatColor.WHITE + "- 1 Corazon adicional."
        ));
        Inventory inventory = ProfessionManager.getInstance().getServer().createInventory(null, 9*3, ChatColor.GREEN + "" + ChatColor.BOLD + getName());

        ItemStack emptyItem = createProfessionTypeItem(Material.BLACK_STAINED_GLASS_PANE, " ", new ArrayList<>());

        ItemStack professionIcon = createProfessionTypeItem(Material.BOW, ChatColor.DARK_PURPLE + getName(), Lore);
        ItemStack description = createProfessionTypeItem(Material.ZOMBIE_HEAD, ChatColor.WHITE + "Descripcion", descriptionLore);
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
