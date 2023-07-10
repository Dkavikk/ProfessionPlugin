package org.vac.professionplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.vac.professionplugin.ProfessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SetProfessionCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(@NotNull CommandSender sender,Command command,@NotNull String label, String[] args)
    {
        Connection connection = ProfessionManager.getConnection();
        if (command.getName().equalsIgnoreCase("setprofesion"))
        {
            if (args.length > 1)
            {
                sender.sendMessage(ChatColor.RED + "Usar: /setprofesion");
                return true;
            }

            Player targetPlayer = (Player) sender;

            try
            {
                // Verificar si el jugador ya tiene una profesión en la base de datos
                PreparedStatement checkStatement = connection.prepareStatement(
                        "SELECT COUNT(*) FROM player_professions WHERE player_uuid = ?"
                );
                checkStatement.setString(1, targetPlayer.getUniqueId().toString());
                ResultSet checkResult = checkStatement.executeQuery();
                checkResult.next();
                int count = checkResult.getInt(1);
                checkStatement.close();

                if (count > 0)
                {
                    sender.sendMessage(ChatColor.RED + "¡Ya tiene una profesión!");
                    return true;
                }

                //ProfessionManager.getInstance().openProfessionInventory(targetPlayer);
                ProfessionManager.getInstance().getInventoryController().openSetProfessioInventory(targetPlayer);
            }
            catch (SQLException e)
            {
                sender.sendMessage(ChatColor.RED + "Failed to set profession: " + e.getMessage());
            }

            return true;
        }
        return false;
    }
}
