package net.schst.XJConf;

import net.schst.XJConf.exceptions.ValueConversionException;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Definition to access the child of the tag.
 *
 * This can be used to pass the child to the constructor.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de/>
 */
public class ChildDefinition implements Definition {

    /**
     * Name of the child to access.
     */
    private String name;

    /**
     * Create a new child definition.
     *
     * @param name
     */
    public ChildDefinition(String name) throws XJConfException {
        if (name == null) {
            throw new XJConfException("ChildDefinition needs a name.");
        }
        this.name = name;
    }

    /**
     * Get the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Convert the value.
     */
    public Object convertValue(Tag tag, ClassLoader loader) throws ValueConversionException {
        Tag child = tag.getChild(getName());
        if (child == null) {
            throw new RuntimeException("Child element " + getName() + " does not exist");
        }
        return child.getConvertedValue(loader);
    }

    /**
     * Get the type of the attribute.
     *
     * @return  Class object
     */
    public Class<?> getValueType(Tag tag, ClassLoader loader) {
        Tag child = tag.getChild(getName());
        if (child == null) {
            throw new RuntimeException("Child element " + getName() + " does not exist");
        }
        return child.getValueType(tag, loader);
    }

    /**
     * This does not provide a setter method.
     */
    public String getSetterMethod() {
        return null;
    }

    /**
     * It's not possible to add a new child.
     */
    public void addChildDefinition(Definition def) throws Exception {
    }

}
