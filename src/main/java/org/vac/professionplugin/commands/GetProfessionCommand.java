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

            Player player = (Player) sender;
            Profession playerProfession = ProfessionManager.getInstance().getDataBase().getPlayerProfession(player);
            if (playerProfession != null)
            {
                sender.sendMessage(ChatColor.GREEN + "Profesión de jugador " + player.getName() + ": " +
                        playerProfession.getName() + " " +
                        "(Level " + playerProfession.getLevel() + ") (exp: " + playerProfession.getExp() +" / " + Profession.requiredExperience(playerProfession.getLevel()) + ")");
            }
            else
            {
                player.sendMessage(ChatColor.RED + "No tienes una profesion");
            }

            return true;
        }

        return false;
    }
}
