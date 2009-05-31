/*
 * SubFormats.java
 *
 * Created on 22 Ιούνιος 2005, 3:40 πμ
 *
 * This file is part of Jubler.
 *
 * Jubler is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2.
 *
 *
 * Jubler is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Jubler; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */
package com.panayotis.jubler.subs.loader;

import com.panayotis.jubler.Main;
import com.panayotis.jubler.subs.loader.text.PlainText;
import java.util.ArrayList;

/**
 *
 * @author teras and Hoang Duy Tran
 */
public class AvailSubFormats {

    private static final ArrayList<SubFormat> Formats = new ArrayList<SubFormat>();
    int current;

    /** Creates a new instance of SubFormats */
    public AvailSubFormats() {
        current = 0;
        if (size() == 0) {    // populate list
            Main.plugins.callPostInitListeners(this);
            Formats.add(new PlainText());
        }
    }

    public boolean hasMoreElements() {
        if (current < Formats.size())
            return true;
        return false;
    }

    public SubFormat nextElement() {
        return Formats.get(current++);
    }

    public static int size() {
        return Formats.size();
    }

    public static SubFormat findFromDescription(String name) {
        if (name == null)
            return null;
        for (int i = 0; i < Formats.size(); i++)
            if (Formats.get(i).getDescription().equals(name))
                return Formats.get(i);
        return null;
    }

    public static SubFormat findFromName(String ext) {
        if (ext == null)
            return null;
        for (int i = 0; i < Formats.size(); i++)
            if (Formats.get(i).getName().equals(ext))
                return Formats.get(i);
        return null;
    }

    public static SubFormat get(int i) {
        return Formats.get(i);
    }
}
