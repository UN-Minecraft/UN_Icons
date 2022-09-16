package unmineraft.unicons.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import unmineraft.unicons.UNIcons;
import unmineraft.unicons.teams.TeamsBuilder;

import java.util.UUID;

public class FriendlyFireEvent implements Listener {
    private boolean disableFF = false;

    @EventHandler
    public void disabledFriendlyFire(EntityDamageByEntityEvent event){
        if (!this.disableFF) return;

        // Target
        if (!(event.getEntity() instanceof Player)) return;
        Player target = (Player) event.getEntity();

        // Damager
        if (!(event.getDamager() instanceof  Player)) return;
        Player damager = (Player) event.getDamager();

        // Verify Teams
        UUID targetID = target.getUniqueId();
        UUID damagerID = damager.getUniqueId();

        // In case the players does not have a team
        if (!(TeamsBuilder.PLAYER_TEAM.containsKey(targetID))) return;
        if (!(TeamsBuilder.PLAYER_TEAM.containsKey(damagerID))) return;

        // In case the players have different team
        String targetTeam = TeamsBuilder.PLAYER_TEAM.get(targetID);
        String damagerTeam = TeamsBuilder.PLAYER_TEAM.get(damagerID);

        if (!(targetTeam.equals(damagerTeam))) return;

        // In case the player have a same team
        event.setCancelled(true);
    }


    public FriendlyFireEvent(UNIcons plugin){
        String confBoolean = plugin.getConfig().getString("config.friendlyFire");
        if (confBoolean == null) return;

        this.disableFF = confBoolean.equalsIgnoreCase("false");
    }
}
