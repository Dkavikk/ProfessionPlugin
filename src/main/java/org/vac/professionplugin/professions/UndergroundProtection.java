package org.vac.professionplugin.professions;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.vac.professionplugin.ProfessionManager;

public class UndergroundProtection
{
    private static final int DURATION_SECONDS = 15; // Duración de la protección en segundos (ejemplo: 60 segundos).
    private static final int COOLDOWN_SECONDS = 10; // Tiempo de espera en segundos para volver a activar la habilidad.
    private final Player player;
    private boolean isActive;
    private boolean hasLavaResistance;
    private boolean isOnCooldown;

    public UndergroundProtection(Player player)
    {
        this.player = player;
        this.isActive = false;
        this.hasLavaResistance = false;
        this.isOnCooldown = false;
    }

    public void activate()
    {
        if (!isOnCooldown)
        {
            isActive = true;
            player.sendMessage("¡Has activado la Protección Subterránea! Estás inmune a daños de explosiones y lava por " + DURATION_SECONDS + " segundos.");
            //giveLavaResistance(); // Aplica la resistencia a la lava al activar la protección.

            // Desactiva la protección después del tiempo especificado.
            Bukkit.getScheduler().runTaskLater(ProfessionManager.getInstance(), () -> deactivate(), DURATION_SECONDS * 20L); // 20 ticks por segundo.

            // Inicia el cooldown para evitar que el jugador use la habilidad nuevamente durante el tiempo especificado.
            Bukkit.getScheduler().runTaskLater(ProfessionManager.getInstance(), () -> isOnCooldown = false, (COOLDOWN_SECONDS + DURATION_SECONDS) * 20L); // 20 ticks por segundo.
            isOnCooldown = true;
        }
    }

    public void deactivate()
    {
        isActive = false;
        //removeLavaResistance(); // Elimina la resistencia a la lava al desactivar la protección.
        player.sendMessage("La Protección Subterránea ha terminado. Ahora eres vulnerable a daños de explosiones.");
    }

    public boolean isActive()
    {
        return isActive;
    }

    public boolean hasLavaResistance()
    {
        return hasLavaResistance;
    }

    private void giveLavaResistance()
    {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, DURATION_SECONDS * 20, 0));
        hasLavaResistance = true;
    }

    private void removeLavaResistance()
    {
        player.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        hasLavaResistance = false;
    }
}
