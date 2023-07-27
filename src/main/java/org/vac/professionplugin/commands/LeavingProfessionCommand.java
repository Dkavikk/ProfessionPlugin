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
        Connection connection = ProfessionManager.getConnection();

        if (command.getName().equalsIgnoreCase("salirprofesion"))
        {
            if (!(sender instanceof Player))
            {
                sender.sendMessage(ChatColor.RED + "This command can only be executed by a player.");
                return true;
            }

            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();

            try
            {
                // Eliminar la información de la profesión del jugador en la base de datos
                PreparedStatement statement = connection.prepareStatement(
                        "DELETE FROM player_professions WHERE player_uuid = ?"
                );
                statement.setString(1, playerUUID.toString());
                int rowsAffected = statement.executeUpdate();
                statement.close();

                if (rowsAffected > 0)
                {
                    sender.sendMessage(ChatColor.GREEN + "You have left your profession!");
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "You don't have a profession to leave!");
                }
            }
            catch (SQLException e)
            {
                sender.sendMessage(ChatColor.RED + "Failed to leave profession: " + e.getMessage());
            }
            return true;
        }

        return false;
    }
}
