package unmineraft.unicons.utilities;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import unmineraft.unicons.UNIcons;

public class Utilities {
    public static String translateColor(String baseString){
        return ChatColor.translateAlternateColorCodes('&', baseString);
    }

    public static void reloadTeams(UNIcons plugin){
        FileConfiguration config = plugin.getConfig();
        int minutes = config.getInt("config.reloadCooldownInMinutes");

        if (config == null) return;

        int ticksPerSecond = 20, secondsPerMinute = 60;
        long cooldown = (long) minutes * secondsPerMinute * ticksPerSecond;

        plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {

                if (plugin.getServer().getOnlinePlayers().size() == 0) return;

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tl reload");
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "nte reload");
            }
        }, 0L, cooldown);
    }
}
