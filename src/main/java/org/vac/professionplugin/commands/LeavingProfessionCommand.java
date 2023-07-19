package org.vac.professionplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.vac.professionplugin.ProfessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class LeavingProfessionCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("salirprofesion"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
                return true;
            }
            ProfessionManager.getInstance().getDataBase().LeavingProfessionPlayer((Player) sender);
            return true;
        }

        return false;
    }
}
