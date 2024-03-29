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

public class Pause {
    private final Long duration;

    private boolean inTicks;

    public Pause(Integer duration){
        this.duration = duration.longValue();
    }

    public Pause(Long duration,boolean inTicks){
        this.duration = duration;
        this.inTicks = inTicks;
    }

    public int getDurationInt(){
        return duration.intValue();
    }

    public long getDuration(){
        return duration;
    }

    public boolean isInTicks(){
        return inTicks;
    }
}
