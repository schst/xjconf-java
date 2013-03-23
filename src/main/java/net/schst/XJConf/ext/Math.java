package net.schst.XJConf.ext;

import java.util.ArrayList;

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
        return this.namespace;
    }

    public void startElement(XmlReader reader, Tag tag, ClassLoader loader) throws XJConfException {
    }

    public Tag endElement(XmlReader reader, Tag tag, ClassLoader loader) throws XJConfException {

        // add several values
        if (tag.getName().equals("add")) {
            double result = 0;

            ArrayList<Tag> children = tag.getChildren();
            Tag child;
            for (int i = 0; i < children.size(); i++) {
                child = children.get(i);
                result = result + Double.parseDouble(child.getConvertedValue(loader).toString());
            }
            GenericTag resultTag = new GenericTag(tag.getName());
            resultTag.setValue(Double.valueOf(result));
            return resultTag;
        }
        return null;
    }

}
