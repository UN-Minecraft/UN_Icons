package unmineraft.unicons.events;

import com.nametagedit.plugin.NametagEdit;
import com.nametagedit.plugin.api.data.INametag;
import com.nametagedit.plugin.api.events.NametagFirstLoadedEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import unmineraft.unicons.UNIcons;
import unmineraft.unicons.teams.TeamsBuilder;

public class NameTagFirstLoadEvent implements Listener {
    protected UNIcons plugin;

    @EventHandler
    public void firstLoadPlayerTag(NametagFirstLoadedEvent event) {
        System.out.println("EVENTO TAG");
        if (event.getPlayer() == null) return;
        Player player = event.getPlayer();

        // Check if the player is in any group. In case it has a group, put a prefix
        String groupName = TeamsBuilder.isPlayerHaveAnyTeam(player);
        if (groupName != null){
            INametag nameTag = event.getNametag();
            String prefix = nameTag.getPrefix();

            // Avoid the null pointer exception
            if (!TeamsBuilder.PREFIX_GROUPS.containsKey(groupName)) return;
            String groupPrefix = TeamsBuilder.PREFIX_GROUPS.get(groupName);

            // Validate if the existing prefix already contains the icon
            if (prefix.contains(groupPrefix)) return;

            // Otherwise, set the icon in prefix
            NametagEdit.getApi().setPrefix(player, groupPrefix);
            player.sendMessage(groupPrefix);
        }
    }

    public NameTagFirstLoadEvent(UNIcons plugin){
        this.plugin = plugin;
    }
}
