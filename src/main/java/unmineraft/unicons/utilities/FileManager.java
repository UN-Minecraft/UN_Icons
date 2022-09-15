package unmineraft.unicons.utilities;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import unmineraft.unicons.UNIcons;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

public class FileManager {
    private final FileConfiguration config;
    private final ArrayList<String> pathSections = new ArrayList<>();
    private HashMap<String, File> fileReference = new HashMap<>();

    protected UNIcons plugin;
    protected Path pluginsFolder;

    public FileManager(UNIcons plugin){
        this.plugin = plugin;
        this.config = plugin.getConfig();
        this.pluginsFolder = plugin.getDataFolder().toPath().getParent().toAbsolutePath();
    }

    private String getFileSeparator(){
        String separatorPath = "config.separator";
        return this.config.getString(separatorPath);
    }

    private void getPathSections(){
        String pathSections = "config.path";
        ConfigurationSection sections = this.config.getConfigurationSection(pathSections);
        if (sections == null) return;

        this.pathSections.addAll(sections.getKeys(false));
    }

    private void getExternalFile(){
        this.getPathSections();

        String generalPath = "config.path.";

        for (String pluginName : this.pathSections){
            String additionalPath = this.config.getString(generalPath + pluginName);
            if (additionalPath == null) continue;

            String configExternalPath = this.pluginsFolder.toString();
            configExternalPath += additionalPath;

            System.out.println(configExternalPath);

            try {
                File file = new File(configExternalPath);
                this.fileReference.put(pluginName, file);
            } catch (Exception exception){
                System.out.println(exception.getMessage());
                break;
            }
        }
    }
}
