/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.panayotis.updater.list;

import com.panayotis.updater.UpdaterException;
import com.panayotis.updater.html.UpdaterHTMLCreator;
import com.panayotis.updater.html.DefaultHTMLCreator;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author teras
 */
public class UpdaterXMLHandler extends DefaultHandler {

    private UpdaterAppElements elements; // Location to store various application elements, needed in GUI
    private Arch arch;  // The stored architecture of the running system - null if unknown
    private Version latest; // The list of the latest files, in order to upgrade
    private Version current;    // The list of files for the current reading "version" object
    private boolean ignore_version; // true, if this version is too old and should be ignored
    private StringBuffer descbuffer;    // Temporary buffer to store descriptions
    private UpdaterHTMLCreator display; // Store version updated
    private boolean distributionBased = false;
    // true:  Some files can be ignored, if they are taken care by a distribution
    // false: All files should be  updated
    public UpdaterXMLHandler(String current_release, String current_version, String app_home, boolean distributionBased) { // We are interested only for version "current_version" onwards
        elements = new UpdaterAppElements(current_release, current_version, app_home);
        this.distributionBased = distributionBased;
        ignore_version = false;
        display = new DefaultHTMLCreator();
    }

    public void startElement(String uri, String localName, String qName, Attributes attr) {
        if (qName.equals("alias")) {
            Arch a = new Arch(attr.getValue("tag"), attr.getValue("os"), attr.getValue("arch"));
            if (a.isCurrent())
                arch = a;
        } else if (qName.equals("version")) {
            int release_last = Integer.parseInt(attr.getValue("id"));
            String version_last = attr.getValue("release");
            elements.updateVersion(release_last, version_last);
            ignore_version = release_last <= elements.getCurRelease();
        } else if (qName.equals("description")) {
            descbuffer = new StringBuffer();
        } else if (qName.equals("arch")) {
            if (ignore_version)
                return;
            String tag = attr.getValue("name");
            if (arch != null && arch.isTag(tag) || (arch == null && tag.equals("any"))) {
                // We have found the correct arch OR we are using generic tag, if nothing is found
                current = new Version();
            }
        } else if (qName.equals("file")) {
            if (shouldIgnore(attr.getValue("forceinstall")))
                return;
            FileAdd f = new FileAdd(attr.getValue("name"), attr.getValue("sourcedir"), attr.getValue("destdir"), elements);
            current.put(f.getHash(), f);
        } else if (qName.equals("rm")) {
            if (shouldIgnore(attr.getValue("forceinstall")))
                return;
            FileRm f = new FileRm(attr.getValue("name"), attr.getValue("destdir"), elements);
            current.put(f.getHash(), f);
        } else if (qName.equals("updatelist")) {
            elements.setBaseURL(attr.getValue("baseurl"));
            elements.setAppName(attr.getValue("application"));
            elements.setIconpath(attr.getValue("icon"));
        }
    }

    private boolean shouldIgnore(String force) {
        if(ignore_version)
            return true;
        if (current==null)
            return true;
        
        if (!distributionBased)
            return false;
        if (force != null) {
            force = force.toLowerCase().trim();
            if (force.equals("true") || force.equals("yes") || force.equals("1"))
                return false;
        }
        return true;
    }

    public void endElement(String uri, String localName, String qName) {
        if (qName.equals("arch")) {
            if (latest == null) {
                latest = current;
            } else {
                latest.merge(current);
            }
            current = null;
        } else if (qName.equals("version")) {
            ignore_version = false;
        } else if (qName.equals("description")) {
            if (ignore_version)
                return;
            display.addInfo(elements.getLastVersion(), descbuffer.toString());
        }
    }

    public void endDocument() {
        elements.setHTML(display.getHTML());
    }

    public void characters(char[] ch, int start, int length) {
        if (ignore_version)
            return;
        if (descbuffer == null)
            return;
        String info = new String(ch, start, length).trim();
        if (info.equals(""))
            return;
        descbuffer.append(info);
    }

    UpdaterAppElements getAppElements() {
        return elements;
    }

    Version getVersion() {
        return latest;
    }
}
