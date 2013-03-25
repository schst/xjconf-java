package net.schst.XJConf.ext;

import net.schst.XJConf.Extension;
import net.schst.XJConf.GenericTag;
import net.schst.XJConf.Tag;
import net.schst.XJConf.XmlReader;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Basic example to show how extensions may return values.
 *
 * Supported tags are:
 * - add
 *
 * @author Stephan Schmidt
 */
public class Math implements Extension {

    private String namespace = "http://www.schst.net/XJConf/Math";

    public String getNamespace() {
        return namespace;
    }

    public void startElement(XmlReader reader, Tag tag, ClassLoader loader) throws XJConfException {
    }

    public Tag endElement(XmlReader reader, Tag tag, ClassLoader loader) throws XJConfException {

        // add several values
        if (tag.getName().equals("add")) {
            double result = 0;
            for (Tag child : tag.getChildren()) {
                result = result + Double.parseDouble(child.getConvertedValue(loader).toString());
            }
            GenericTag resultTag = new GenericTag(tag.getName());
            resultTag.setValue(Double.valueOf(result));
            return resultTag;
        }
        return null;
    }

}
