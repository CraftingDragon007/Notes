package ch.gamepowerx.notes.commands;

import ch.gamepowerx.notes.Notes;
import ch.gamepowerx.notes.Song;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class GetSongInfo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Song song = null;
        for(Song s : Notes.songs){
            if(s.getName().equals(args[0])){
                song = s;
            }
        }
        if(song==null){
            song = Notes.songs.get(0);
        }
        sender.sendMessage("-----------------------------------------------------");
        sender.sendMessage("Name: "+song.getName());
        sender.sendMessage("Author: "+song.getAuthor());
        sender.sendMessage("Instrument: "+song.getInstrument().name());
        StringBuilder stringBuilder = new StringBuilder();
        for(String s : Song.parseNoteStrings(song.getNoteList())){
            stringBuilder.append(s);
            stringBuilder.append(' ');
        }
        sender.sendMessage("Noten: "+stringBuilder);
        sender.sendMessage("-----------------------------------------------------");
        return true;
    }
}
