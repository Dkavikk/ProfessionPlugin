package org.vac.professionplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.vac.professionplugin.ProfessionManager;
import org.vac.professionplugin.professions.Profession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetProfessionCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, @NotNull String label, String[] args)
    {
        Connection connection = ProfessionManager.getConnection();

        if (command.getName().equalsIgnoreCase("getprofesion"))
        {
            if (args.length > 1)
            {
                sender.sendMessage(ChatColor.RED + "Usar: /getprofesion");
                return true;
            }

            Player targetPlayer = (Player) sender;

            try
            {
                // Obtener la profesión del jugador desde la base de datos
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT profession_name, profession_level, profession_exp FROM player_professions WHERE player_uuid = ?"
                );
                statement.setString(1, targetPlayer.getUniqueId().toString());
                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next())
                {
                    String professionName = resultSet.getString("profession_name");
                    int level = resultSet.getInt("profession_level");
                    float exp = resultSet.getInt("profession_exp");

                    sender.sendMessage(ChatColor.GREEN + "Profesión de jugador " + targetPlayer.getName() + ": " +
                            professionName + " (Level " + level + ") (exp: " + exp +" / " + Profession.requiredExperience(level) + ")");
                }
                else
                {
                    sender.sendMessage(ChatColor.RED + "No profession found for player " + targetPlayer.getName());
                }

                resultSet.close();
                statement.close();
            }
            catch (SQLException e)
            {
                sender.sendMessage(ChatColor.RED + "Failed to get profession: " + e.getMessage());
            }
            return true;
        }

        return false;
    }
}
