package ch.gamepowerx.notes.commands;

import ch.gamepowerx.notes.Notes;
import ch.gamepowerx.notes.Song;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlaySavedSong implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length==1)
        for(Song song : Notes.songs){
            if(song.getName().equalsIgnoreCase(args[0])){
                if(sender instanceof Player)
                    song.playSong(((Player) sender));
            }
        }
        if(args.length==2){
            Player target = Bukkit.getPlayer(args[1]);
            if(sender.hasPermission("notes.play.other"))
            if(target!=null){
                for(Song song : Notes.songs){
                    if(song.getName().equalsIgnoreCase(args[0])){
                        song.playSong(target);
                    }
                }
            }
        }
        return true;
    }
}
