package org.vac.professionplugin.professions;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;

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

        player.sendMessage("has resivido " + experienceGained + " de xp, ahora tu experiencia es de " + this.exp);
        // Incremento de experiencia requerida por nivel
        int experienceNextLevel = requiredExperience(this.level);
        if (this.exp >= experienceNextLevel)
        {
            this.level++;
            this.exp -= experienceNextLevel;
            this.levelUp = true;
            newLevel();
        }
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

    public abstract void performProfessionAction(BlockBreakEvent event);
    public abstract void performProfessionAction(EntityDeathEvent event);
    public abstract void newLevel();
    public abstract void startRepeatTasks();
    public abstract void Level5Reward();
    public abstract void Level10Reward();
    public abstract void Level15Reward();
    public abstract void Level20Reward();
}