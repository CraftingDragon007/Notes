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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SongList extends ArrayList<Song> {
    public SongList(Song... songs){
        this.addAll(Arrays.asList(songs));
    }

    public Song getSongByName(String name){
        if(containsSong(name)){
            for(Song song : this){
                if(song.getName().equalsIgnoreCase(name)){
                    return song;
                }
            }
        }
        return null;
    }

    public boolean containsSong(String name){
        for(Song song : this){
            if(song.getName().equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }

    public Collection<String> getSongNames(){
        Collection<String> songNames = new ArrayList<>();
        for(Song song : this){
            songNames.add(song.getName());
        }
        return songNames;
    }
}
