package unmineraft.unicons.teams;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.PermissionNode;
import net.luckperms.api.node.types.SuffixNode;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import unmineraft.unicons.UNIcons;
import unmineraft.unicons.utilities.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class TeamsBuilder {
    public static ArrayList<String> ALL_GROUPS = new ArrayList<>();

    public static HashMap<UUID, String> PLAYER_TEAM = new HashMap<>();
    
    public static boolean isPlayerHaveTeam(Player player, String group){
        return player.hasPermission("group." + group);
    }

    public static String isPlayerHaveAnyTeam(Player player){
        for (String group : TeamsBuilder.ALL_GROUPS){
            if (TeamsBuilder.isPlayerHaveTeam(player, group)) return group;
        }
        return null;
    }

    private String name;
    private String suffix;
    private Group group;

    private final UNIcons plugin;
    private final LuckPerms luckPerms;

    // Constructors
    public TeamsBuilder(UNIcons plugin, String name){
        this.plugin = plugin;
        this.luckPerms = plugin.getLuckPermsApi();
        this.name = name;

        this.createTeam();

        // Add name to control variable
        if (this.name.equals("")) return;
        if (TeamsBuilder.ALL_GROUPS.contains(this.name)) return;

        TeamsBuilder.ALL_GROUPS.add(this.name);
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

    private void addNode(Node node, String successMessage){
        ConsoleCommandSender console = Bukkit.getConsoleSender();

        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            // It is not necessary to save the information, the modifyGroup method takes care of it
            this.luckPerms.getGroupManager().modifyGroup(this.name, group1 -> {
                group1.data().add(node);
            });
            console.sendMessage(this.plugin.name + Utilities.translateColor(successMessage));
        });
    }

    private void setTeamPrefix(){
        ConsoleCommandSender console = Bukkit.getConsoleSender();

        if (this.suffix == null || this.suffix.equals("")){
            console.sendMessage(this.plugin.name + Utilities.translateColor("&l&c The team has no suffix"));
            return;
        }

        // Create a suffix type node and add it to the group
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            SuffixNode node = SuffixNode.builder(this.suffix, 0).build();
            this.addNode(node, "&l&a New suffix is added");
        });
    }

    private void setTeamPermission(String permission){
        ConsoleCommandSender console = Bukkit.getConsoleSender();

        if (permission == null || permission.equals("")){
            console.sendMessage(this.plugin.name + Utilities.translateColor("&l&c Insert a valid permission"));
            return;
        }

        // Create a permission node and add it to the group
        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            PermissionNode node = PermissionNode.builder(permission).build();
            this.addNode(node, "&l&a New permission is added");
        });
    }

    private void addPlayer(Player player){
        if (this.name == null) return;

        Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
            UUID playerUUID = player.getUniqueId();
            User user = luckPerms.getUserManager().getUser(playerUUID);

            if (user == null) return;

            user.setPrimaryGroup(this.name);

            luckPerms.getUserManager().saveUser(user);
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

    public String getSuffix(){
        if (this.suffix != null) return this.suffix;
        return "";
    }

    public boolean setSuffix(String newSuffix){
        if (newSuffix != null){
            this.suffix = newSuffix;
            return true;
        }
        return false;
    }

    public boolean isMember(Player player){
        UUID playerId = player.getUniqueId();
        if (!TeamsBuilder.PLAYER_TEAM.containsKey(playerId)) return false;

        return TeamsBuilder.PLAYER_TEAM.get(playerId).equals(this.name);
    }

    public void addMember(Player player){
        this.addPlayer(player);
        TeamsBuilder.PLAYER_TEAM.put(player.getUniqueId(), this.name);
    }

    public void addSuffix(String suffix){
        this.setSuffix(suffix);
        this.setTeamPrefix();
    }

    public void addPermission(String permission){
        this.setTeamPermission(permission);
    }

    public void addPermissions(String[] permissions){
        for (String permission : permissions){
            this.addSuffix(permission);
        }
    }
}
