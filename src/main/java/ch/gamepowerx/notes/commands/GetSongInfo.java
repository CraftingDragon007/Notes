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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class GetSongInfo implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
