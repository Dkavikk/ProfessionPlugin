package org.vac.professionplugin.inventory;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class LoreItemInventory
{
    // TODO refactorizar el retorno, para que sea una lista
    public static List<String> LORE_MINER_PROFESSION = new ArrayList<>(List.of(
     ChatColor.WHITE + "Se rumorea que hace siglos,",
               ChatColor.WHITE + "un minero excepcional descubrió un mineral extremadamente raro y poderoso" ,
               ChatColor.WHITE + "en las profundidades de las minas." ,
               ChatColor.WHITE + "Desde entonces," ,
               ChatColor.WHITE + "los mineros aspiran a encontrar ese mineral legendario" ,
               ChatColor.WHITE + "para desbloquear su verdadero potencial." ,
                                 " ",
                                 " ",
               ChatColor.GREEN + "Beneficios pasivos:" ,
               ChatColor.GREEN + "Experiecia adicional al picar minerales," ,
               ChatColor.GREEN + "Probabilidad de obtener minerales extras"
    ));


    ;
    public static String LORE_HUNTER_PROFESSION =
    ChatColor.WHITE + "En las profundidades de los bosques ancestrales y las vastas llanuras, \n" +
                      "se alza la figura del Cazador, \n" +
                      "un maestro en el arte de la supervivencia y la caza. \n" +
                      "Forjado en la soledad de la naturaleza y dotado \n" +
                      "con la sabiduría ancestral de los guardianes de la tierra, \n" +
                      "el Cazador se ha convertido en un ser letal y sigiloso." +
                      "\n"+
                      "\n"+
    ChatColor.GREEN + "Beneficios por nivel: \n" +
                      "LVL  5: 1 Corazon adicional, \n" +
                      "LVL 10: Maya precision al disparar con arco, \n" +
                      "LVL 15: 1 Corazon adicional, \n" +
                      "LVL 20: Objeto para buscar animales, \n" +
                      "\n"+
    ChatColor.GREEN + "Beneficios por nivel: \n" +
                      "LVL  5: 1 Corazon adicional, \n" +
                      "LVL 10: Maya precision al disparar con arco, \n" +
                      "LVL 15: 1 Corazon adicional, \n" +
                      "LVL 20: Objeto para buscar animales, \n";

}
