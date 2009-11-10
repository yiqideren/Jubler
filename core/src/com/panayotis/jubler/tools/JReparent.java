/*
 * JReparent.java
 *
 * Created on 25 ΈôΈΩœçΈΫΈΙΈΩœ² 2005, 3:31 ΈΦΈΦ
 * 
 * This file is part of JubFrame.
 *
 * JubFrame is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 2.
 *
 *
 * JubFrame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JubFrame; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 *
 */

package com.panayotis.jubler.tools;
import static com.panayotis.jubler.i18n.I18N._;

import com.panayotis.jubler.JubFrame;
import java.util.Vector;

import static com.panayotis.jubler.i18n.I18N._;
import java.util.ArrayList;

/**
 *
 * @author  teras
 */
public class JReparent extends javax.swing.JPanel {
    private ArrayList<JubFrame> jublerlist;
    
    /** Creates new form JSplit */
    public JReparent(JubFrame current, JubFrame parent) {
        initComponents();
        jublerlist = new ArrayList<JubFrame>();
        
        int selection = 0;
        JubFrame cjubler;
        
        JubSelector.addItem(_("-No parent available-"));
        for ( int i = 0 ; i <JubFrame.windows.size() ; i++) {
            cjubler = JubFrame.windows.elementAt(i);
            if ( cjubler != current ) {
                jublerlist.add(cjubler);
                if (cjubler==parent) selection = jublerlist.size();
                JubSelector.addItem(cjubler.getSubtitles().getSubFile().getStrippedFile().getName());
            }
        }
        JubSelector.setSelectedIndex(selection);
    }
    
    public JubFrame getDesiredParent() {
        if (JubSelector.getSelectedIndex()<1) return null;
        return jublerlist.get(JubSelector.getSelectedIndex()-1);
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        Position = new javax.swing.ButtonGroup();
        JubSelector = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        JubSelector.setToolTipText(_("The new parent subtitles file"));
        add(JubSelector, java.awt.BorderLayout.CENTER);

        jLabel1.setText(_("Provide the desired parent for this subtitles file"));
        add(jLabel1, java.awt.BorderLayout.NORTH);

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox JubSelector;
    private javax.swing.ButtonGroup Position;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
    
}
