package unmineraft.unicons.events;

import org.bukkit.event.Listener;
import unmineraft.unicons.UNIcons;

public class NewPlayerJoin implements Listener {
    private final UNIcons plugin;

    public NewPlayerJoin(UNIcons plugin){
        this.plugin = plugin;
    }
}
