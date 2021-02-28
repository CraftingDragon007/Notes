package ch.gamepowerx.notes.commands;

import ch.gamepowerx.notes.Notes;
import ch.gamepowerx.notes.Song;
import org.bukkit.Instrument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Scan implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            switch (args[0]) {
                case "create" :
                    if(args.length==3) {
                        List<String> names = new ArrayList<>();
                        for(Song song : Notes.songs){
                            names.add(song.getName());
                        }
                        Song song = new Song(args[1], sender.getName(), Instrument.valueOf(args[2]));
                        Notes.scans.put(((Player) sender).getPlayer(), song);
                        sender.sendMessage("§aScan erstellt bitte klicke alle Notenblöcke und Repeater an und gebe dann §6/scan stop");
                        break;
                    }else sender.sendMessage("§cBitte verwende: §6/scan create <Name> <Instrument>");
                case "stop" :
                    if(Notes.scans.containsKey(((Player) sender).getPlayer())){
                        Notes.songs.add(Notes.scans.remove(sender));
                        sender.sendMessage("§aScan wurde erfolgreich beendet!");
                    }else {
                        sender.sendMessage("§cDu hast keinen Scan erstellt!");
                    }
                    break;
                case "rename" :
                    if(args.length==2) {
                        if (Notes.scans.containsKey(((Player) sender).getPlayer())) {
                            Notes.scans.get(sender).setName(args[1]);
                            sender.sendMessage("§aScan wurde erfolgreich umbennant!");
                        } else {
                            sender.sendMessage("§cDu hast keinen Scan erstellt!");
                        }
                    }else sender.sendMessage("§cBitte verwende: §6/scan rename <Name>");
                    break;
                case "cancel" :
                    if (Notes.scans.containsKey(((Player) sender).getPlayer())) {
                        Notes.scans.remove(sender);
                        sender.sendMessage("§aScan wurde erfolgreich §cabgebrochen§a!");
                    } else {
                        sender.sendMessage("§cDu hast keinen Scan erstellt!");
                    }
            }
        }
        return true;
    }
}
