package unmineraft.unicons.utilities;

import org.bukkit.configuration.file.FileConfiguration;
import unmineraft.unicons.UNIcons;

import java.nio.file.Path;

public class FileManager {
    public String fileSeparator;

    private FileConfiguration config;

    protected UNIcons plugin;
    protected Path pluginsFolder;

    public FileManager(UNIcons plugin){
        this.plugin = plugin;
        this.pluginsFolder = plugin.getDataFolder().toPath().getParent();
        this.config = plugin.getConfig();
    }


}
