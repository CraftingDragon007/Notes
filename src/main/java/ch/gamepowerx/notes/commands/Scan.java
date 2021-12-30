/*
 *       Notes is a Minecraft Plugin that adds the ability to create digitized Noteblock Songs
 *                  Copyright (C) 2021 CraftingDragon007
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ch.gamepowerx.notes.commands;

import ch.gamepowerx.notes.Notes;
import ch.gamepowerx.notes.Song;
import org.bukkit.Instrument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Scan implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            switch (args[0]) {
                case "create" :
                    if(args.length==3) {
                        List<String> names = new ArrayList<>();
                        for(Song song : Notes.songs){
                            names.add(song.getName());
                        }
                        Song song = new Song(args[1], sender.getName(), Instrument.valueOf(args[2]));
                        if(names.contains(song.getName())){
                            sender.sendMessage("§cEs existiert bereits ein Song mit diesem Namen!");
                            return true;
                        }
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
                            sender.sendMessage("§aScan wurde erfolgreich umbenannt!");
                        } else {
                            sender.sendMessage("§cDu hast keinen Scan erstellt!");
                        }
                    }else sender.sendMessage("§cBitte verwende: §6/scan rename <Name>");
                    break;
                case "cancel" :
                    if (Notes.scans.containsKey(((Player) sender).getPlayer())) {
                        Notes.scans.remove(sender);
                        sender.sendMessage("§aScan wurde erfolgreich§c abgebrochen§a!");
                    } else {
                        sender.sendMessage("§cDu hast keinen Scan erstellt!");
                    }
            }
        }
        return true;
    }
}
