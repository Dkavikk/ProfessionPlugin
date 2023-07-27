package org.vac.professionplugin.commands;

import org.vac.professionplugin.ProfessionManager;
import org.vac.professionplugin.professions.Profession;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ProfessionCommands implements CommandExecutor
{
    @Override
    public boolean onCommand(@NotNull CommandSender sender,Command command,@NotNull String label, String[] args)
    {


        return false;
    }
}
