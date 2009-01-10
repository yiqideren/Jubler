/*
 * TMPGencLayoutDataItem.java
 *
 * Created on 09-Jan-2009 by Hoang Duy Tran <hoang_tran>
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
package com.panayotis.jubler.subs.loader.processor.TMPGenc;

import com.panayotis.jubler.subs.SubtitlePatternProcessor;
import com.panayotis.jubler.subs.records.TMPGenc.LayoutDataItemRecord;

/**
 *
 * @author Hoang Duy Tran <hoang_tran>
 */
public class TMPGencLayoutDataItem extends SubtitlePatternProcessor implements TMPGencPatternDef {

    /**
     * Pattern to identify item lines from the "[LayoutData]"
     * section of TMPGenc subtitle file.
     * <pre>
     * "Picture bottom layout",4,Tahoma,0.07,17588159451135,0,0,0,0,1,2,0,1,0.0035,0
     * </pre>
     * This class is used to parse the following line of data
     * <pre><b>"Picture bottom layout",0,Tahoma,0.07,17588159451135,0,0,0,0,1,2,0,1,0.0035,0</b></pre>
     *
     * The definition of each component is defined below:
     * <pre>
     * Name : "Picture top layout",
     * Display Area:
     *      Picture bottom = 0
     *      Picture top = 1
     *      Picture left = 2
     *      Picture right = 3
     *      Picture center = 4
     *      Picture bottom (Computer display) = 5
     *      Picture top (Computer display) = 6
     * Font : Tahoma,
     * Size%: 0.7 (7/10)
     * Font colour:
     *      red: (255,0,0 = #FF0000) TMP: 17587891077120 => 0x0FFF 0000 0000
     *      yellow: (255,255,0 = #FFFF00) TMP: 17588159447040 => 0x0FFF 0FFF 0000
     * Font style: 	Normal	Bold	Italic	Underscore	StrikeThrough
     * <num1>      	0	1	0	0		0
     * <num2>		0	0	1	0		0
     * <num3>		0	0	0	1		0
     * <num4>		0	0	0	0		1
     * Horizontal Alignment: left = 0, center = 1, right = 2 # ie. (2),2,0,1,0.0035,0
     * Vertical Alignment: top = 0, mid = 1, bottom = 2 	       (2),0,1,0.0035,0
     * Text Rotation: Write Vertical = 1  2,(1),1,0.0035,0
     * Border: No = 0, Yes = 1 	ie. (1),0.0035,0
     * Border size: 5 = 0.035, 7 = 0.049 (formular: x * 7 / 100)#
     * Border colour: black = 0, gray = 5841244652880 = 0x0550. 0550. 0550  = #55 55 55 = (RGB: 85,85,85)
     * </pre>
     */
    private static final String pattern =
            char_double_quote +
            "Picture" +
            printable + //layout
            char_double_quote +
            single_comma +
            digits + //4
            single_comma +
            printable + //font name
            single_comma +
            graph + //0.07
            single_comma +
            graph + //colour 17588159451135
            single_comma + //,0,0,0,0,1,2,0,1,0.0035,0
            digits + //0
            single_comma +
            digits + //0
            single_comma +
            digits + //0
            single_comma +
            digits + //0
            single_comma + //1,2,0,1,0.0035,0
            digits + //1
            single_comma +
            digits + //2
            single_comma +
            digits + //0
            single_comma +
            digits + //1
            single_comma + //0.0035,0
            graph + //0
            single_comma +
            digits;

    public TMPGencLayoutDataItem() {
        super(pattern);
        setTargetObjectClassName(LayoutDataItemRecord.class.getName());
    }

    public void parsePattern(String[] matched_data, Object record) {
        try {
            LayoutDataItemRecord r = (LayoutDataItemRecord) record;
            String txt = matched_data[0];
            String[] list = txt.split(",");

            r.setName(list[0]);
            r.setDisplayArea(Byte.parseByte(matched_data[2]));
            r.setFontName(matched_data[3]);
            r.setForntSize(Float.parseFloat(matched_data[4]));
            r.setFontColour(Double.parseDouble(matched_data[5]));

            r.setStyleBold(Byte.parseByte(matched_data[6]));
            r.setStyleItalic(Byte.parseByte(matched_data[7]));
            r.setStyleUnderScore(Byte.parseByte(matched_data[8]));
            r.setStyleStrikeThrough(Byte.parseByte(matched_data[9]));
            r.setAlignmentHorizontal(Byte.parseByte(matched_data[10]));
            r.setAlignmentVertical(Byte.parseByte(matched_data[11]));
            r.setTextRotation(Byte.parseByte(matched_data[12]));
            r.setTextBorder(Byte.parseByte(matched_data[13]));
            r.setBorderSize(Float.parseFloat(matched_data[14]));
            r.setBorderColour(Long.parseLong(matched_data[15]));
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }

    }//end if
}
