package unmineraft.unicons.teams;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class TeamsBuilder {
    private static final List<TeamsBuilder> ALL_TEAMS = new ArrayList<>();
    private static final HashMap<UUID, String> playersRegisterAllTeams = new HashMap<>();
    private Team team;

    protected String name;
    protected String prefix;

    public static List<TeamsBuilder> getTeamsList(){
        return new ArrayList<>(TeamsBuilder.ALL_TEAMS);
    }

    public static boolean playerHasTeam(Player player){
        return playersRegisterAllTeams.containsKey(player.getUniqueId());
    }

    private static Scoreboard getScoreboard(){
        ScoreboardManager manager = Bukkit.getScoreboardManager();

        if (manager == null) return null;

        return manager.getMainScoreboard();
    }

    public TeamsBuilder(String teamName, String prefixTeam){
        if (teamName == null || prefixTeam == null){
            return;
        }

        this.name = teamName;
        this.prefix = prefixTeam;

        Scoreboard scoreboard = TeamsBuilder.getScoreboard();
        if (scoreboard == null) {
            return;
        }

        this.team = scoreboard.registerNewTeam(this.name);
        this.team.setPrefix(this.prefix);

        this.team.setAllowFriendlyFire(false);
        this.team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
        this.team.setCanSeeFriendlyInvisibles(false);

        TeamsBuilder.ALL_TEAMS.add(this);
    }

    public String getName(){
        return this.name;
    }

    public String getPrefix(){
        return this.prefix;
    }

    public boolean add(Player player){
        UUID idPlayer = player.getUniqueId();
        if (playersRegisterAllTeams.containsKey(idPlayer)) return false;

        try {
            playersRegisterAllTeams.put(idPlayer, this.getName());
            this.team.addEntry(player.getName());
        } catch (Exception error){
            System.out.println("Error: " + error.getMessage());
            return false;
        }
        return true;
    }

    public boolean remove(Player player){
        UUID idPlayer = player.getUniqueId();

        if (!playersRegisterAllTeams.containsKey(idPlayer)) return false;

        playersRegisterAllTeams.remove(idPlayer);

        try {
            this.team.removeEntry(player.getName());
        } catch (Exception error){
            System.out.println("Error: " + error.getMessage());
            return false;
        }

        return true;
    }
}
