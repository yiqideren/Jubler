/*
 * JSynchronize.java
 *
 * Created on 4 Μάρτιος 2006, 1:35 πμ
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

package com.panayotis.jubler.tools;
import com.panayotis.jubler.Jubler;
import static com.panayotis.jubler.i18n.I18N._;
import com.panayotis.jubler.subs.SubEntry;
import com.panayotis.jubler.subs.Subtitles;
import java.util.ArrayList;
import java.util.Vector;

/**
 *
 * @author  teras
 */
public class JSynchronize extends JTool {
    
    private ArrayList<Jubler> jublerlist;
    private Subtitles target, model;
    private boolean copytime, copytext;
    private int offset;
    
    /** Creates new form JSynchronize */
    public JSynchronize(Jubler current, Vector<Jubler> list, int[] selected) {
        super(current.getSubtitles(), selected, true);
        
        target = current.getSubtitles();
        
        jublerlist = new ArrayList<Jubler>();
        Jubler cjubler;
        
        int cid = -1;
        for ( int i = 0 ; i <list.size() ; i++) {
            cjubler = list.elementAt(i);
            jublerlist.add(cjubler);
            JubSelector.addItem(cjubler.getFileName()+ ((cjubler==current)?"  "+_("-current-"):"") );
            if (cjubler==current) cid = i;
            
        }
        JubSelector.setSelectedIndex(cid);
    }
    
    
    public void initialize() {
        initComponents();
    }
    
    public String getToolTitle() {
        return _("Synchronize");
    }
    
    public void storeSelections() {
            model = jublerlist.get(JubSelector.getSelectedIndex()).getSubtitles();
        copytime = InTimeS.isSelected();
        copytext = InTextS.isSelected();
        offset = ((Integer)OffsetS.getValue()).intValue();
        
        if (offset<0) { // We have to invert the selected subtitles!
            Vector<SubEntry> inv_affected = new Vector<SubEntry>();
            for (int i = affected_list.size()-1; i >= 0 ; i--) {
                inv_affected.add(affected_list.elementAt(i));
            }
            affected_list = inv_affected;
        }
    }
    
    public void affect(int which) {
        SubEntry to = affected_list.elementAt(which);
        
        int modid = target.indexOf(to)+offset;
        if (modid<0 || modid >= model.size()) return;
        
        SubEntry from = model.elementAt(modid);
        if (copytime) {
            to.setStartTime(from.getStartTime());
            to.setFinishTime(from.getFinishTime());
        }
        if (copytext) to.setText(from.getText());
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        JubSelector = new javax.swing.JComboBox();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        OffsetS = new javax.swing.JSpinner();
        InTimeS = new javax.swing.JCheckBox();
        InTextS = new javax.swing.JCheckBox();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(0, 1));

        jLabel1.setText(_("Synchronize data from the following subtitles"));
        jPanel1.add(jLabel1);

        JubSelector.setToolTipText(_("The subtitles file to use as a model"));
        jPanel1.add(JubSelector);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jLabel2.setText(_("Model subtitles offset"));
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 8));
        jPanel2.add(jLabel2, java.awt.BorderLayout.WEST);

        OffsetS.setToolTipText(_("Relative offset of the model subtitles. It is based on index, not time."));
        jPanel2.add(OffsetS, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2);

        InTimeS.setSelected(true);
        InTimeS.setText(_("Import timestamp"));
        InTimeS.setToolTipText(_("Use the timestamp of the subtitles as the time model for current subtitles"));
        InTimeS.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        InTimeS.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel1.add(InTimeS);

        InTextS.setText(_("Import text"));
        InTextS.setToolTipText(_("Copy the text of the other subtitles into this file"));
        InTextS.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        InTextS.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jPanel1.add(InTextS);

        add(jPanel1, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox InTextS;
    private javax.swing.JCheckBox InTimeS;
    private javax.swing.JComboBox JubSelector;
    private javax.swing.JSpinner OffsetS;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
    
}
