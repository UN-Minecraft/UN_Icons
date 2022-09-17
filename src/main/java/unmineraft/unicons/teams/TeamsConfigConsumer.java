package unmineraft.unicons.teams;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import unmineraft.unicons.UNIcons;

import java.util.ArrayList;
import java.util.List;

public class TeamsConfigConsumer {
    private final UNIcons plugin;
    private final FileConfiguration config;
    public ArrayList<String> groupsSection;

    public TeamsConfigConsumer(UNIcons plugin){
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.groupsSection = this.getTeamsSection();
    }

    protected ArrayList<String> getTeamsSection(){
        ArrayList<String> teamsSection = new ArrayList<>();

        ConfigurationSection configGroupSection = this.config.getConfigurationSection("groups");

        if (configGroupSection == null) return teamsSection;

        teamsSection.addAll(configGroupSection.getKeys(false));
        return teamsSection;
    }

    public TeamsBuilder buildTeam(String groupSectionName){
        TeamsBuilder team = new TeamsBuilder(this.plugin, groupSectionName);

        String pathGroup = "groups." + groupSectionName + ".";

        // Get Permissions and add it
        String permissionsPath = pathGroup + "general.permissions";
        List<String> permissions = this.config.getStringList(permissionsPath);

        if (permissions != null) {
            team.addPermissions(permissions);
        } else {
            Bukkit.getConsoleSender().sendMessage("ERROR_10: INVALID PATH");
        }

        // Get Suffix and add it
        String suffixPath = pathGroup + "scoreboard.work_name";
        String suffix = this.config.getString(suffixPath);

        if (suffix != null){
            team.addSuffix(suffix);
        } else {
            Bukkit.getConsoleSender().sendMessage("ERROR_20: INVALID PATH");
        }

        return team;
    }

    public void buildAllTeams(ArrayList<String> groupsSections){
        for (String groupName : groupsSections){
            TeamsBuilder team = this.buildTeam(groupName);
            if (TeamsBuilder.teams.containsKey(groupName)) continue;

            TeamsBuilder.teams.put(groupName, team);
        }
    }
}
