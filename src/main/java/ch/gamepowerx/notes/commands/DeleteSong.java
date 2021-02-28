package ch.gamepowerx.notes.commands;

import ch.gamepowerx.notes.Notes;
import ch.gamepowerx.notes.Song;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DeleteSong implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==1){
            if(Notes.songs.containsSong(args[0])){
                Notes.songs.remove(Notes.songs.getSongByName(args[0]));
                sender.sendMessage("§aDer Song wurde erfolgreich §cgelöscht§a!");
            }else sender.sendMessage("§cDer Song konnte nicht gefunden werden!");
        }else sender.sendMessage("§cBitte verwende: §6/deletesong <Name>");
        return true;
    }
}
