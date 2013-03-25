package net.schst.XJConf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import net.schst.XJConf.exceptions.ValueConversionException;

import org.xml.sax.Attributes;

/**
 * Generic Tag wrapper that can be used by extensions to dynamically add
 * children to other tags.
 *
 * @author Stephan Schmidt <me@schst.net/>
 */
public class GenericTag implements Tag {

    /**
     * Name of the tag.
     */
    private String name = null;

    /**
     * Character data.
     */
    private StringBuffer data = new StringBuffer();

    /**
     * Content of the tag (overrides data).
     */
    private Object content = null;

    /**
     * Attributes of the tag.
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
     * Creates a new tag without attributes.
     *
     * @param name   name of the tag
     */
    public GenericTag(String name) {
        this.name = name;
    }

    /**
     * Creates a new tag with attributes.
     *
     * @param name   name of the tag
     * @param attributes   attributes of the tag
     */
    public GenericTag(String name, Attributes attributes) {
        this.name = name;

        // store attributes in a HashMap
        for (int i = 0; i < attributes.getLength(); i++) {
            atts.put(attributes.getQName(i), attributes.getValue(i));
        }
    }

    /**
     * Adds text data.
     */
    public int addData(char[] buf, int offset, int len) {
        String s = new String(buf, offset, len);
        data.append(s);
        return data.length();
    }

    /**
     * Checks whether the tag has a certain attribute.
     *
     * @param aName
     * @return true or false
     */
    public boolean hasAttribute(String aName) {
        return atts.containsKey(aName);
    }

    /**
     * Gets an attribute.
     *
     * @param    attName of the attribute
     * @return   value of the attribute
     */
    public String getAttribute(String attName) {
        return atts.get(attName);
    }

    /**
     * Gets all children of the tag.
     *
     * @return   children
     */
    public ArrayList<Tag> getChildren() {
        return children;
    }

    /**
     * Gets the child with a specific name.
     *
     * @param aName
     * @return The specified child tag.
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
     * Gets the name of the tag.
     *
     * @return name of the tag
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the character data of the tag.
     *
     * @return   character data
     */
    public String getData() {
        return data.toString().trim();
    }

    /**
     * Adds a new child to this tag.
     *
     * @param child  child to add
     * @return       number of children
     */
    public int addChild(Tag child) {
        children.add(child);
        return children.size();
    }

    /**
     * Fetches the value.
     *
     * @param loader
     * @return the value of the tag
     */
    public Object getConvertedValue(ClassLoader loader) throws ValueConversionException {
        return value;
    }

    /**
     * Gets the key under which the value will be stored.
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the tag.
     *
     * @param value
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Gets the type of the value.
     */
    public Class<?> getValueType(Tag tag, ClassLoader loader) {
        if (value == null) {
            return null;
        }
        return value.getClass();
    }

    /**
     * Sets the key.
     *
     * @param key
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the setter method.
     */
    public String getSetterMethod() {
        if (key == null) {
            return null;
        }
        return "set" + key.substring(0, 1).toUpperCase(Locale.ENGLISH) + key.substring(1);
    }

    /**
     * Sets the content (overrides the character data).
     */
    public void setContent(Object content) {
        this.content = content;
    }

    /**
     * Gets the content.
     */
    public Object getContent() {
        if (content != null) {
            return content;
        }
        return getData();
    }

    /**
     * Checks whether the tag supports indexed children.
     */
    public boolean supportsIndexedChildren() {
        return true;
    }

    public boolean validate() {
        return true;
    }

}
