package unmineraft.unicons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import unmineraft.unicons.events.NewPlayerJoin;
import unmineraft.unicons.teams.TeamsBuilder;
import unmineraft.unicons.utilities.Utilities;

public final class UNIcons extends JavaPlugin {
    PluginDescriptionFile pdfile = this.getDescription();
    public TeamsBuilder un;

    public String version = Utilities.translateColor("&a" + pdfile.getVersion());
    public String name = Utilities.translateColor("&e[&aUNIcons&e]");

    public void eventsRegister(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new NewPlayerJoin(this), this);
    }

    @Override
    public void onEnable() {
        String initPluginMessage = Utilities.translateColor(name + "&r&f has been enabled in the version: " + version);
        Bukkit.getConsoleSender().sendMessage(initPluginMessage);

        String prefix = Utilities.translateColor("&l&a[UN]");
        un = new TeamsBuilder("Universidad Nacional de Colombia", prefix);

        eventsRegister();
    }

    @Override
    public void onDisable() {
        String endPluginMessage = ChatColor.translateAlternateColorCodes('&', name + "&r&c has been disabled");
        Bukkit.getConsoleSender().sendMessage(endPluginMessage);
    }
}
