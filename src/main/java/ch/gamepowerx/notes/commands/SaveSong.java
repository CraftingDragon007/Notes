package ch.gamepowerx.notes.commands;

import ch.gamepowerx.notes.Notes;
import ch.gamepowerx.notes.Song;
import org.bukkit.Instrument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SaveSong implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Song song = new Song(args[0],sender.getName(),Instrument.valueOf(args[1]));
        int count = 2;
        while(count < args.length) {
            song.addNotes(Song.parseNote(args[count]));
            count++;
        }
        /*
        if(args[2].equalsIgnoreCase("true")) {
            song = new Song(args[0], sender.getName(), Instrument.PIANO, Song.parseNoteList(args[1], true));
        }else if (args[2].equalsIgnoreCase("false")) {
            song = new Song(args[0], sender.getName(), Instrument.PIANO, Song.parseNoteList(args[1], false));
        }else {
            return false
         */
        boolean save = true;
        for(Song song1 : Notes.songs){
            if(song1.getName().equalsIgnoreCase(song.getName())){
                save = false;
            }
        }
        if(save)
        Notes.songs.add(song);
        if(!save)
            sender.sendMessage("Der Song konnte nicht gespeichert werden: Ein Song mit dem gleichen Namen ist schon vorhanden!");
        if(save)
            if(sender instanceof Player)
                song.playSong((Player) sender);
        return true;
    }
}
