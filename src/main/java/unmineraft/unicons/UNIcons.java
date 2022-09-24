package unmineraft.unicons;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import unmineraft.unicons.commands.ManageTeams;
import unmineraft.unicons.events.FriendlyFireEvent;
import unmineraft.unicons.teams.TeamsConfigConsumer;
import unmineraft.unicons.utilities.Utilities;

import java.io.File;
import java.util.Objects;

public final class UNIcons extends JavaPlugin {
    PluginDescriptionFile pdfile = this.getDescription();
    public String version = Utilities.translateColor("&a" + this.pdfile.getVersion());
    public String name = Utilities.translateColor("&e[&aUNIcons&e]");
    public String pathConfig;

    private LuckPerms api;

    public LuckPerms getLuckPermsApi(){
        return this.api;
    }

    public void eventsRegister(){
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new FriendlyFireEvent(this), this);
    }

    public void configRegister(){
        File config = new File(this.getDataFolder(), "config.yml");
        pathConfig = config.getPath();

        if (!config.exists()){
            this.getConfig().options().copyDefaults(true);
            saveConfig();
        }
    }

    public void commandRegister(){
        Objects.requireNonNull(this.getCommand("unico")).setExecutor(new ManageTeams(this));
    }

    @Override
    public void onEnable() {
        String initPluginMessage = Utilities.translateColor(this.name + "&r&f has been enabled in the version: " + this.version);
        Bukkit.getConsoleSender().sendMessage(initPluginMessage);

        // Load LuckPerms API
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider == null){
            Bukkit.getConsoleSender().sendMessage(this.name + Utilities.translateColor("&l&c Error: &rProvider LuckPerms not load"));
        }

        if (provider != null){
            this.api = provider.getProvider();
        }

        if (this.api == null) {
            Bukkit.getConsoleSender().sendMessage(this.name + Utilities.translateColor("&l&cError: API NOT LOAD"));
            return;
        }

        // Manage Events
        this.eventsRegister();

        // Config File Management
        this.saveDefaultConfig();
        this.configRegister();

        // Inicializate TeamsConsumer (This build the teams from config file)
        TeamsConfigConsumer teamsConfigConsumer = new TeamsConfigConsumer(this);
        teamsConfigConsumer.buildAllTeams(teamsConfigConsumer.groupsSection);

        // Manage Commands
        this.commandRegister();

        // Add Reload Task
        Utilities.reloadTeams(this);
    }

    @Override
    public void onDisable() {
        String endPluginMessage = ChatColor.translateAlternateColorCodes('&', this.name + "&r&c has been disabled");
        Bukkit.getConsoleSender().sendMessage(endPluginMessage);
    }
}
