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

package ch.gamepowerx.notes.tabcompleter;

import ch.gamepowerx.notes.Song;
import org.bukkit.Instrument;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayNotes implements TabCompleter {
    private final List<String> completer = new ArrayList<>();
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        completer.clear();
        if(args.length==1){
            completer.add("Test");
        }else if(args.length==2){
            for(Instrument instrument : Instrument.values()){
                completer.add(instrument.name());
            }
        }else if(args.length>=3){
            completer.add("-5T");
            completer.add("-1S");
            completer.add("0G");
            completer.add("0G#");
            completer.add("0A");
            completer.add("0A#");
            completer.add("0B");
            completer.add("0B#");
            completer.add("0C");
            completer.add("0C#");
            completer.add("0D");
            completer.add("0D#");
            completer.add("0E");
            completer.add("0E#");
            completer.add("0F");
            completer.add("0F#");
            completer.add("1G");
            completer.add("1G#");
            completer.add("1A");
            completer.add("1A#");
            completer.add("1B");
            completer.add("1B#");
            completer.add("1C");
            completer.add("1C#");
            completer.add("1D");
            completer.add("1D#");
            completer.add("1E");
            completer.add("1E#");
            completer.add("1F");
            completer.add("1F#");
            //completer.add("2F#");
        }
        if(args.length>=4){
            if(sender instanceof Player) {
                try {
                    Song song = new Song("Test", sender.getName(), Instrument.valueOf(args[1]), Song.parseNote(args[args.length - 2]));
                    song.playSong((Player) sender);
                }catch (Exception ignored){
                }
            }
        }
        return completer;
    }
}
