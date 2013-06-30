/*
 * JTimeArea.java
 *
 * Created on 5 Ιούλιος 2005, 2:21 μμ
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

package com.panayotis.jubler.time.gui;

import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.subs.SubEntry;
import com.panayotis.jubler.subs.Subtitles;
import com.panayotis.jubler.time.Time;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author teras
 */
public abstract class JTimeArea extends JPanel {

    protected Subtitles subs;
    protected int[] selected;
    /* Use these variables to make new time dialogs to open with meaningful defaults */
    protected final static int DEFAULTS_BYSELECTION = 0;
    protected final static int DEFAULTS_BYCOLOR = 1;
    protected final static int DEFAULTS_BYTHEME = 2;
    protected final static int DEFAULTS_BYREGION = 3;
    protected static int selection_model = DEFAULTS_BYSELECTION;

    public abstract ArrayList<SubEntry> getAffectedSubs();

    /**
     * Creates a new instance of JTimeArea
     */
    public JTimeArea() {
        super();
        initComponents();
    }

    public void updateData(Subtitles subs, int[] selected) {
        this.subs = subs;
        this.selected = selected;
    }

    Time findFirstInList(Subtitles subs, int[] selected) {
        Time min, cur;
        double minsecs, cursecs;
        int i;

        minsecs = Time.MAX_TIME;
        min = new Time(0d);
        for (i = 0; i < selected.length; i++) {
            cur = subs.elementAt(selected[i]).getStartTime();
            cursecs = cur.toSeconds();
            if (cursecs < minsecs) {
                minsecs = cursecs;
                min = cur;
            }
        }
        return min;
    }

    Time findLastInList(Subtitles subs, int[] selected) {
        Time max, cur;
        double maxsecs, cursecs;
        int i;

        maxsecs = 0;
        max = new Time(0d);
        for (i = 0; i < selected.length; i++) {
            cur = subs.elementAt(selected[i]).getFinishTime();
            cursecs = cur.toSeconds();
            if (cursecs > maxsecs) {
                maxsecs = cursecs;
                max = cur;
            }
        }
        return max;
    }

    public void updateSubsMark(List<SubEntry> affected) {
        if (!ChSubColorB.isEnabled())
            return;
        int new_mark = ChSubColorC.getSelectedIndex();
        for (SubEntry sub : affected)
            sub.setMark(new_mark);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        SubCP = new javax.swing.JPanel();
        ChSubColorB = new javax.swing.JCheckBox();
        ChSubColorC = new javax.swing.JComboBox();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        SubCP.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 0, 0, 0));
        SubCP.setOpaque(false);
        SubCP.setLayout(new java.awt.BorderLayout(12, 0));

        ChSubColorB.setText(_("Change affected subtitles' color"));
        ChSubColorB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChSubColorBActionPerformed(evt);
            }
        });
        SubCP.add(ChSubColorB, java.awt.BorderLayout.WEST);

        ChSubColorC.setModel(new javax.swing.DefaultComboBoxModel(SubEntry.MarkNames));
        ChSubColorC.setEnabled(false);
        SubCP.add(ChSubColorC, java.awt.BorderLayout.CENTER);

        add(SubCP, java.awt.BorderLayout.SOUTH);
    }// </editor-fold>//GEN-END:initComponents

    private void ChSubColorBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChSubColorBActionPerformed
        ChSubColorC.setEnabled(ChSubColorB.isSelected());
    }//GEN-LAST:event_ChSubColorBActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ChSubColorB;
    private javax.swing.JComboBox ChSubColorC;
    protected javax.swing.JPanel SubCP;
    // End of variables declaration//GEN-END:variables
}
