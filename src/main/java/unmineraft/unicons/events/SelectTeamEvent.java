package unmineraft.unicons.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import unmineraft.unicons.UNIcons;

public class SelectTeamEvent implements Listener {
    protected UNIcons plugin;

    @EventHandler
    public void playerSelect(PlayerJoinEvent event){
        Player player = event.getPlayer();
    }

    public SelectTeamEvent(UNIcons plugin){
        this.plugin = plugin;
    }
}
