package net.schst.XJConf;

import java.util.ArrayList;
import net.schst.XJConf.exceptions.ValueConversionException;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Interface for tag containers.
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public interface Tag {
    int addChild(Tag child);
    int addData(char[] buf, int offset, int len);
    void setContent(Object content);
    boolean hasAttribute(String name);
    String getAttribute(String name);
    ArrayList<Tag> getChildren();
    Tag getChild(String name);
    String getName();
    String getData();
    Object getContent();
    Object getConvertedValue(ClassLoader loader) throws ValueConversionException;
    Class<?> getValueType(Tag tag, ClassLoader loader);
    String getKey();
    String getSetterMethod();
    boolean supportsIndexedChildren();
    boolean validate() throws XJConfException;
}
