package org.vac.professionplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.vac.professionplugin.ProfessionManager;

public class SetProfessionCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender,Command command,@NotNull String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("setprofesion"))
        {
            if (args.length > 1)
            {
                sender.sendMessage(ChatColor.RED + "Usar: /setprofesion");
                return true;
            }

            Player targetPlayer = (Player) sender;
            ProfessionManager.getInstance().getInventoryController().openSetProfessionInventory(targetPlayer);

            return true;
        }
        return false;
    }
}
