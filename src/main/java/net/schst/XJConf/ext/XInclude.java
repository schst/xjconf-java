package net.schst.XJConf.ext;

import java.io.File;

import net.schst.XJConf.Extension;
import net.schst.XJConf.Tag;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.UnknownTagException;
import net.schst.XJConf.ext.xinc.XIncludeException;

/**
 * Very basic xInclude mechanism
 * 
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class XInclude implements Extension {

    /**
     * Namespace of the extension
     */
    private String namespace = "http://www.w3.org/2001/XInclude";

    /**
     * Get the namspace URI 
     */
    public String getNamespace() {
        return this.namespace;
    }

    /**
     * Handle an opening tag.
     * 
     * Currently this does not do anything.
     * 
     * Future versions should check, whether the file exists and skip all
     * child elements.
     */
    public void startElement(XmlReader reader, Tag tag, ClassLoader loader) {
    }

    /**
     * Handle a closing tag.
     * 
     * Does the actual x-include.
     */
    public Tag endElement(XmlReader reader, Tag tag, ClassLoader loader)
    	throws XIncludeException, UnknownTagException {
    	
    	if (tag.getName().equals("include")) {
    		
            String href = tag.getAttribute("href");
            if (href == null) {
                return null;
            }
            
            if (!href.startsWith("/")) {
                File current = reader.getCurrentFile().getAbsoluteFile();
                href = current.getParent() + "/" + href;
            }
            try {
                reader.parse(href);
                return null;
            } catch (Exception e) {
                throw new XIncludeException("Could not xInclude " + href, e);
            }    		
    	}
        throw new UnknownTagException("Unknown tag " + tag.getName() + " in XInclude namespace.");
    }
}
