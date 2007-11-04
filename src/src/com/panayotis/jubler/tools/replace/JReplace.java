/*
 * JReplace.java
 *
 * Created on 28 Ιούλιος 2005, 12:29 μμ
 */

package com.panayotis.jubler.tools.replace;

import com.panayotis.jubler.os.JIDialog;
import com.panayotis.jubler.Jubler;
import com.panayotis.jubler.subs.Subtitles;
import com.panayotis.jubler.undo.UndoEntry;
import com.panayotis.jubler.undo.UndoList;
import java.awt.Color;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.subs.SubEntry;


/**
 *
 * @author  teras
 */
public class JReplace extends javax.swing.JDialog {
    
    private Jubler parent;
    private Subtitles subs;
    private UndoList undo;
    
    private int row, foundpos, nextpos, length;
    
    /**
     * Creates new form JReplace
     */
    public JReplace(Jubler parent, int row, UndoList undo) {
        super(parent, false);
        
        this.parent = parent;
        this.row = row;
        if ( this.row < 0 ) this.row = 0;
        nextpos = 0;
        foundpos = -1;
        this.undo = undo;
        
        subs = parent.getSubtitles();
        initComponents();
        FindT.requestFocusInWindow();
    }
    
    
    public void findNextWord() {
        String what, inwhich;
        
        what = FindT.getText();
        if ( IgnoreC.isSelected()) what = what.toLowerCase();
        length = what.length();
        if ( subs.size() < 1) return;
        
        while ( true ) {
            inwhich = subs.elementAt(row).getText();
            if ( IgnoreC.isSelected()) inwhich = inwhich.toLowerCase();
            foundpos = inwhich.indexOf(what, nextpos);
            if ( foundpos >= 0 ) {
                ReplaceB.setEnabled(true);
                nextpos = foundpos + length;
                setSentence(subs.elementAt(row).getText(), foundpos, length);
                parent.setSelectedSub(row, true);
                return;
            }
            row ++;
            nextpos = 0;
            if ( row == subs.size()) {
                int ret = JIDialog.action(this, _("End of subtitles reached.\nStart from the beginnning."), _("End of subtitles"));
                if ( ret != JIDialog.OK_OPTION) {
                    prepareExit();
                    return;
                }
                row = 0;
            }
        }
    }
    
    private void prepareExit() {
        setVisible(false);
        dispose();
    }
    
    
    private void setSentence(String txt, int pos, int len) {
        ContextT.setText(txt.replace('\n','|'));
        
        /* Change color of error to red */
        SimpleAttributeSet set = new SimpleAttributeSet();
        set.addAttribute(StyleConstants.ColorConstants.Foreground, Color.RED);
        ContextT.getStyledDocument().setCharacterAttributes(pos, len, set, true);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        IconPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        ContextT = new javax.swing.JTextPane();
        FindT = new javax.swing.JTextField();
        ReplaceT = new javax.swing.JTextField();
        IgnoreC = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        FindB = new javax.swing.JButton();
        ReplaceB = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        CloseB = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Find & replace");
        IconPanel.setLayout(new java.awt.BorderLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/find.png")));
        jLabel1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(30, 1, 1, 1)));
        IconPanel.add(jLabel1, java.awt.BorderLayout.NORTH);

        getContentPane().add(IconPanel, java.awt.BorderLayout.WEST);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel1.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(4, 1, 4, 1)));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new java.awt.GridLayout(3, 1));

        jPanel3.add(jLabel4);

        jLabel2.setText(_("Find"));
        jPanel3.add(jLabel2);

        jLabel3.setText(_("Replace with") + "  ");
        jPanel3.add(jLabel3);

        jPanel2.add(jPanel3, java.awt.BorderLayout.WEST);

        jPanel4.setLayout(new java.awt.GridLayout(3, 1));

        ContextT.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.background"));
        ContextT.setBorder(new javax.swing.border.EtchedBorder());
        ContextT.setEditable(false);
        ContextT.setToolTipText(_("The context of the found text"));
        jPanel4.add(ContextT);

        FindT.setColumns(20);
        FindT.setToolTipText(_("What to search for"));
        jPanel4.add(FindT);

        ReplaceT.setToolTipText(_("Replace found text with this"));
        jPanel4.add(ReplaceT);

        jPanel2.add(jPanel4, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.CENTER);

        IgnoreC.setText(_("Ignore case"));
        IgnoreC.setToolTipText("Ignore the case of the found text");
        jPanel1.add(IgnoreC, java.awt.BorderLayout.SOUTH);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel5.setBorder(new javax.swing.border.EmptyBorder(new java.awt.Insets(5, 3, 3, 3)));
        jPanel6.setLayout(new java.awt.GridLayout(0, 1));

        FindB.setText(_("Find"));
        FindB.setToolTipText(_("Find the next occurence of the searched text"));
        FindB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FindBActionPerformed(evt);
            }
        });

        jPanel6.add(FindB);

        ReplaceB.setText(_("Replace"));
        ReplaceB.setToolTipText(_("Replace the found text and find the next occurence of the searched text"));
        ReplaceB.setEnabled(false);
        ReplaceB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReplaceBActionPerformed(evt);
            }
        });

        jPanel6.add(ReplaceB);

        jPanel6.add(jSeparator1);

        CloseB.setText(_("Close"));
        CloseB.setToolTipText(_("Close this dialog box"));
        CloseB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseBActionPerformed(evt);
            }
        });

        jPanel6.add(CloseB);

        jPanel5.add(jPanel6, java.awt.BorderLayout.NORTH);

        getContentPane().add(jPanel5, java.awt.BorderLayout.EAST);

        pack();
    }
    // </editor-fold>//GEN-END:initComponents
    
    private void ReplaceBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReplaceBActionPerformed
        /* We keep track of the undo list ONLY the first time a change has been done.
         * Then we "forget" this pointer in order to prevent a double
         * insertion of a undo action */
        if ( undo != null ) {
            undo.addUndo(new UndoEntry(subs, _("Replace")));
            undo = null;
        }
        
        SubEntry [] selected = parent.getSelectedSubs();
        
        String repl = ReplaceT.getText();
        String older = subs.elementAt(row).getText();
        String newer = older.substring(0, foundpos) + repl + older.substring(foundpos+length);
        nextpos = foundpos + repl.length();
        subs.elementAt(row).setText(newer);
        
        parent.tableHasChanged(selected);
        findNextWord();
    }//GEN-LAST:event_ReplaceBActionPerformed
    
    private void FindBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FindBActionPerformed
        findNextWord();
    }//GEN-LAST:event_FindBActionPerformed
    
    private void CloseBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseBActionPerformed
        prepareExit();
    }//GEN-LAST:event_CloseBActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseB;
    private javax.swing.JTextPane ContextT;
    private javax.swing.JButton FindB;
    private javax.swing.JTextField FindT;
    private javax.swing.JPanel IconPanel;
    private javax.swing.JCheckBox IgnoreC;
    private javax.swing.JButton ReplaceB;
    private javax.swing.JTextField ReplaceT;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables
    
}
