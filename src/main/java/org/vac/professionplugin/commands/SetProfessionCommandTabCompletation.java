package org.vac.professionplugin.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SetProfessionCommandTabCompletation implements TabCompleter
{

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command,String s, String[] strings)
    {
        if (command.getName().equalsIgnoreCase("setprofesion"))
        {
            if (strings.length == 1)
            {
                List<String> professionsNames;
                professionsNames = new ArrayList<>();
                professionsNames.add("Minero");
                professionsNames.add("Cazador");

                return professionsNames;
            }
        }
        return null;
    }
}