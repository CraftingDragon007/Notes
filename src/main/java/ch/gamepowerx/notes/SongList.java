package ch.gamepowerx.notes;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SongList extends ArrayList<Song> {
    public SongList(Song... songs){
        this.addAll(Arrays.asList(songs));
    }

    public SongList(){

    }

    @Nullable
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

    @NotNull
    public boolean containsSong(String name){
        for(Song song : this){
            if(song.getName().equalsIgnoreCase("name")){
                return true;
            }
        }
        return false;
    }

    @NotNull
    public Collection<String> getSongNames(){
        Collection<String> songNames = new ArrayList<>();
        for(Song song : this){
            songNames.add(song.getName());
        }
        return songNames;
    }
}
