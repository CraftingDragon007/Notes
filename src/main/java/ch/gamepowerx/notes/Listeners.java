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

package ch.gamepowerx.notes;

import org.bukkit.Material;
import org.bukkit.block.data.type.NoteBlock;
import org.bukkit.block.data.type.Repeater;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static ch.gamepowerx.notes.Notes.scans;

public class Listeners implements Listener {
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(scans.containsKey(player)){
            if(event.getClickedBlock() != null){
                Song song = scans.get(player);
                if(event.getClickedBlock().getType().equals(Material.NOTE_BLOCK)){
                    event.setCancelled(true);
                    NoteBlock block = (NoteBlock) event.getClickedBlock().getBlockData();
                    player.playNote(player.getLocation(), song.getInstrument(), block.getNote());
                    song.addNotes(block.getNote());
                    player.sendMessage("§aNote hinzugefügt! Ton: §6" + block.getNote().toString().replace("Note{","").replace("}", ""));
                }else if(event.getClickedBlock().getType().equals(Material.REPEATER)){
                    event.setCancelled(true);
                    Repeater repeater = (Repeater) event.getClickedBlock().getBlockData();
                    song.addNotes(Song.parseNote("-" + repeater.getDelay() * 2 + "T"));
                    player.sendMessage("§aPause hinzugefügt! Länge: §6" + (repeater.getDelay() * 2) + " Ticks");
                }
            }
        }
    }
}
