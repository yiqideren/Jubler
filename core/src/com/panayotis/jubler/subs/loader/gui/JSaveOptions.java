/*
 * JSavePrefs.java
 *
 * Created on 23 Ιούνιος 2005, 2:32 μμ
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
package com.panayotis.jubler.subs.loader.gui;

import com.panayotis.jubler.options.gui.JRateChooser;
import com.panayotis.jubler.subs.loader.AvailSubFormats;
import java.awt.BorderLayout;

import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.media.MediaFile;
import com.panayotis.jubler.plugins.Availabilities;
import com.panayotis.jubler.subs.SubFile;
import com.panayotis.jubler.subs.Subtitles;

/**
 *
 * @author  teras
 */
public class JSaveOptions extends JFileOptions {

    private JRateChooser CFPS;

    /** Creates new form JSavePrefs */
    public JSaveOptions() {
        super();
        CFPS = new JRateChooser();
        initComponents();
        for (int i = 0; i < Availabilities.formats.size(); i++)
            CFormat.addItem(Availabilities.formats.get(i).getDescription());
        ControlsP.add(CFPS, BorderLayout.CENTER);
    }

    public void updateVisuals(Subtitles subs, MediaFile mfile) {
        setUnicodeVisible(true);
        CEncP.add(getPresetsButton(), BorderLayout.EAST);

        CFPS.setDataFiles(mfile, subs);
        CFPS.setFPS(subs.getSubFile().getFPS());
        CFormat.setSelectedItem(subs.getSubFile().getFormat().getDescription());
        setListItem(CEnc, subs.getSubFile().getEncoding());

        updateVisualFPS();  // Set if FPS controls are visible - should be called AFTER CFormat initialization
    }

    protected void applyOptions(SubFile sfile) {
        super.applyOptions(sfile);
        sfile.setFPS(CFPS.getFPSValue());
        sfile.setEncoding(CEnc.getSelectedItem().toString());
        sfile.setFormat(CFormat.getSelectedItem().toString());
    }


    /* Execute this method whenever the output format is changed (or this panel is displayed */
    private void updateVisualFPS() {
        String format_name = CFormat.getSelectedItem().toString();
        boolean supports_fps = Availabilities.formats.findFromDescription(format_name).supportsFPS();
        FPSPanelL.setVisible(supports_fps);
        CFPS.setVisible(supports_fps);
    }

    public void setPreEncoding(String enc) {
        setListItem(CEnc, enc);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        CFormatL = new javax.swing.JLabel();
        CEncL = new javax.swing.JLabel();
        FPSPanelL = new javax.swing.JLabel();
        ControlsP = new javax.swing.JPanel();
        CFormat = new javax.swing.JComboBox();
        CEncP = new javax.swing.JPanel();
        CEnc = new javax.swing.JComboBox(AvailEncodings);

        setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 4, 0, 4));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 3));

        CFormatL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CFormatL.setText(_("Format"));
        jPanel1.add(CFormatL);

        CEncL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        CEncL.setText(_("Encoding"));
        jPanel1.add(CEncL);

        FPSPanelL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        FPSPanelL.setText(_("FPS"));
        jPanel1.add(FPSPanelL);

        add(jPanel1, java.awt.BorderLayout.NORTH);

        ControlsP.setLayout(new java.awt.GridLayout(1, 3));

        CFormat.setToolTipText(_("Subtitle format of the output file (SRT is prefered)"));
        CFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CFormatActionPerformed(evt);
            }
        });
        ControlsP.add(CFormat);

        CEncP.setLayout(new java.awt.BorderLayout());
        CEncP.add(CEnc, java.awt.BorderLayout.CENTER);

        ControlsP.add(CEncP);

        add(ControlsP, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void CFormatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CFormatActionPerformed
        updateVisualFPS();
    }//GEN-LAST:event_CFormatActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CEnc;
    private javax.swing.JLabel CEncL;
    private javax.swing.JPanel CEncP;
    private javax.swing.JComboBox CFormat;
    private javax.swing.JLabel CFormatL;
    private javax.swing.JPanel ControlsP;
    private javax.swing.JLabel FPSPanelL;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
