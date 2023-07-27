package org.vac.professionplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.vac.professionplugin.ProfessionManager;
import org.vac.professionplugin.professions.Profession;

public class GetProfessionCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("getprofesion"))
        {
            if (args.length > 1)
            {
                sender.sendMessage(ChatColor.RED + "Usar: /getprofesion");
                return true;
            }

            Player targetPlayer = (Player) sender;
            Profession playerProfession = ProfessionManager.getInstance().getDataBase().getPlayerProfession(targetPlayer);
            sender.sendMessage(ChatColor.GREEN + "Profesión de jugador " + targetPlayer.getName() + ": " +
                    playerProfession.getName() + " " +
                    "(Level " + playerProfession.getLevel() + ") (exp: " + playerProfession.getExp() +" / " + Profession.requiredExperience(playerProfession.getLevel()) + ")");

            return true;
        }

        return false;
    }
}
