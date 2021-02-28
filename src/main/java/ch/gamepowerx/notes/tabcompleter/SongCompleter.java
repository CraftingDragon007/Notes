package ch.gamepowerx.notes.tabcompleter;

import ch.gamepowerx.notes.Notes;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.List;

public class SongCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if(args.length==1) {
            return (List<String>) Notes.songs.getSongNames();
        }return Collections.emptyList();
    }
}
