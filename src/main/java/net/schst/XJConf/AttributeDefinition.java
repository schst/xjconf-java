package net.schst.XJConf;

import net.schst.XJConf.exceptions.MissingAttributeException;
import net.schst.XJConf.exceptions.ValueConversionException;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Definition container of an attribute.
 *
 * This class is used to store information on how
 * an attribute of a specific tag should be handled.
 *
 * Options include
 * - Type of the Attribute
 * - Default value for non-existent attributes
 * - Setter method to set the attribute
 * - Whether the attribute is required, or not
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de/>
 */
public class AttributeDefinition implements Definition {

    /**
     * Name of the attribute.
     */
    private String name = null;

    /**
     * Type of the attribute.
     */
    private String type = null;

    /**
     * Name of the setter method.
     */
    private String setter = null;

    /**
     * Default value.
     */
    private String defaultValue = null;

    /**
     * Whether the attribute is required.
     */
    private boolean required = false;

    /**
     * Converter used to convert the attribute.
     */
    private ValueConverter vConverter;

    /**
     * create a new attribute definition for a String attribute.
     *
     * @param    name of the attribute
     */
    public AttributeDefinition(String name) throws XJConfException {

        if (name == null) {
            throw new XJConfException("TagDefinition needs a name.");
        }
        this.name = name;
        type = "java.lang.String";
        vConverter = new ObjectValueConverter(type);
    }

    /**
     * Create a new AttributeDefinition for any other type.
     *
     * @param    name of the attribute
     * @param    type of the attribute
     */
    public AttributeDefinition(String name, String type) throws XJConfException {
        if (name == null) {
            throw new XJConfException("AttributeDefinition needs a name.");
        }
        if (type == null) {
            throw new XJConfException("AttributeDefinition needs a type.");
        }

        this.name = name;
        type = type;

        if (type.indexOf(".") == -1) {
            vConverter = new PrimitiveValueConverter(type);
        } else {
            vConverter = new ObjectValueConverter(type);
        }
    }

    /**
     * Set the default value for the attribute.
     *
     * @param    aDefaultValue   default value that will be used, if a tag does not provide the attribute
     * @see      getDefault()
     */
    public void setDefault(String aDefaultValue) {
        defaultValue = aDefaultValue;
    }

    /**
     * Get the default value of the attribute.
     *
     * @return    default value of the attribute
     * @see       setDefault()
     */
    public String getDefault() {
        return defaultValue;
    }

    /**
     * @return Returns the required.
     */
    public boolean isRequired() {
        return required;
    }
    /**
     * @param required The required to set.
     */
    public void setRequired(boolean required) {
        required = required;
    }

    /**
     * Set the setter method.
     *
     * If no setter method is specified, the standard
     * name "setAttributename()" will be used instead.
     *
     * @param    aSetter    name of the setter method
     * @see      getSetterMethod()
     */
    public void setSetterMethod(String aSetter) {
        setter = aSetter;
    }

    /**
     * Get the name of the setter method that should be used
     * to set the attribute value in the parent container.
     *
     * @return   name of the setter method
     * @see      setSetterMethod()
     */
    public String getSetterMethod() {
        if (setter == null) {
            return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return setter;
    }

    /**
     * Get the name of the attribute.
     *
     * @return   name of the attribute
     */
    public String getName() {
        return name;
    }

    /**
     * Get the type of the attribute.
     *
     * @return  Class object
     */
    public Class<?> getValueType(Tag tag, ClassLoader loader) {
        try {
            return vConverter.getType(loader);
        } catch (Exception e) {
            throw new RuntimeException("Could not return type.");
        }
    }

    /**
     * Get the type of the attribute.
     *
     * @return   type of the attribute
     */
    public String getType() {
        return type;
    }

    /**
     * Convert a value to the defined type.
     *
     * The value you pass in will be cast to a
     * String before it is converted to the defined
     * type.
     *
     * The type of the returned value can be specified in
     * the constructor using the type argument.
     *
     * @param    val   value to convert
     * @return         converted value
     * @throws ValueConversionException
     */
    public Object convertValue(Tag tag, ClassLoader loader) throws ValueConversionException {
        String value;

        if (tag.hasAttribute(getName())) {
            value = tag.getAttribute(name);
        } else {
            value = getDefault();
        }

        if (value == null) {
            if (isRequired()) {
                throw new MissingAttributeException("The attribute '" + name + "' is required for the tag '"
                        + tag.getName() + "'.");
            }
            // it's no use to create an instance of a class passing null
            // to the constructor. This will at least fail with Integers!
            return null;
        }
        Class<?>[] paramTypes = {String.class};
        String[] params = {value};

        Object instance = vConverter.convertValue(params, paramTypes, loader);
        return instance;
    }

    /**
     * Add a child definition.
     *
     * Attributes cannot have any children.
     */
    public void addChildDefinition(Definition def) throws Exception {
    }

}
