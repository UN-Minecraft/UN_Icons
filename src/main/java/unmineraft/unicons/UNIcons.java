package unmineraft.unicons;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import unmineraft.unicons.events.SelectTeamEvent;
import unmineraft.unicons.utilities.Utilities;

public final class UNIcons extends JavaPlugin {
    PluginDescriptionFile pdfile = this.getDescription();
    public String version = Utilities.translateColor("&a" + this.pdfile.getVersion());
    public String name = Utilities.translateColor("&e[&aUNIcons&e]");

    private LuckPerms api;

    public LuckPerms getLuckPermsApi(){
        return this.api;
    }

    public void eventsRegister(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new SelectTeamEvent(this), this);
    }

    @Override
    public void onEnable() {
        String initPluginMessage = Utilities.translateColor(this.name + "&r&f has been enabled in the version: " + this.version);
        Bukkit.getConsoleSender().sendMessage(initPluginMessage);

        // Load LuckPerms api
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null){
            Bukkit.getConsoleSender().sendMessage(this.name + Utilities.translateColor("&l&c Error: &rApi LuckPerms not load"));
        }

        if (provider != null){
            this.api = provider.getProvider();
        }

        if (this.api == null) return;


        eventsRegister();
    }

    @Override
    public void onDisable() {
        String endPluginMessage = ChatColor.translateAlternateColorCodes('&', this.name + "&r&c has been disabled");
        Bukkit.getConsoleSender().sendMessage(endPluginMessage);
    }
}
