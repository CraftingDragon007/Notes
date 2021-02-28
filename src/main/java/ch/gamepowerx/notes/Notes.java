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

import ch.gamepowerx.notes.commands.*;
import ch.gamepowerx.notes.tabcompleter.SongCompleter;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Notes extends JavaPlugin {

    public static SongList songs;
    public static HashMap<Player, Song> scans = new HashMap<>();
    public static FileConfiguration songConfig;

    @Override
    public void onEnable() {
        // Plugin startup logic
        songs = new SongList();
        loadSavedSongs();
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        getCommand("playnotes").setExecutor(new PlayNotes());
        getCommand("playnotes").setTabCompleter(new ch.gamepowerx.notes.tabcompleter.PlayNotes());
        getCommand("playsavedsong").setExecutor(new PlaySavedSong());
        getCommand("playsavedsong").setTabCompleter(new ch.gamepowerx.notes.tabcompleter.PlaySavedSong());
        getCommand("getsonginfo").setExecutor(new GetSongInfo());
        getCommand("getsonginfo").setTabCompleter(new SongCompleter());
        getCommand("savesong").setExecutor(new SaveSong());
        getCommand("savesong").setTabCompleter(new ch.gamepowerx.notes.tabcompleter.PlayNotes());
        getCommand("scan").setExecutor(new Scan());
        getCommand("deletesong").setExecutor(new DeleteSong());
        getCommand("deletesong").setTabCompleter(new SongCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveSavedSongs();
    }

    private void loadSavedSongs(){
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File file = new File(getDataFolder(), "songs.yml");
        if (!file.exists()) {
            try (InputStream in = this.getResource("songs.yml")) {
                assert in != null;
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        songConfig = new YamlConfiguration();
        try {
            songConfig.load(file);
        } catch (IOException|InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
        for(String songString : songConfig.getKeys(false)){
            Song song = new Song(songConfig.getString(songString+".Name"),songConfig.getString(songString+".Author"), Instrument.valueOf(songConfig.getString(songString+".Instrument")));
            for(String noteStr : songConfig.getStringList(songString+".Notes")){
                song.addNotes(Song.parseNote(noteStr));
            }
            songs.add(song);
        }
    }

    private void saveSavedSongs(){
        for(Song song : songs){
            songConfig.set(song.getName()+".Name",song.getName());
            List<String> noteStr = new ArrayList<>();
            for(Object o : song.getNoteList()){
                if(o instanceof Pause){
                    Pause pause = (Pause) o;
                    String isInTicks = null;
                    if(pause.isInTicks())
                        isInTicks = "T";
                    if(!pause.isInTicks())
                        isInTicks = "S";
                    noteStr.add("-"+pause.getDuration()+isInTicks);
                }else if(o instanceof Note){
                    Note note = (Note) o;
                    if(!note.isSharped())
                        noteStr.add(String.valueOf(note.getOctave())+note.getTone());
                    if(note.isSharped())
                        noteStr.add(String.valueOf(note.getOctave())+note.getTone()+"#");
                }
            }
            songConfig.set(song.getName()+".Notes",noteStr);
            songConfig.set(song.getName()+".Author",song.getAuthor());
            songConfig.set(song.getName()+".Instrument",song.getInstrument().toString());
        }
        try {
            songConfig.save(new File(getDataFolder(), "songs.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
