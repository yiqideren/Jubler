/*
 *  DeleteSubtitleAction.java 
 * 
 *  Created on: 19-Oct-2009 at 13:42:39
 * 
 *  
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

package com.panayotis.jubler.events.menu.edit;

import com.panayotis.jubler.Jubler;
import com.panayotis.jubler.MenuAction;
import com.panayotis.jubler.subs.Subtitles;
import com.panayotis.jubler.events.menu.edit.undo.UndoEntry;
import com.panayotis.jubler.events.menu.edit.undo.UndoList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import static com.panayotis.jubler.i18n.I18N._;

/**
 *
 * @author  teras
 */
public class DeleteSubtitleAction extends MenuAction {

    public DeleteSubtitleAction(Jubler parent) {
        super(parent);
    }

    /**
     *
     * @param e Action Event
     */
    public void actionPerformed(ActionEvent evt) {
        Jubler jb = jublerParent;
        JTable SubTable = jb.getSubTable();
        Subtitles subs = jb.getSubtitles();
        UndoList undo = jb.getUndoList();

        undo.addUndo(new UndoEntry(subs, _("Delete subtitles")));
        int sel[] = SubTable.getSelectedRows();
        for (int i = sel.length - 1; i >= 0; i--) {
            subs.remove(sel[i]);
        }
        jb.fn.tableHasChanged(null);

    }//end public void actionPerformed(ActionEvent evt)
}//end public class DeleteSubtitleAction extends MenuAction
