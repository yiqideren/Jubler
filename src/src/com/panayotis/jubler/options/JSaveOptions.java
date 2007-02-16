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

package com.panayotis.jubler.options;
import com.panayotis.jubler.subs.loader.AvailSubFormats;
import com.panayotis.jubler.subs.loader.SubFormat;
import java.awt.BorderLayout;
import java.util.Properties;

import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.media.MediaFile;
import com.panayotis.jubler.subs.Subtitles;
/**
 *
 * @author  teras
 */
public class JSaveOptions extends JOptionsGUI {
    
    private String enc_state, fps_state, format_state;
    private JRateChooser CFPS;
    
    /** Creates new form JSavePrefs */
    public JSaveOptions() {
        initComponents();
        
        CFPS = new JRateChooser();
        
        FPSPanel.add(CFPS, BorderLayout.CENTER);
        fillComponents();
    }
    
    public void updateVisuals(MediaFile mfile, Subtitles subs) {
        CFPS.setDataFiles(mfile, subs);
        updateVisualFPS(null);  // get the current SubFormat
    }
    
    private void fillComponents() {
        int i;
        
        for (i = 0 ; i < AvailSubFormats.Formats.length ; i++ ) {
            CFormat.addItem(AvailSubFormats.Formats[i].getDescription());
        }
        for ( i = 0 ; i < JPreferences.AvailEncodings.length ; i++) {
            CEnc.addItem(JPreferences.AvailEncodings[i]);
        }
        
    }
    
    
    public float getFPS() {
        return CFPS.getFPSValue();
    }
    
    public String getEncoding() {
        return getItemName(CEnc);
    }
    
    public SubFormat getFormat() {
        return AvailSubFormats.findFromDescription(getItemName(CFormat));
    }
    
    public void savePreferences(Properties props) {
        props.setProperty("Save.Encoding", getItemName(CEnc));
        props.setProperty("Save.FPS", CFPS.getFPS());
        
        SubFormat f = AvailSubFormats.findFromDescription(getItemName(CFormat));
        props.setProperty("Save.Format", (f!=null)?f.getName():"UNKNOWN");
    }
    
    
    public void loadPreferences(Properties props) {
        String enc, fps, format;
        
        enc = props.getProperty("Save.Encoding", JPreferences.DefaultEncodings[0]);
        fps = props.getProperty("Save.FPS", JRateChooser.DefaultFPSEntry);
        format = props.getProperty("Save.Format", AvailSubFormats.Formats[0].getName());
        
        setCombo(CEnc, enc, "US-ASCII");
        CFPS.setFPS(fps);
        
        SubFormat f = AvailSubFormats.findFromName(format);
        setCombo(CFormat, (f!=null)?f.getDescription():"UNKNOWN", "UNKNOWN");
        updateVisualFPS(f);
    }
    
    
    /* Execute this method whenever the output format is changed (or this panel is displayed */
    private void updateVisualFPS(SubFormat f) {
        if (f==null) f = getFormat();
        boolean supports_fps = f.supportsFPS();
        FPSPanelL.setVisible(supports_fps);
        FPSPanel.setVisible(supports_fps);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        CFormatL = new javax.swing.JLabel();
        CFormat = new javax.swing.JComboBox();
        CEncL = new javax.swing.JLabel();
        CEnc = new javax.swing.JComboBox();
        FPSPanelL = new javax.swing.JLabel();
        FPSPanel = new javax.swing.JPanel();

        setLayout(new java.awt.GridLayout(3, 2));

        CFormatL.setText(_("Format"));
        add(CFormatL);

        CFormat.setToolTipText(_("Subtitle format of the output file (SRT is prefered)"));
        CFormat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CFormatActionPerformed(evt);
            }
        });

        add(CFormat);

        CEncL.setText(_("Encoding"));
        add(CEncL);

        CEnc.setToolTipText(_("Encoding of the saved file"));
        add(CEnc);

        FPSPanelL.setText(_("FPS"));
        add(FPSPanelL);

        FPSPanel.setLayout(new java.awt.BorderLayout());

        add(FPSPanel);

    }// </editor-fold>//GEN-END:initComponents
    
    private void CFormatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CFormatActionPerformed
        updateVisualFPS(null);
    }//GEN-LAST:event_CFormatActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox CEnc;
    private javax.swing.JLabel CEncL;
    private javax.swing.JComboBox CFormat;
    private javax.swing.JLabel CFormatL;
    private javax.swing.JPanel FPSPanel;
    private javax.swing.JLabel FPSPanelL;
    // End of variables declaration//GEN-END:variables
    
}
