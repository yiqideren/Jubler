/*
 * jTimeRegion.java
 *
 * Created on 5 Ιούλιος 2005, 11:30 πμ
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

import com.panayotis.jubler.subs.SubEntry;
import com.panayotis.jubler.subs.Subtitles;
import java.awt.BorderLayout;
import java.util.ArrayList;

/**
 *
 * @author teras
 */
public class JTimeRegion extends JTimeArea {

    private JTimeSelector starttime, endtime;

    /**
     * Creates new form jTimeRegion
     */
    public JTimeRegion() {
        super();
        initComponents();
        add(SubCP, BorderLayout.SOUTH);

        starttime = new JTimeSelector(true);
        endtime = new JTimeSelector(false);
        TimesP.add(starttime);
        TimesP.add(endtime);
    }

    @Override
    public void setEnabled(boolean status) {
        super.setEnabled(status);
        starttime.setEnabled(status);
        endtime.setEnabled(status);
    }

    @Override
    public void updateData(Subtitles subs, int[] selected) {
        super.updateData(subs, selected);
        starttime.updateData(findFirstInList(subs, selected));
        endtime.updateData(findLastInList(subs, selected));
    }

    public ArrayList<SubEntry> getAffectedSubs() {
        /* Select affected subtitles by time region */
        double tstart, tfinish;
        double tcurrent;

        ArrayList<SubEntry> affected;
        SubEntry csub;
        int i;

        tstart = getStartTime();
        tfinish = getFinishTime();

        affected = new ArrayList<SubEntry>();
        for (i = 0; i < subs.size(); i++) {
            csub = subs.elementAt(i);
            tcurrent = csub.getStartTime().toSeconds();
            if (tcurrent >= tstart && tcurrent <= tfinish)
                affected.add(csub);
        }
        return affected;
    }

    public double getStartTime() {
        return starttime.getTime();
    }

    public double getFinishTime() {
        return endtime.getTime();
    }

    public void setRegionToMaximum() {
        starttime.setTimeToEdge();
        endtime.setTimeToEdge();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        FromGroup = new javax.swing.ButtonGroup();
        ToGroup = new javax.swing.ButtonGroup();
        TimesP = new javax.swing.JPanel();

        setOpaque(false);
        setLayout(new java.awt.BorderLayout());

        TimesP.setOpaque(false);
        TimesP.setLayout(new java.awt.GridLayout(1, 2));
        add(TimesP, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup FromGroup;
    private javax.swing.JPanel TimesP;
    private javax.swing.ButtonGroup ToGroup;
    // End of variables declaration//GEN-END:variables
}
