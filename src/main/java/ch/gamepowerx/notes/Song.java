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

import org.bukkit.Instrument;
import org.bukkit.Note;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Song {
    private String name;
    private final String author;
    private final Instrument instrument;
    private List<Object> noteList;

    public Song(String name,String author, Instrument instrument){
        this.name = name;
        this.author = author;
        this.instrument = instrument;
        this.noteList = new ArrayList<>();
    }

    public Song(String name,String author, Instrument instrument, Object... notes){
        this.name = name;
        this.author = author;
        this.instrument = instrument;
        this.noteList = new ArrayList<>();
        this.noteList.addAll(Arrays.asList(notes));
    }

    public Song(String name,String author, Instrument instrument, List<Object> noteList){
        this.name = name;
        this.author = author;
        this.instrument = instrument;
        this.noteList = noteList;
    }

    public void playSong(Player player) {
        Thread thread = new Thread(() -> {
            for (Object o : noteList) {
                if (o instanceof Note note) {
                    player.playNote(player.getLocation(), instrument, note);
                } else if (o instanceof Pause pause) {
                    try {
                        if (!pause.isInTicks()) {
                            TimeUnit.SECONDS.sleep(pause.getDurationInt());
                        } else {
                            TimeUnit.MILLISECONDS.sleep(pause.getDuration() * 50);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, "Notes");
        thread.start();
    }

    public void setName(String name){
        this.name = name;
    }

    public void setNotes(List<Object> noteList){
        this.noteList = noteList;
    }

    public void addNotes(Object... notes){
        this.noteList.addAll(Arrays.asList(notes));
    }

    public String getName(){
        return name;
    }

    public String getAuthor(){
        return author;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public List<Object> getNoteList(){
        return noteList;
    }

    public static Object parseNote(String strNote){
        char[] chars = strNote.toCharArray();
        Note note = null;
        Pause pause = null;
        if(chars[0]=='-'){
            final long duration = Long.parseLong(String.valueOf(chars[1]));
            if(chars.length==3){
                if(chars[2]=='T')
                    pause = new Pause(duration,true);
                if(chars[2]=='S')
                    pause = new Pause(duration,false);
            }else
            pause = new Pause(duration,true);
        }else
        if(chars.length==3){
            if(chars[2]=='#'){
                if(chars[0]=='0')
                    note = new Note(0, Note.Tone.valueOf(String.valueOf(chars[1])), true);
                if(chars[0]=='1')
                    note = new Note(1, Note.Tone.valueOf(String.valueOf(chars[1])), true);
                if(chars[0]=='2')
                    note = new Note(2, Note.Tone.valueOf(String.valueOf(chars[1])), true);
            }
        }else {
            if(chars[0]=='0')
                note = new Note(0, Note.Tone.valueOf(String.valueOf(chars[1])), false);
            if(chars[0]=='1')
                note = new Note(1, Note.Tone.valueOf(String.valueOf(chars[1])), false);
            if(chars[0]=='2')
                note = new Note(2, Note.Tone.F, true);
        }
        if(note==null){
            return pause;
        }else
        return note;
    }

    public static List<String> parseNoteStrings(List<Object> noteList){
        List<String> noteStr = new ArrayList<>();
        for(Object o : noteList){
            parseNoteList(noteStr, o);
        }
        return noteStr;
    }

    public static void parseNoteList(List<String> noteStr, Object o) {
        if(o instanceof Pause pause){
            String isInTicks = null;
            if(pause.isInTicks())
                isInTicks = "T";
            if(!pause.isInTicks())
                isInTicks = "S";
            noteStr.add("-"+pause.getDuration()+isInTicks);
        }else if(o instanceof Note note){
            if(!note.isSharped())
                noteStr.add(String.valueOf(note.getOctave())+note.getTone());
            if(note.isSharped())
                noteStr.add(String.valueOf(note.getOctave())+note.getTone()+"#");
        }
    }

    @Deprecated
    public static List<Object> parseNoteList(String noteArray, boolean isInTicks){
        List<Object> noteList = new ArrayList<>();
        for(Character character : noteArray.toUpperCase().toCharArray()){
           if(Character.isDigit(character)){
               if(isInTicks){
                   noteList.add(new Pause(Long.parseLong(character.toString()),true));
               }else
               noteList.add(new Pause(Integer.parseInt(character.toString())));
           }else {
               noteList.add(new Note(0, Note.Tone.valueOf(character.toString()),false));
           }
        }
        return noteList;
    }

@Deprecated
    public static String parseNoteArrayString(List<Object> noteList){
        StringBuilder str = new StringBuilder();
        for(Object o : noteList){
            if(o instanceof Note note){
                str.append(note.getTone());
            }else if(o instanceof Pause pause){
                str.append(pause.getDuration());
            }
        }
        return str.toString();
    }
}
