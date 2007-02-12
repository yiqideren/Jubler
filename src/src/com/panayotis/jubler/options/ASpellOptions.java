/*
 * ASpellOptions.java
 *
 * Created on 16 Ιούλιος 2005, 4:17 μμ
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
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Vector;

import static com.panayotis.jubler.i18n.I18N._;/**
 *
 * @author  teras
 */
import java.io.File;
import java.util.ArrayList;
public class ASpellOptions extends ExtOptions {
    
    private ASpellDict language;
    Vector<ASpellDict> dictionaries;
    
    /** Creates new form ASpellOptions */
    public ASpellOptions(String type, String name, String programname) {
        super(type, name, programname);
        initComponents();
        
        dictionaries = new Vector<ASpellDict>();
        add(Visuals, BorderLayout.SOUTH);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        LangList = new javax.swing.JList();

        setLayout(new java.awt.BorderLayout());

        jLabel1.setText(_("Language to use"));
        add(jLabel1, java.awt.BorderLayout.NORTH);

        jScrollPane1.setViewportView(LangList);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

    }
    // </editor-fold>//GEN-END:initComponents
    
    public void updateOptions() {
        populateDictionaries();       // shouldn't needed any more - this method is called from Aspell.getOptionsPanel();
        LangList.setListData(dictionaries);
        
        /* Load preferred language */
        setSelectedLanguage( Options.getOption(type + "." + name + ".Language", "en") );
    }
    
    
    /* Find the selected language */
    private void setSelectedLanguage(String lng) {
        ASpellDict current;
        for (int i = 0 ; i < dictionaries.size() ; i++) {
            current = dictionaries.get(i);
            if (current.lang.equals(lng)) {
                LangList.setSelectedIndex(i);
                LangList.ensureIndexIsVisible(i);
                language = current;
            }
        }
    }
    
    public void populateDictionaries() {
        /* load dictionaries list from aspell */
        dictionaries.removeAllElements();
        getDictsFromPath(null);
        
        File cocoaspell = new File("/Library/Application Support/cocoAspell");
        if (cocoaspell.exists() && cocoaspell.isDirectory()) {
            getDictsFromPath(cocoaspell);
            File [] childs = cocoaspell.listFiles();
            for (int i = 0 ; i < childs.length ; i++ ) {
                if (childs[i].isDirectory())
                    getDictsFromPath(childs[i]);
            }
        }
    }
    
    private void getDictsFromPath( File path ) {
        ArrayList<String> cmd = new ArrayList<String>();
        cmd.add(getExecFileName());
        cmd.add("dicts");
        if (path!=null && path.exists()) cmd.add("--dict-dir="+path);
        try {
            Process proc = Runtime.getRuntime().exec(cmd.toArray(new String[1]));
            BufferedReader get = new BufferedReader( new InputStreamReader(proc.getInputStream()));
            
            String d;
            while ( (d=get.readLine()) != null ) {
                dictionaries.add(new ASpellDict(d, path));
            }
        } catch (IOException e) {
        }
    }
    
    
    public void saveOptions() {
        super.saveOptions();
        
        int which = LangList.getSelectedIndex();
        if ( which < 0 ) language = null;
        else language = (ASpellDict)LangList.getModel().getElementAt(which);
        
        Options.setOption(type + "." + name + ".Language", language.lang);
        Options.saveOptions();
    }
    
    public void resetOptions() {
        super.resetOptions();
        LangList.setSelectedValue(language, true);
    }
    
    public ASpellDict getLanguage() {
        return language;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList LangList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
    
    
    public class ASpellDict {
        public String lang;
        public String path;
        public ASpellDict(String lang, File path) {
            this.lang = lang;
            if (path!=null) this.path = path.getPath();
            else this.path = null;
        }
        
        public String toString() {
            return lang;
        }
    }
    
}


