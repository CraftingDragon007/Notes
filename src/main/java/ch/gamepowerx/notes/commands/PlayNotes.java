package ch.gamepowerx.notes.commands;

import ch.gamepowerx.notes.Song;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayNotes implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            //Song song = new Song("Play",player.getName(),Instrument.PIANO,Song.parseNoteList(args[0],true));
            Song song = new Song(args[0],sender.getName(),Instrument.valueOf(args[1]));
            int count = 2;
            while(count < args.length) {
                song.addNotes(Song.parseNote(args[count]));
                count++;
            }
            song.playSong(player);
        }
        return true;
    }
}
