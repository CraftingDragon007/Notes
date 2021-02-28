package ch.gamepowerx.notes.tabcompleter;

import ch.gamepowerx.notes.Notes;
import ch.gamepowerx.notes.Song;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlaySavedSong implements TabCompleter {
    private final List<String> completer = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        completer.clear();
        if(args.length==1){
            for(Song song : Notes.songs){
                completer.add(song.getName());
            }
        }else if(args.length==2){
            for(Player player : Bukkit.getOnlinePlayers()){
                completer.add(player.getName());
            }
        }
        return completer;
    }
}
