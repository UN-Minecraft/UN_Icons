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
        String playerMessage;
        if (!(sender instanceof Player)){
            playerMessage = Utilities.translateColor("&cNo puedes ejecutar comandos desde la consola");
            sender.sendMessage(this.plugin.name + playerMessage);
            return false;
        }

        Player player = (Player) sender;

        String message = args[0] + " " + args[1];

        if (message.equals("team set")){
            if (args.length < 3){
                playerMessage = Utilities.translateColor("&cComando incompleto");
                player.sendMessage(playerMessage);
                return false;
            }
            String teamName = args[2];

            if (teamName.equals("") || !(TeamsBuilder.teams.containsKey(teamName))){
                playerMessage = Utilities.translateColor("&cGrupo desconocido, registro: " + teamName);
                player.sendMessage(playerMessage);
                return false;
            }

            TeamsBuilder team = TeamsBuilder.teams.get(teamName);

            team.addMember(player);
            playerMessage = Utilities.translateColor("&aBienvenido al equipo " + team.getName());
            player.sendMessage(playerMessage);
            return true;

        }

        if (message.equals("team get")){
            player.sendMessage("Equipos Almacenados en Teams: " + TeamsBuilder.teams.keySet());
            player.sendMessage("Equipos Almacenados en ALL_GROUPS: " + TeamsBuilder.ALL_GROUPS);
        }

        playerMessage = Utilities.translateColor("&cComando desconocido");
        player.sendMessage(playerMessage);
        return false;
    }
}
