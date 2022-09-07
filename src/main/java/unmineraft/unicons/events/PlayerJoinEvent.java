package unmineraft.unicons.events;

import com.nametagedit.plugin.api.events.NametagFirstLoadedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import unmineraft.unicons.teams.TeamsBuilder;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public void firstLoadPlayerTag(NametagFirstLoadedEvent event) {
        if (event.getPlayer() == null) return;
        Player player = event.getPlayer();

        // Check if the player is in any group. In case it has a group, the tag is not modified
        if (TeamsBuilder.isPlayerHaveAnyTeam(player)){
            return;
        }
    }
}
