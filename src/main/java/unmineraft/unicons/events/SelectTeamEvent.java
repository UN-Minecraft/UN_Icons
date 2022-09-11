package unmineraft.unicons.events;

import com.nametagedit.plugin.NametagEdit;
import com.nametagedit.plugin.api.data.Nametag;
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

        plugin.UN.add(player);

        Nametag tag = NametagEdit.getApi().getNametag(player);

        player.sendMessage("TAG:" + tag.toString());
    }

    public SelectTeamEvent(UNIcons plugin){
        this.plugin = plugin;
    }
}
