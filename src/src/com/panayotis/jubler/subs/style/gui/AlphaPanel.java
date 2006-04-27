/*
 * AlphaPanel.java
 *
 * Created on 7 Σεπτέμβριος 2005, 7:24 μμ
 */

package com.panayotis.jubler.subs.style.gui;

import com.panayotis.jubler.options.SystemDependent;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.UIManager;

/**
 *
 * @author  teras
 */
public class AlphaPanel extends javax.swing.JPanel {
    AlphaColor basecolor;
    
    /** Creates new form AlphaPanel */
    public AlphaPanel(AlphaColor c) {
        initComponents();
        basecolor = c;
    }
    
    
    public void setAlphaColor(AlphaColor c) {
        basecolor = c;
        repaint();
    }
    
    public void setColorOnly(Color c) {
        basecolor = new AlphaColor(c, basecolor.getAlpha());
        repaint();
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(512+SystemDependent.getSliderLOffset()+SystemDependent.getSliderROffset(), 48);
    }
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }
    
    
    public void paint(Graphics g) {
        int y = getHeight();
        
        g.setColor(UIManager.getDefaults().getColor("Panel.background"));
        g.fillRect(0, 0, getWidth(), getHeight());
        Color c1, c2, cswap;
        int dx = SystemDependent.getSliderLOffset();
        for (int i = 0 ; i < 256 ; i++) {
            c1 = basecolor.getMixed(Color.GRAY, i);
            c2 = basecolor.getMixed(Color.DARK_GRAY, i);
            if ( ((i/4)%2) == 1 ) {
                cswap = c1;
                c1 = c2;
                c2 = cswap;
            }
            for (int j = 0 ; j < 3 ; j++ ) {
                g.setColor(c1);
                g.fillRect(i*2+dx, j*16, 2, 8);
                g.setColor(c2);
                g.fillRect(i*2+dx, j*16+8, 2, 8);
            }
        }
    }
    
        /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {

    }
    // </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
