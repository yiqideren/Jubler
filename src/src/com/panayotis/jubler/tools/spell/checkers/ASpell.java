/*
 * ASpell.java
 *
 * Created on 15 Ιούλιος 2005, 1:58 πμ
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

package com.panayotis.jubler.tools.spell.checkers;

import com.panayotis.jubler.os.DEBUG;
import com.panayotis.jubler.options.ASpellOptions;
import com.panayotis.jubler.options.JExtBasicOptions;
import com.panayotis.jubler.tools.spell.SpellChecker;
import com.panayotis.jubler.tools.spell.SpellError;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;
import java.util.Vector;

import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.os.SystemDependent;
import java.util.ArrayList;

/**
 *
 * @author teras
 */
public class ASpell extends SpellChecker {
    BufferedWriter send;
    BufferedReader get;
    
    ASpellOptions opts;
    
    Process proc;
    
    /**
     * Creates a new instance of ASpell
     */
    public ASpell() {
        opts = new ASpellOptions(getType(), getName(), getFileName());
    }
    
    public boolean initialize() {
        try {
            boolean forceutf8 = SystemDependent.forceASpellEncoding();
            
            ArrayList<String> cmd = new ArrayList<String>();
            cmd.add(opts.getExecFileName());
            if (forceutf8) cmd.add("--encoding=utf-8");
            
            ASpellOptions.ASpellDict lang = opts.getLanguage();
            if ( lang != null ) {
                if (lang.path != null) {
                    cmd.add("--dict-dir="+lang.path);
                }
                cmd.add("-d");
                cmd.add(lang.lang);
            }
            cmd.add("pipe");
            
            String[] c = cmd.toArray(new String[1]);
            proc = Runtime.getRuntime().exec(c);
            DEBUG.info(DEBUG.toString(c), DEBUG.INFO_ALWAYS);
            
            if (forceutf8) {
                send = new BufferedWriter( new OutputStreamWriter(proc.getOutputStream(), "UTF-8"));
                get = new BufferedReader( new InputStreamReader(proc.getInputStream(), "UTF-8"));
            } else {
                send = new BufferedWriter( new OutputStreamWriter(proc.getOutputStream()));
                get = new BufferedReader( new InputStreamReader(proc.getInputStream()));
            }
            
            get.readLine();
            /* Read aspell information */
            send.write("!\n");  /* Enter terse mode */
            return true;
        } catch (IOException e) {
            DEBUG.error(_("Error while executing ASpell"));
        }
        return false;
    }
    
    public void stop() {
        if ( proc != null ) proc.destroy();
        proc = null;
    }
    
    public boolean insertWord(String word) {
        if ( proc != null ) {
            try {
                send.write('*'+word+"\n#\n");
                send.flush();
                return true;
            } catch (IOException e) {}
        }
        return false;
    }
    
    
    public boolean supportsInsert() { return true; }
    
    public Vector<SpellError> checkSpelling(String text) {
        Vector<SpellError> ret = new Vector<SpellError>();
        String input;
        
        String orig;
        int pos;
        Vector<String> sug;
        
        try {
            send.write('^' + text.replace('\n', '|').replace('\r', '|') +'\n');
            send.flush();
            
            while ( !(input=get.readLine()).equals("")) {
                StringTokenizer token = new StringTokenizer(input," \t\r\n:,");
                String part = token.nextToken();
                if ( part.equals("&") ) {
                    sug = new Vector<String>();
                    orig = token.nextToken();
                    token.nextToken();
                    pos = Integer.parseInt(token.nextToken()) - 1;
                    while ( token.hasMoreTokens()) {
                        sug.add(token.nextToken());
                    }
                    ret.add(new SpellError(pos, orig, sug));
                } else if ( part.equals("#")) {
                    sug = new Vector<String>();
                    orig = token.nextToken();
                    pos = Integer.parseInt(token.nextToken()) - 1;
                    ret.add(new SpellError(pos, orig, sug));
                }
            }
            return ret;
        } catch (IOException e) {}
        return ret;
    }
    
    public JExtBasicOptions getOptionsPanel() { return opts; }
    public String getName() { return "ASpell"; }
    public String getType() { return "Speller"; }
    public String getLocalType() { return _("Speller"); }
    
}
