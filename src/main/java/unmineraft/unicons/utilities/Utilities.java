package unmineraft.unicons.utilities;

import org.bukkit.ChatColor;

public class Utilities {
    public static String translateColor(String baseString){
        return ChatColor.translateAlternateColorCodes('&', baseString);
    }
}
