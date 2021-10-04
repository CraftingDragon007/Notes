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

public class SaveSong implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        Song song = new Song(args[0],sender.getName(),Instrument.valueOf(args[1]));
        int count = 2;
        while(count < args.length) {
            song.addNotes(Song.parseNote(args[count]));
            count++;
        }
        boolean save = true;
        for(Song song1 : Notes.songs){
            if (song1.getName().equalsIgnoreCase(song.getName())) {
                save = false;
                break;
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
