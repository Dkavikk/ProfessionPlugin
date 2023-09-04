package org.vac.professionplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vac.professionplugin.custom_items.CustomAnimalTrackerItem;

public class CommandsTest implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender,Command command, String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("test"))
        {
            Player player = (Player) sender;

            if (!player.isOp())
            {
                sender.sendMessage(ChatColor.RED + "Que wea asi mierda metia!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Que wea asi mierda metia!");
                Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "El " + player.getName() + " trato de usar el comando /test");
                return true;
            }
            player.getInventory().addItem(new CustomAnimalTrackerItem());

            player.sendMessage(ChatColor.YELLOW + "¡Has recibido un Rastreador de animales!");

            return true;
        }

        return false;
    }
}
