/*
 * SWTSubtitleText.java
 *
 * Created on 11-Dec-2008 by Hoang Duy Tran <hoang_tran>
 */

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
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
 * Contributor(s):
 * 
 */
package com.panayotis.jubler.subs.loader.processor.SWT;

import com.panayotis.jubler.subs.SubtitlePatternProcessor;
import com.panayotis.jubler.subs.records.SWTSubEntry;

/**
 *
 * @author Hoang Duy Tran <hoang_tran>
 */
public class SWTSubtitleText extends SubtitlePatternProcessor implements SWTPatternDef {

    private static String pattern = p_swt_text;
    int index = 1;
    private SWTSubEntry swtSubEntry = null;

    public SWTSubtitleText() {
        super(pattern);
        setMatchIndexList(index);
    }

    public void parsePattern(String[] matched_data, Object record) {
        if (record instanceof SWTSubEntry) {
            setSwtSubEntry((SWTSubEntry) record);

            String txt = getSwtSubEntry().getText();
            boolean has_text = (txt != null && txt.length() > 0);
            if (has_text) {
                StringBuffer b = new StringBuffer();
                b.append(getSwtSubEntry().getText());
                b.append(UNIX_NL);
                b.append(matched_data[0]);
                getSwtSubEntry().setText(b.toString());
            } else {
                getSwtSubEntry().setText(matched_data[0]);
            }//end if            
        }//end if (record instanceof SonSubEntry)

    }//end if

    public SWTSubEntry getSwtSubEntry() {
        return swtSubEntry;
    }

    public void setSwtSubEntry(SWTSubEntry swtSubEntry) {
        this.swtSubEntry = swtSubEntry;
    }
}
