package net.schst.XJConf.ext;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import net.schst.XJConf.Extension;
import net.schst.XJConf.Tag;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.UnknownTagException;
import net.schst.XJConf.ext.xinc.XIncludeException;
import net.schst.XJConf.io.ResourceSource;
import net.schst.XJConf.io.Source;
import net.schst.XJConf.io.URLSource;

/**
 * Very basic xInclude mechanism.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class XInclude implements Extension {

    /**
     * Namespace of the extension.
     */
    private String namespace = "http://www.w3.org/2001/XInclude";

    /**
     * Get the namspace URI.
     */
    public String getNamespace() {
        return namespace;
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
     * @throws IOException
     */
    public Tag endElement(XmlReader reader, Tag tag, ClassLoader loader) throws XIncludeException, UnknownTagException {

        if (tag.getName().equals("include")) {
            String href = tag.getAttribute("href");
            if (href == null) {
                return null;
            }

            Source includedSource = resolveSource(reader, href, loader);
            try {
                reader.parse(includedSource);
                return null;
            } catch (Exception e) {
                throw new XIncludeException("Could not xInclude " + href, e);
            }
        }
        throw new UnknownTagException("Unknown tag " + tag.getName() + " in XInclude namespace.");
    }

    protected Source resolveSource(XmlReader reader, String href, ClassLoader loader) throws XIncludeException {
        URI hrefUri;
        try {
            hrefUri = new URI(href);
        } catch (URISyntaxException e) {
            throw new XIncludeException(e);
        }
        hrefUri = hrefUri.normalize();

        Source result;
        if (hrefUri.isAbsolute()) {
            if (hrefUri.getScheme().equals("classpath")) {
                result = new ResourceSource(hrefUri, loader);
            } else {
                try {
                    result = new URLSource(hrefUri.toURL());
                } catch (IOException e) {
                    throw new XIncludeException(e);
                }
            }
        } else {
            Source currentSource = reader.getCurrentSource();
            if (currentSource == null) {
                if (loader == null) {
                    loader = getClass().getClassLoader();
                }
                result = new ResourceSource(hrefUri, loader);
            } else {
                try {
                    result = currentSource.createRelative(hrefUri.toString());
                } catch (IOException e) {
                    throw new XIncludeException(e);
                }
            }
        }
        return result;
    }

}
