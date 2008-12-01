/*
 * PlainText.java
 *
 * Created on 26 Αύγουστος 2005, 11:08 πμ
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
package com.panayotis.jubler.subs.loader.text;

import com.panayotis.jubler.subs.loader.AbstractTextSubFormat;
import com.panayotis.jubler.subs.SubEntry;
import com.panayotis.jubler.time.Time;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.panayotis.jubler.i18n.I18N._;

/**
 * The class is used to processing OCR(ed) text file to capture subtitle lines
 * in the following typical example:
 * 
 *        In November 1908, the steamship
 *        Hamburg America left Cherbourg
 * 
 * 	  on a transatlantic voyage
 * 		to New York.
 * 
 * @author Hoang Tran
 */
public class PlainOCRTextWithDoubleNewLine extends AbstractTextSubFormat {
    private static final String TAB = "\t";
    private static final String NEW_LINE = "\n";
    private static final String DOUBLE_NEW_LINE = "\\n\\n";
    private static final String PAGE_BREAK = "\f";
    
    private static final Pattern pat, test_pat;
    private double current_time = 0;
    

    static {
        test_pat = Pattern.compile("(.+?)" + DOUBLE_NEW_LINE);
        pat = Pattern.compile("(.+?)" + PAGE_BREAK);
    }

    protected Pattern getTestPattern() {
        return test_pat;
    }
    
    protected Pattern getPattern() {
        return pat;
    }

    protected SubEntry getSubEntry(Matcher m) {
        String g0 = m.group(0);
        String g1 = g0.trim();
        boolean has_text = (g1.length() > 0);
        if (has_text) {
            g1 = m.group(1).trim().replace(TAB, NEW_LINE);
            current_time += 2;
            Time start = new Time(current_time);
            current_time += 1;
            Time finish = new Time(current_time);
            return new SubEntry(start, finish, g1);
        } else {
            return null;
        }
    }

    /**
     * This is required to remove blank lines and the page-break,
     * reformat the lines with double new lines. The new-lines between 
     * lines of the same subtitle-event is replaced with TAB character
     * which will be replaced in the getSubEntry() later-on.
     * @param input The input string of the subtitle-text.
     * @return Recomposed subtitle-text.
     */
    public String preProcessing(String input) {
        boolean has_text = false;
        String[] list = input.split(DOUBLE_NEW_LINE);
        int len = list.length;
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < len; i++) {
            String this_line = list[i];
            String trimmed_text = this_line.trim();
            has_text = (trimmed_text.length() > 0);
            if (has_text) {
                String[] sub_list = trimmed_text.split(NEW_LINE);                
                for(int j=0; j < sub_list.length; j++){
                    trimmed_text = sub_list[j].trim();
                    has_text = (trimmed_text.length() > 0);
                    if (has_text){
                        b.append( trimmed_text );
                        b.append( TAB );
                    }//end if
                }//end if
                b.append(PAGE_BREAK);
            }//end if            
        }//end if
        input = b.toString();        
        return input;
    }

    public String getExtension() {
        return "txt";
    }

    public String getName() {
        return "PlainTxt";
    }

    public String getExtendedName() {
        return _("Plain text");
    }

    protected void appendSubEntry(SubEntry sub, StringBuffer str) {
        str.append(sub.getText()).append('\n');
    }

    protected String initLoader(String input) {
        current_time = 0;
        return super.initLoader(input);
    }

    public boolean supportsFPS() {
        return false;
    }
}