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

import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        if(!GraphicsEnvironment.isHeadless())
            JOptionPane.showMessageDialog(null, "This is a Minecraft Plugin, not a Java Application! You have to upload the plugin to the server and then restart the server to use this plugin!", "Notes", JOptionPane.ERROR_MESSAGE);
        System.out.println("ERROR: This is a Minecraft Plugin, not a Java Application! You have to upload the plugin to the server and then restart the server to use this plugin!");
    }
}