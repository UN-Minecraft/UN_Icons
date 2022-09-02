package unmineraft.unicons.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import unmineraft.unicons.UNIcons;
import unmineraft.unicons.teams.TeamsBuilder;

public class NewPlayerJoin implements Listener {
    private final UNIcons plugin;

    @EventHandler
    public void playerSelectTeam(PlayerJoinEvent event){
        Player player = event.getPlayer();

        if (TeamsBuilder.playerHasTeam(player)) return;

        if (this.plugin.un == null) return;

        if (this.plugin.un.add(player)) System.out.println("Agregado");
        player.sendMessage("Entraste a: " + this.plugin.un.getName());
    }

    public NewPlayerJoin(UNIcons plugin){
        this.plugin = plugin;
    }
}
