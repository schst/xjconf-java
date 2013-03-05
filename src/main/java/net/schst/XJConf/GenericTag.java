package net.schst.XJConf;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;

import net.schst.XJConf.exceptions.ValueConversionException;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Generic Tag wrapper that can be used by extensions to dynamically add
 * children to other tags.
 *
 * @author Stephan Schmidt <me@schst.net/>
 */
public class GenericTag implements Tag {

    /**
     * name of the tag.
     */
    private String name = null;

    /**
     * character data.
     */
    private StringBuffer data = new StringBuffer();

    /**
     * Content of the tag (overrides data).
     */
    private Object content = null;

    /**
     * attributes of the tag.
     */
    private HashMap<String, String> atts = new HashMap<String, String>();

    /**
     * Children of the tag.
     */
    private ArrayList<Tag> children = new ArrayList<Tag>();

    /**
     * value of the tag.
     */
    private Object value = null;

    /**
     * Key of the tag.
     */
    private String key = null;

    /**
     * Create a new tag without attributes.
     *
     * @param name   name of the tag
     */
    public GenericTag(String name) {
        this.name = name;
    }

    /**
     * Create a new tag with attributes.
     *
     * @param name   name of the tag
     * @param atts   attributes of the tag
     */
    public GenericTag(String name, Attributes atts) {
        this.name = name;

        // store attributes in a HashMap
        for (int i = 0; i < atts.getLength(); i++) {
            this.atts.put(atts.getQName(i), atts.getValue(i));
        }
    }

    /**
     * Add text data.
     */
    public int addData(char[] buf, int offset, int len) {
        String s = new String(buf, offset, len);
        data.append(s);
        return data.length();
    }

    /**
     * Check, whether the tag has a certain attribute.
     *
     * @param aName
     * @return
     */
    public boolean hasAttribute(String aName) {
        return atts.containsKey(aName);
    }

    /**
     * get an attribute.
     *
     * @param    aName of the attribute
     * @return   value of the attribute
     */
    public String getAttribute(String aName) {
        return (String) atts.get(aName);
    }

    /**
     * Get all children of the tag.
     *
     * @return   children
     */
    public ArrayList<Tag> getChildren() {
        return children;
    }

    /**
     * Get the child with a specific name.
     *
     * @param aName
     * @return
     */
    public Tag getChild(String aName) {
        for (Tag child : children) {
            if (child.getName().equals(aName)) {
                return child;
            }
        }
        return null;
    }

    /**
     * Get the name of the tag.
     *
     * @return   name of the tag
     */
    public String getName() {
        return name;
    }

    /**
     * Get the character data of the tag.
     *
     * @return   character data
     */
    public String getData() {
        return data.toString().trim();
    }

    /**
     * Add a new child to this tag.
     *
     * @param child  child to add
     * @return       number of childs added
     */
    public int addChild(Tag child) {
        children.add(child);
        return children.size();
    }

    /**
     * Fetch the value.
     *
     * @param loader
     * @retun the value of the tag
     */
    public Object getConvertedValue(ClassLoader loader) throws ValueConversionException {
        return value;
    }

    /**
     * Get the key under which the value will be stored.
     */
    public String getKey() {
        return key;
    }

    /**
     * Set the value of the tag.
     *
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Get the type of the value.
     */
    public Class<?> getValueType(Tag tag, ClassLoader loader) {
        if (value == null) {
            return null;
        }
        return value.getClass();
    }

    /**
     * Set the key.
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Get the setter method.
     */
    public String getSetterMethod() {
        if (key == null) {
            return null;
        }
        return "set" + key.substring(0, 1).toUpperCase() + key.substring(1);
    }

    /**
     * Set the content (overrides the character data).
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * Get the content.
     */
    public Object getContent() {
        if (content != null) {
            return content;
        }
        return getData();
    }

    /**
     * Checks, whether the tag supports indexed children.
     */
    public boolean supportsIndexedChildren() {
        return true;
    }

    public boolean validate() throws XJConfException {
        return true;
    }
}
