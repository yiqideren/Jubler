/*
 * JPreferences.java
 *
 * Created on June 1, 2007, 1:57 PM
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

import static com.panayotis.jubler.i18n.I18N._;

import com.panayotis.jubler.Jubler;
import com.panayotis.jubler.media.MediaFile;
import com.panayotis.jubler.media.player.AvailPlayers;
import com.panayotis.jubler.media.player.VideoPlayer;
import com.panayotis.jubler.options.gui.JOptionTabs;
import com.panayotis.jubler.subs.Subtitles;
import com.panayotis.jubler.subs.loader.AvailSubFormats;
import com.panayotis.jubler.subs.loader.SubFormat;
import com.panayotis.jubler.tools.spell.SpellChecker;
import com.panayotis.jubler.tools.spell.checkers.AvailSpellCheckers;
import java.awt.BorderLayout;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

/**
 *
 * @author  teras
 */
public class JPreferences extends javax.swing.JDialog {
    public static final String []AvailEncodings;
    public static final String []DefaultEncodings;
    public static final SubFormat DefaultSubFormat;
    
    /* GUI element to hold various preferences 
     * it is "friendly", since it is needed in Options 
     */
    JOptionTabs Tabs;
    
    /* Shortcuts to panels */
    private JLoadOptions jload;
    private JSaveOptions jsave;
    private JExternalOptions jplay;
    private JExternalOptions jspell;
    private JShortcutsOptions jcut;
    
    private boolean dialog_status;
    
    static {
        SortedMap encs = Charset.availableCharsets();
        AvailEncodings = new String[encs.size()];
        int pos;
        String item;
        
        pos = 0;
        for (Iterator it = encs.keySet().iterator() ; it.hasNext() ; ) {
            item = it.next().toString();
            AvailEncodings[pos++] = item;
        }
        
        DefaultEncodings = new String[3];
        DefaultEncodings[0] = "UTF-8";
        DefaultEncodings[1] = "ISO-8859-1";
        DefaultEncodings[2] = "UTF-16";
        
        DefaultSubFormat = AvailSubFormats.findFromName("SubStationAlpha");
    }
    
    
    /** Creates new form JPreferences */
    public JPreferences(Jubler jub) {
        
        Tabs = new JOptionTabs(this);
        Tabs.addTab(jload = new JLoadOptions());
        Tabs.addTab(jsave = new JSaveOptions());
        Tabs.addTab(jplay = new JExternalOptions(new AvailPlayers()));
        Tabs.addTab(jspell = new JExternalOptions(new AvailSpellCheckers()));
        Tabs.addTab(jcut = new JShortcutsOptions(jub.JublerMenuBar));
        Options.loadSystemPreferences(this);
        
        initComponents();
        add(Tabs, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
    }
    
    
    /* Various methods to get the preferences to the othe components */
    public float getLoadFPS() {
        return jload.getFPS();
    }
    public String[] getLoadEncodings() {
        return jload.getEncodings();
    }
    public String getSaveEncoding() {
        return jsave.getEncoding();
    }
    public float getSaveFPS() {
        return jsave.getFPS();
    }
    public SubFormat getSaveFormat() {
        return jsave.getFormat();
    }
    
    public VideoPlayer getVideoPlayer() {
        return (VideoPlayer)jplay.getObject();
    }
    public SpellChecker getSpellChecker() {
        return (SpellChecker)jspell.getObject();
    }
    
    public void setMenuShortcuts(JMenuBar bar) {
        jcut.applyMenuShortcuts(bar);
    }
    
    public void showLoadDialog(JFrame parent, MediaFile mfile, Subtitles subs) {
        jload.showDialog(parent, mfile, subs);
        Tabs.setVisibleTab(0);
    }
    public void showSaveDialog(JFrame parent, MediaFile mfile, Subtitles subs) {
        jsave.showDialog(parent, mfile, subs);
        Tabs.setVisibleTab(1);
    }
    
    public void showPreferencesDialog() {
        dialog_status = false;
        Options.loadSystemPreferences(this);
        Tabs.initTabs();
        setVisible(true);
        if ( dialog_status ) Options.saveSystemPreferences(this);
        else Options.loadSystemPreferences(this); // Make sure options are returned to their saved state
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        LowerP = new javax.swing.JPanel();
        ButtonsP = new javax.swing.JPanel();
        CancelB = new javax.swing.JButton();
        AcceptB = new javax.swing.JButton();

        setTitle(_("Jubler Preferences"));
        setModal(true);
        setResizable(false);
        LowerP.setLayout(new java.awt.BorderLayout());

        ButtonsP.setLayout(new java.awt.GridLayout(1, 2, 4, 0));

        ButtonsP.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 0, 6, 16));
        CancelB.setText(_("Cancel"));
        CancelB.setToolTipText(_("Cancel changes and revert to previous values"));
        CancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelBActionPerformed(evt);
            }
        });

        ButtonsP.add(CancelB);

        AcceptB.setText(_("Accept"));
        AcceptB.setToolTipText(_("Accept and save preferences"));
        AcceptB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AcceptBActionPerformed(evt);
            }
        });

        ButtonsP.add(AcceptB);

        LowerP.add(ButtonsP, java.awt.BorderLayout.EAST);

        getContentPane().add(LowerP, java.awt.BorderLayout.SOUTH);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void CancelBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelBActionPerformed
        dialog_status = false;
        setVisible(false);
    }//GEN-LAST:event_CancelBActionPerformed
    
    private void AcceptBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AcceptBActionPerformed
        dialog_status = true;
        setVisible(false);
    }//GEN-LAST:event_AcceptBActionPerformed
        
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AcceptB;
    private javax.swing.JPanel ButtonsP;
    private javax.swing.JButton CancelB;
    private javax.swing.JPanel LowerP;
    // End of variables declaration//GEN-END:variables
    
}
