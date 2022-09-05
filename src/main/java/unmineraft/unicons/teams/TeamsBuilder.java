package unmineraft.unicons.teams;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.node.types.PrefixNode;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import unmineraft.unicons.UNIcons;
import unmineraft.unicons.utilities.Utilities;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class TeamsBuilder {
    private String name;
    private String prefix;
    private Group group;

    private final UNIcons plugin;
    private final LuckPerms luckPerms;

    // Constructors
    public TeamsBuilder(UNIcons plugin, String name){
        this.plugin = plugin;
        this.luckPerms = plugin.getLuckPermsApi();
        this.name = name;

        this.createTeam();
    }

    public TeamsBuilder(UNIcons plugin, String name, String prefix){
        this.plugin = plugin;
        this.luckPerms = plugin.getLuckPermsApi();
        this.name = name;
        this.prefix = prefix;

        this.createTeam();
        this.setTeamPrefix();
    }

    // Private Methods
    private void saveGroup(){
        if (this.group == null) return;

        // Save a group data in storage provider
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            CompletableFuture<Void> saveFuture = this.luckPerms.getGroupManager().saveGroup(this.group);
            saveFuture.join();
        });
    }

    private void createTeam(){
        ConsoleCommandSender console = Bukkit.getConsoleSender();

        if (this.name == null || this.name.equals("")){
            console.sendMessage(this.plugin.name + Utilities.translateColor("&l&c The team has no name"));
            return;
        }

        // Create and load group
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            CompletableFuture<Group> groupFuture = this.luckPerms.getGroupManager().createAndLoadGroup(this.name);
            this.group = groupFuture.join();

            // Check if there are any exceptions
            if (this.group instanceof CompletionException){
                this.group = null;
                return;
            }
            String confirmationMessage = this.plugin.name + Utilities.translateColor("&a New team created: " + this.group.getName());
            console.sendMessage(confirmationMessage);
        });
        saveGroup();
    }

    private void setTeamPrefix(){
        ConsoleCommandSender console = Bukkit.getConsoleSender();

        if (this.prefix == null || this.prefix.equals("")){
            console.sendMessage(this.plugin.name + Utilities.translateColor("&l&c The team has no prefix"));
            return;
        }

        // Create a prefix type node and add it to the group
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            PrefixNode node = PrefixNode.builder(this.prefix, 1).build();

            // It is not necessary to save the information, the modifyGroup method takes care of it
            this.luckPerms.getGroupManager().modifyGroup(this.name, group1 -> {
                group1.data().add(node);
            });
            console.sendMessage(this.plugin.name + Utilities.translateColor("&l&a New prefix is added"));
        });
    }

    // Public Methods
    public String getName(){
        if (this.name != null) return this.name;
        return "";
    }
    public boolean setName(String newName){
        if (newName != null){
            this.name = newName;
            return true;
        }
        return false;
    }

    public String getPrefix(){
        if (this.prefix != null) return this.prefix;
        return "";
    }

    public boolean setPrefix(String newPrefix){
        if (newPrefix != null){
            this.prefix = newPrefix;
            return true;
        }
        return false;
    }
}
