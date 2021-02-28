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
                //System.out.println(noteStr);
                song.addNotes(Song.parseNote(noteStr));
            }
            songs.add(song);
        }
    }

    private void saveSavedSongs(){
        //songConfig.set("Songs",songs);
        for(Song song : songs){
            songConfig.set(song.getName()+".Name",song.getName());
            //songConfig.set(song.getName()+".Notes",Song.parseNoteArrayString(song.getNoteList()));
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
