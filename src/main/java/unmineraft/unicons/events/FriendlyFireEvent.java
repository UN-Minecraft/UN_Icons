package unmineraft.unicons.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import unmineraft.unicons.UNIcons;
import unmineraft.unicons.teams.TeamsBuilder;


public class FriendlyFireEvent implements Listener {
    private boolean disableFF = false;

    @EventHandler
    public void disabledFriendlyFire(EntityDamageByEntityEvent event){
        if (!this.disableFF) return;

        // Target
        if (!(event.getEntity() instanceof Player)) return;
        Player target = (Player) event.getEntity();

        // Damager
        Entity entityDamager = event.getDamager();
        if (!(entityDamager instanceof Player) && !(entityDamager instanceof Arrow)) {
            return;
        }

        Player damager = null;

        if (entityDamager instanceof Arrow){
            Arrow arrow = (Arrow) entityDamager;
            if (arrow.getShooter() instanceof Player){
                damager = (Player) arrow.getShooter();
            }
        }

        if (entityDamager instanceof Player){
            damager = (Player) event.getDamager();
        }

        if (damager == null) return;


        if (damager.isOp()) return;

        // Verify Teams
        String targetTeamName = TeamsBuilder.getPlayerTeam(target);
        String damagerTeamName = TeamsBuilder.getPlayerTeam(damager);

        // In case the players does not have a team
        if (targetTeamName == null ||damagerTeamName == null) return;

        // In case the players have different team
        if (!(targetTeamName.equals(damagerTeamName))) return;

        // In case the player have a same team
        event.setCancelled(true);
    }


    public FriendlyFireEvent(UNIcons plugin){
        String confBoolean = plugin.getConfig().getString("config.friendlyFire");
        if (confBoolean == null) return;

        this.disableFF = confBoolean.equalsIgnoreCase("false");
    }
}
