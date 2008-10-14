/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.jubler.information;

import com.panayotis.jubler.StaticJubler;
import com.panayotis.jubler.os.DEBUG;
import com.panayotis.jubler.os.SystemDependent;
import com.panayotis.jupidator.ApplicationInfo;
import com.panayotis.jupidator.Updater;
import com.panayotis.jupidator.UpdaterException;
import com.panayotis.jupidator.UpdaterListener;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author teras
 */
public class AutoUpdater implements UpdaterListener {

    public AutoUpdater() {
        try {
            Properties current = new Properties();
            current.load(Updater.class.getResource("/com/panayotis/jubler/information/version.prop").openStream());

            String URL = "file:////Users/teras/Works/Development/Java/Jubler/resources/system/updater.xml";
            ApplicationInfo ap = new ApplicationInfo("/Users/teras/Works/Development/Java/Jubler/testcase/release");
            ap.setCurrentVersion(current.getProperty("release"), current.getProperty("version"));
            ap.setAppConfigFile(SystemDependent.getConfigPath());       
            //ap.setAppUpdaterFile("/Users/teras/Works/Development/Java/Jubler/testcase/release/config/updater.xml");
            
            ap.setDistributionBased(false);
            Updater upd = new Updater(URL, ap, this);
            
        } catch (IOException ex) {
            DEBUG.debug(ex);
        } catch (UpdaterException ex) {
            DEBUG.debug(ex);
        }
    }

    public boolean requestRestart() {
        return StaticJubler.requestQuit(null);
    }
}
