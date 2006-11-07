/*
 * JWavePreview.java
 *
 * Created on October 3, 2005, 4:14 PM
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

package com.panayotis.jubler.preview;
import static com.panayotis.jubler.preview.JFramePreview.DT;

import com.panayotis.jubler.preview.JSubTimeline.SubInfo;
import com.panayotis.jubler.preview.decoders.AbstractDecoder;
import com.panayotis.jubler.preview.decoders.AudioCache;
import com.panayotis.jubler.subs.SubEntry;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author teras
 */
public class JWavePreview extends JPanel {
    
    private static final AudioCache demoaudio = new AudioCache(1, 1000);
    
    private final static Color [] background = {new Color(0,20,0), new Color(0,20,20)};
    private final static Color bordercolor = Color.WHITE;
    private final static Color basecolor = Color.LIGHT_GRAY;
    
    private WavePanel[] panels;
    
    private final JSubTimeline timeline;
    
    private AbstractDecoder decoder;
    private AudioCache audio;
    
    
    private JAudioLoader loader;
    
    private double start_time = -1, end_time = -1;
    
    /** Creates a new instance of JWavePreview */
    public JWavePreview(AbstractDecoder decoder, JSubTimeline tline) {
        this.decoder = decoder;
        timeline = tline;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        loader = new JAudioLoader(decoder);
        
        addMouseMotionListener( new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                timeline.mouseStillDragging(e);
            }
            public void mouseMoved(MouseEvent e)  {
                timeline.mouseUpdateCursor(e);
            }
        });
        addMouseListener( new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                timeline.mouseStartsDragging(e);
            }
            public void mouseReleased(MouseEvent e) {
                timeline.mouseStopsDragging(e);
            }
        });
        
    }
    
    
    public Dimension getPreferredSize() { return new Dimension(500, 200); }
    public Dimension getMinimumSize() { return new Dimension(500, 50); }
    
    
    public void startCacheCreation() {
        add(loader);
        loader.setVisible(true);
        loader.setValue(0);
        setEnabled(false);
    }
    
    public void stopCacheCreation() {
        remove(loader);
        loader.setVisible(false);
        setEnabled(true);
    }
    
    public void updateCacheCreation(float state) {
        loader.setValue((int)(state*100));
    }
    
    public void cleanUp() {
        decoder.forgetAudioCache();
    }
    
    public void setAudiofile(File vfile, File afile, File cfile) {
        /* We do two things at once: 1) start creating of cahce files, 2) update the filename of the JAudioLOader */
        loader.setFilename(decoder.setAudiofile(vfile, afile, cfile, this));
        updateWave();
    }
    
    
    public void setTime(double start, double end) {
        if ( Math.abs(start-start_time) < DT && Math.abs(end-end_time) < DT ) {
            repaint();
            return;
        }
        start_time = start;
        end_time = end;
        updateWave();
    }
    
    
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        updateWave();
    }
    
    private void updateWave() {
        if (isEnabled()) audio = decoder.getAudioCache(start_time, end_time);
        else audio = null;
        if (audio==null) audio = demoaudio;
        
        /* Remove old panels */
        if (panels!=null) {
            for (int i = 0 ; i < panels.length ; i++) {
				if (panels[i]!=null) remove(panels[i]);
            }
        }
        /* Create new panels */
        panels = new WavePanel[audio.channels()];
        for (int i = 0 ; i < panels.length ; i++) {
            panels[i] = new WavePanel(audio.getChannel(i), background[i%2]);
            add(panels[i]);
        }
        validate();
    }
    
    public void playbackWave() {
        if (timeline.getSelectedList().size() < 1 ) return;
        
        int which = timeline.getSelectedList().get(0).pos;
        SubEntry entry = timeline.getJubler().getSubtitles().elementAt(which);
        decoder.playAudioClip(entry.getStartTime().toSeconds(), entry.getFinishTime().toSeconds());
    }
    
    
    class WavePanel extends JPanel {
        private float [][] data;
        private Color c;
        
        public WavePanel(float[][] data, Color c) {
            this.data = data;
            this.c = c;
        }
        
        public void paintComponent(Graphics g) {
            int width = getWidth();
            int height = getHeight();
            
            /* Draw channel box */
            g.setColor(c);
            g.fillRect(0, 0, width-1, height-1);
            
            /* Draw selected boxes */
            if (timeline != null ) {
                g.setColor(JSubTimeline.SelectColor);
                for (SubInfo i: timeline.getSelectedList()) {
                    double sstart, send;
                    if (i.start > i.end) {
                        sstart = i.end;
                        send = i.start;
                    } else {
                        sstart = i.start;
                        send = i.end;
                    }
                    g.fill3DRect((int)(sstart*width),0,(int)((send-sstart)*width),height, false);
                }
            }
            
            /* Draw lines */
            g.setColor(bordercolor);
            g.drawRect(0, 0, width-1, height-1);
            g.setColor(basecolor);
            g.drawLine(1, height/2, width-1, height/2);
            
            int x1, x2, y1, y2, yswap;
            float factor = ((float)width) / data.length;
            for (int i = 0 ; i < data.length ; i++) {
                x1 = (int)(factor*i);
                x2 = (int)(factor*(i+1));
                y1 = (int)(height*data[i][0]);
                y2 = (int)(height*data[i][1]);
                if (y1>y2) {
                    yswap = y1;
                    y1 = y2;
                    y2 = yswap;
                }
                g.fillRect( x1, y1, x2-x1, y2-y1);
            }
        }
        
    }
}