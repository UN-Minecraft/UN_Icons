package unmineraft.unicons;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import unmineraft.unicons.events.NewPlayerJoin;
import unmineraft.unicons.utilities.Utilities;

public final class UNIcons extends JavaPlugin {
    PluginDescriptionFile pdfile = this.getDescription();

    public LuckPerms api;

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

        // Load LuckPerms api
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null){
            Bukkit.getConsoleSender().sendMessage(Utilities.translateColor("&l&cError: &rApi LuckPerms not load"));
        }

        if (provider != null){
            this.api = provider.getProvider();
        }

        // Manage events
        eventsRegister();
    }

    @Override
    public void onDisable() {
        String endPluginMessage = ChatColor.translateAlternateColorCodes('&', name + "&r&c has been disabled");
        Bukkit.getConsoleSender().sendMessage(endPluginMessage);
    }
}
