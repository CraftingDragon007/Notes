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
import java.util.Objects;

public final class Notes extends JavaPlugin {

    public static SongList songs;
    public static final HashMap<Player, Song> scans = new HashMap<>();
    public static FileConfiguration songConfig;

    @SuppressWarnings("SpellCheckingInspection")
    @Override
    public void onEnable() {
        // Plugin startup logic
        songs = new SongList();
        loadSavedSongs();
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
        Objects.requireNonNull(getCommand("playnotes")).setExecutor(new PlayNotes());
        Objects.requireNonNull(getCommand("playnotes")).setTabCompleter(new ch.gamepowerx.notes.tabcompleter.PlayNotes());
        Objects.requireNonNull(getCommand("playsavedsong")).setExecutor(new PlaySavedSong());
        Objects.requireNonNull(getCommand("playsavedsong")).setTabCompleter(new ch.gamepowerx.notes.tabcompleter.PlaySavedSong());
        Objects.requireNonNull(getCommand("getsonginfo")).setExecutor(new GetSongInfo());
        Objects.requireNonNull(getCommand("getsonginfo")).setTabCompleter(new SongCompleter());
        Objects.requireNonNull(getCommand("savesong")).setExecutor(new SaveSong());
        Objects.requireNonNull(getCommand("savesong")).setTabCompleter(new ch.gamepowerx.notes.tabcompleter.PlayNotes());
        Objects.requireNonNull(getCommand("scan")).setExecutor(new Scan());
        Objects.requireNonNull(getCommand("deletesong")).setExecutor(new DeleteSong());
        Objects.requireNonNull(getCommand("deletesong")).setTabCompleter(new SongCompleter());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        saveSavedSongs();
    }

    private void loadSavedSongs(){

        //noinspection ResultOfMethodCallIgnored
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
                Song.parseNoteList(noteStr, o);
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
