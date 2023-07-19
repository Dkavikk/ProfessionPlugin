package org.vac.professionplugin;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.vac.professionplugin.professions.Profession;

import java.sql.*;
import java.util.UUID;



public class ProfessionDataBase
{
    private Connection connection;


    public void connectToDatabase()
    {
        // Establecer la conexión a la base de datos
        String databaseURL = "jdbc:mysql://localhost:3306/minecraft";
        String username = "root";
        String password = "Indiopicaro.1";

        try
        {
            connection = DriverManager.getConnection(databaseURL, username, password);
            Bukkit.getConsoleSender().sendMessage("[ProfessionPlugin] " + ChatColor.GREEN + "Connected to the database!");

        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to connect to the database: " + e.getMessage());
        }
    }

    public void disconnectFromDatabase()
    {
        try
        {
            // Cerrar la conexión a la base de datos
            if (connection != null && !connection.isClosed())
            {
                connection.close();
                Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Disconnected from the database!");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void setProfessionDB(Player player, String professionName)
    {

        try
        {
            // Guardar la profesión en la base de datos
            String query = "INSERT INTO player_professions (player_uuid, profession_name, profession_level, profession_exp) " +
                    "VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, player.getUniqueId().toString());
            statement.setString(2, professionName);
            statement.setInt(3, 1);
            statement.setFloat(4, 1);

            statement.executeUpdate();
            statement.close();

            player.sendMessage(ChatColor.GREEN + "Profesión establecida con éxito para el jugador " + player.getName() +
                    ": " + professionName);
        }
        catch (SQLException e)
        {
            player.sendMessage(ChatColor.RED + "Fallo al establecer profesion: " + e.getMessage());
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to set profession: " + e.getMessage());
        }
    }

    public Profession getPlayerProfession(Player player)
    {
        UUID playerUUID = player.getUniqueId();
        Profession profession = null;

        try
        {
            String professionName;
            int level;
            float exp;

            String query = "SELECT profession_name, profession_level, profession_exp " +
                    "FROM player_professions " +
                    "WHERE player_uuid = ?";

            // Obtener la profesión del jugador desde la base de datos
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, playerUUID.toString());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                professionName = resultSet.getString("profession_name");
                level = resultSet.getInt("profession_level");
                exp = resultSet.getInt("profession_exp");
                profession = Profession.getProfessionByName(professionName, level, exp, player);
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to get profession: " + e.getMessage());
            player.sendMessage(ChatColor.RED + "No tienes una profession");
        }

        return profession;
    }

    public void UpdateProfessionInDB(Player player, Profession profession)
    {
        try
        {
            String query = "UPDATE player_professions " +
                    "SET profession_level = ?, profession_exp = ? " +
                    "WHERE player_uuid = ? AND profession_name = ?";
            // Actualiza el nivel de la profesión en la base de datos
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, profession.getLevel());
            statement.setFloat(2, profession.getExp());
            statement.setString(3, player.getUniqueId().toString());
            statement.setString(4, profession.getName());

            if (profession.getLevelUp())
            {
                player.sendMessage(ChatColor.GREEN + "Has subido de nivel ");
                player.sendMessage(ChatColor.GREEN + "Ahora eres nivel: " + profession.getLevel());
            }

            statement.executeUpdate();
            statement.close();
        }
        catch (SQLException e)
        {
            player.sendMessage(ChatColor.RED + "No se pudo aumentar el nivel de profesión o exp: " + e.getMessage());
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "No se pudo aumentar el nivel de profesión o exp: " + e.getMessage());
        }
    }

    public void LeavingProfessionPlayer(Player player)
    {
        try
        {
            // Eliminar la información de la profesión del jugador en la base de datos
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM player_professions WHERE player_uuid = ?"
            );
            statement.setString(1, player.getUniqueId().toString());
            int rowsAffected = statement.executeUpdate();
            statement.close();

            if (rowsAffected > 0)
            {
                player.sendMessage(ChatColor.GREEN + "¡Has dejado tu profesión!");
            }
            else
            {
                player.sendMessage(ChatColor.RED + "¡No tienes una profesión que dejar!");
            }
            statement.close();
        }
        catch (SQLException e)
        {
            player.sendMessage(ChatColor.RED + "Failed to leave profession: " + e.getMessage());
        }
    }


    public BlockDataProfession getBlockDataForBlockName(String blockName)
    {
        BlockDataProfession blockDataProfession = null;

        try
        {
            String query = "SELECT " +
                    "MINER_PROFESSION, " +
                    "HUNTER_PROFESSION, " +
                    "C, " +
                    "D, " +
                    "E, " +
                    "XP_BREAK, " +
                    "XP_PLACE, " +
                    "ALLOWED_LUMINARITA_ELFICA, " +
                    "ALLOWED_DUPLICATE, " +
                    "ALLOWED_EXTRA_EXPERIENCE, " +
                    "MATERIAL_DUPLICATE, " +
                    "CHANCE_LVL5, CHANCE_LVL10, CHANCE_LVL15, CHANCE_LVL20 " +
                    "FROM block_data_profession " +
                    "WHERE material_name = ?";
            PreparedStatement statement = ProfessionManager.getInstance().getDataBase().getConnection().prepareStatement(query);
            statement.setString(1, blockName);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                blockDataProfession = new BlockDataProfession(
                        blockName,
                        resultSet.getBoolean("miner_profession"),
                        resultSet.getBoolean("hunter_profession"),
                        resultSet.getBoolean("c"),
                        resultSet.getBoolean("d"),
                        resultSet.getBoolean("e"),
                        resultSet.getFloat("xp_break"),
                        resultSet.getFloat("xp_place"),
                        resultSet.getBoolean("allowed_luminarita_elfica"),
                        resultSet.getBoolean("allowed_duplicate"),
                        resultSet.getBoolean("allowed_extra_experience"),
                        resultSet.getString("material_duplicate"),
                        resultSet.getDouble("chance_lvl5"),
                        resultSet.getDouble("chance_lvl10"),
                        resultSet.getDouble("chance_lvl15"),
                        resultSet.getDouble("chance_lvl20")
                );
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to get miner_profession: " + e.getMessage());
        }

        return blockDataProfession;
    }

    public HunterProfessionData getHunterProfessionData(@NotNull LivingEntity entity)
    {
        HunterProfessionData hunterProfessionData = null;
        try
        {
            String query = "SELECT xp, allowed_extra_experience, allowed_cooked, material_original, material_cooked " +
                           "FROM hunter_profession " +
                           "WHERE entity_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, entity.getType().name());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next())
            {
                hunterProfessionData = new HunterProfessionData(
                        entity.getType().name(),
                        resultSet.getFloat("xp"),
                        resultSet.getBoolean("allowed_extra_experience"),
                        resultSet.getBoolean("allowed_cooked"),
                        resultSet.getString("material_original"),
                        resultSet.getString("material_cooked")
                );
            }

            resultSet.close();
            statement.close();
        }
        catch (SQLException e)
        {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Failed to get hunter_profession: " + e.getMessage());
        }
        return hunterProfessionData;
    }




    public Connection getConnection()
    {
        return connection;
    }
}
