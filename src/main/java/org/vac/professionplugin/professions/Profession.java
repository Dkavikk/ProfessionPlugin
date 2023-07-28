package org.vac.professionplugin.professions;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

public abstract class Profession {
    private final String name;
    private int level;
    private float exp;
    private boolean levelUp;

    private final Player player;

    // Constantes
    private static final int BASE_EXPERIENCE = 200; // Experiencia requerida para el nivel 1
    private static final int INCREASE_EXPERIENCE = 250; // Incremento de experiencia requerida por nivel

    public Profession(String name, int level, float exp, Player player)
    {
        this.name = name;
        this.level = level;
        this.exp = exp;
        this.player = player;
    }

    public String getName()
    {
        return name;
    }
    public int getLevel()
    {
        return level;
    }
    public float getExp()
    {
        return exp;
    }
    public boolean getLevelUp()
    {
        return levelUp;
    }
    public Player getPlayer()
    {
        return player;
    }



    // Método para calcular la experiencia requerida para alcanzar un nivel dado
    public static int requiredExperience(int level)
    {
        return BASE_EXPERIENCE + (level - 1) * INCREASE_EXPERIENCE;
    }

    public void increaseExperience(float experienceGained)
    {
        this.exp += experienceGained;

        // Incremento de experiencia requerida por nivel
        int experienceNextLevel = requiredExperience(this.level);
        if (this.exp >= experienceNextLevel)
        {
            this.level++;
            this.exp -= experienceNextLevel;
            this.levelUp = true;
            newLevel();
        }
        player.sendMessage("has resivido " + experienceGained + " de xp, ahora tu experiencia es de " + this.exp);
    }

    public static Profession getProfessionByName(String name, int level, float exp ,Player player)
    {
        if (name.equals("Minero"))
        {
            return new Miner(level, exp, player);
        }
        else if (name.equals("Cazador"))
        {
            return new Hunter(level, exp, player);
        }
        else
        {
            return null;
        }
    }

    public void newLevel()
    {
        // TODO Add money economy system

        player.sendMessage(ChatColor.GREEN + "Has subido de nivel ");
        player.sendMessage(ChatColor.GREEN + "Ahora eres nivel: " + level);

        if (getLevel() == 5)
        {
            level5Reward();
        }

        if (getLevel() == 10)
        {
            level10Reward();
        }

        if (getLevel() == 15)
        {
            level15Reward();
        }

        if (getLevel() == 20)
        {
            level20Reward();
        }
    }

    public abstract Inventory getInventoryProfessionData();

    public abstract void onBlockBreak(BlockBreakEvent event);
    public abstract void onEntityDeath(EntityDeathEvent event);
    public abstract void onEntityDamage(EntityDamageByEntityEvent event);
    public abstract void onPlayerShootBow(EntityShootBowEvent event);
    public abstract void onEntityBreed(EntityBreedEvent event);

    public abstract void level5Reward();
    public abstract void level10Reward();
    public abstract void level15Reward();
    public abstract void level20Reward();
    public abstract void leaveProfession();

    public boolean belongToProfession(BlockDataProfession data)
    {
        if (data.allowedMiner && Objects.equals(name, "Minero"))
        {
            return true;
        }
        else if (data.allowedHunter && Objects.equals(name, "Cazador"))
        {
            return true;
        }
        else if (data.allowedC && Objects.equals(name, "c"))
        {
            return true;
        }
        else if (data.allowedD && Objects.equals(name, "d"))
        {
            return true;
        }
        else if (data.allowedE && Objects.equals(name, "e"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean belongToProfession(EntityDataProfession data)
    {
        if (data.allowedMiner && Objects.equals(name, "Minero"))
        {
            return true;
        }
        else if (data.allowedHunter && Objects.equals(name, "Cazador"))
        {
            return true;
        }
        else if (data.allowedC && Objects.equals(name, "c"))
        {
            return true;
        }
        else if (data.allowedD && Objects.equals(name, "d"))
        {
            return true;
        }
        else if (data.allowedE && Objects.equals(name, "e"))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}