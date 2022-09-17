package unmineraft.unicons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import unmineraft.unicons.UNIcons;
import unmineraft.unicons.teams.TeamsBuilder;
import unmineraft.unicons.utilities.Utilities;

public class ManageTeams implements CommandExecutor {
    private final UNIcons plugin;

    public ManageTeams(UNIcons plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        String errorMessage;
        if (!(sender instanceof Player)){
            errorMessage = Utilities.translateColor("&cNo puedes ejecutar comandos desde la consola");
            sender.sendMessage(this.plugin.name + errorMessage);
            return false;
        }

        Player player = (Player) sender;

        // Team
        if (args[0].equalsIgnoreCase("team")){
            // Set
            if (args[1].equalsIgnoreCase("set")){
                String teamName = args[2];

                if (teamName == null || teamName.equals("")){
                    errorMessage = Utilities.translateColor("&cNo has registrado un equipo");
                    player.sendMessage(errorMessage);
                    return false;
                }

                if (!TeamsBuilder.teams.containsKey(teamName)){
                    errorMessage = Utilities.translateColor("&cEquipo registrado invalido");
                    player.sendMessage(errorMessage);
                    return false;
                }

                // Group
                TeamsBuilder selectedTeam = TeamsBuilder.teams.get(teamName);
                boolean isAdded = selectedTeam.addMember(player);

                if (!isAdded){
                    errorMessage = Utilities.translateColor("&cYa estas en un equipo");
                    player.sendMessage(errorMessage);
                    return false;
                }

                String welcomeMessage = Utilities.translateColor("&aBienvenido al equipo " + selectedTeam.getName());
                player.sendMessage(welcomeMessage);
                return true;
            }

            errorMessage = Utilities.translateColor("&cSelección Invalida");
            player.sendMessage(errorMessage);
            return false;
        }
        return false;
    }
}
