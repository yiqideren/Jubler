/*
 * JLoadPrefs.java
 *
 * Created on 23 Ιούνιος 2005, 2:27 μμ
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

package com.panayotis.jubler.options;
import com.panayotis.jubler.options.gui.JRateChooser;
import java.awt.BorderLayout;

import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.media.MediaFile;
import com.panayotis.jubler.subs.Subtitles;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

/**
 *
 * @author  teras
 */
public class JLoadOptions extends JFileOptions {
    
    private JRateChooser CFPS;
    private JComboBox [] CEnc;
    
    /** Creates new form JLoadPrefs */
    public JLoadOptions() {
        super();
        CEnc = new JComboBox[3];
        for (int i = 0 ; i < CEnc.length ; i++) {
            CEnc[i] = new JComboBox(AvailEncodings);
        }
        initComponents();
        hideUnicodeMenu();
        
        /* Fix DialogVisible */
        addDialogOption();
        updateDialogOption(_(" Show load preferences while loading file"),_("Show preferences every time the user loads a subtitle file"));
        
        CFPS = new JRateChooser();
        FPSPanel.add(CFPS, BorderLayout.CENTER);
    }
    
    
    public void updateVisuals(MediaFile mfile, Subtitles subs) {
        CFPS.setDataFiles(mfile, subs);
    }
    
    public float getFPS() {
        return CFPS.getFPSValue();
    }
    
    public String[] getEncodings() {
        String [] encs  = new String[CEnc.length];
        for (int i = 0 ; i < encs.length ; i++)
            encs[i] = CEnc[i].getSelectedItem().toString();
        return encs;
    }
    
    public void loadPreferences() {
        String e;
        String fps;
        
        for (int i = 0 ; i<CEnc.length ; i++ ) {
            e = Options.getOption("Load.Encoding"+(i+1), JPreferences.DefaultEncodings[i]);
            setListItem(CEnc[i], e);
        }
         fps = Options.getOption("Load.FPS", JRateChooser.DefaultFPSEntry);
        CFPS.setFPS(fps);
        setDialogOption(Options.getOption("System.ShowLoadDialog", "true").equals("true"));
    }
    
    public void savePreferences() {
        for (int i = 0 ; i < CEnc.length ; i++) {
            Options.setOption("Load.Encoding"+(i+1), CEnc[i].getSelectedItem().toString());
        }
        Options.setOption("Load.FPS", CFPS.getFPS());
        Options.setOption("System.ShowLoadDialog", getDialogOption());
    }
    
    public String getTabName() { return _("Load"); }
    public String getTabTooltip() { return _("Load subtitles options"); }
    public Icon getTabIcon() { return new ImageIcon(getClass().getResource("/icons/load_small.png")); }

    public void setPreEncoding(String enc) {
        CEnc[0].setSelectedItem("UTF-8");
        setListItem(CEnc[1], enc);
        CEnc[2].setSelectedItem("UTF-16");
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        OptsP = new javax.swing.JPanel();
        CEnc1L = new javax.swing.JLabel();
        CEnc2L = new javax.swing.JLabel();
        CEnc3L = new javax.swing.JLabel();
        FPSL = new javax.swing.JLabel();
        FPSPanel = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        OptsP.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 4, 0));
        OptsP.setLayout(new java.awt.GridLayout(4, 2));

        CEnc1L.setText(_("First Encoding"));
        OptsP.add(CEnc1L);
        OptsP.add(CEnc[0]);

        CEnc2L.setText(_("Second Encoding"));
        OptsP.add(CEnc2L);
        OptsP.add(CEnc[1]);

        CEnc3L.setText(_("Third Encoding"));
        OptsP.add(CEnc3L);
        OptsP.add(CEnc[2]);

        FPSL.setText(_("FPS"));
        OptsP.add(FPSL);

        FPSPanel.setLayout(new java.awt.BorderLayout());
        OptsP.add(FPSPanel);

        add(OptsP, java.awt.BorderLayout.NORTH);
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel CEnc1L;
    private javax.swing.JLabel CEnc2L;
    private javax.swing.JLabel CEnc3L;
    private javax.swing.JLabel FPSL;
    private javax.swing.JPanel FPSPanel;
    private javax.swing.JPanel OptsP;
    // End of variables declaration//GEN-END:variables

    
}
