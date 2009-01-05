/*
 * CommonDef.java
 *
 * Created on 04-Dec-2008, 02:19:26
 *
 * This file is part of Jubler.
 * Jubler is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2.
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

package com.panayotis.jubler.subs;

/**
 * Common pattern definitions.
 * @author Hoang Duy Tran
 */
public interface CommonDef {
    public static final String USER_HOME_DIR = System.getProperty("user.home") + System.getProperty("file.separator");
    public static final String USER_CURRENT_DIR = System.getProperty("user.dir") + System.getProperty("file.separator");    
    public static final String DOS_NL = "\r\n";
    public static final String UNIX_NL = "\n";
    public static final String nl = "\\\n";
    public static final String sp = "([ \\t]+)";
    public static final String sp_maybe = "([ \\t]*)";
    public static final String digits = "([0-9]+)";
    public static final String graph = "(\\p{Graph}+)";
    public static final String printable = "(\\p{Print}+)";
    public static final String anything = "(.*?)";
    public static final String son_time = digits + ":" + digits + ":" + digits + ":" + digits;
    public static final String srt_time = digits + ":" + digits + ":" + digits + "," + digits;
    public static final String sp_digits = sp_maybe + digits;
}
