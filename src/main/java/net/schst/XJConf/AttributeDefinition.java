package net.schst.XJConf;

import net.schst.XJConf.exceptions.MissingAttributeException;
import net.schst.XJConf.exceptions.ValueConversionException;
import net.schst.XJConf.exceptions.XJConfException;

/**
 * Definition container of an attribute.
 *
 * This class is used to store information on how an attribute of a specific tag should be handled.
 *
 * Options include - Type of the Attribute - Default value for non-existent attributes - Setter method to set the
 * attribute - Whether the attribute is required, or not
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
     * Creates a new AttributeDefinition for a String attribute.
     *
     * @param name
     *            The attribute's name.
     */
    public AttributeDefinition(final String name) throws XJConfException {

        if (name == null) {
            throw new XJConfException("TagDefinition needs a name.");
        }
        this.name = name;
        this.type = "java.lang.String";
        vConverter = new ObjectValueConverter(this.type);
    }

    /**
     * Creates a new AttributeDefinition for specified type.
     *
     * @param name
     *            The attribute's name.
     * @param type
     *            The attribute's type.
     */
    public AttributeDefinition(final String name, final String type) throws XJConfException {
        if (name == null) {
            throw new XJConfException("AttributeDefinition needs a name.");
        }
        if (type == null) {
            throw new XJConfException("AttributeDefinition needs a type.");
        }

        this.name = name;
        this.type = type;

        if (this.type.indexOf(".") == -1) {
            this.vConverter = new PrimitiveValueConverter(this.type);
        } else {
            this.vConverter = new ObjectValueConverter(this.type);
        }
    }

    /**
     * Sets the attribute's default value to be used when a tag does not provide the attribute.
     *
     * @param aDefaultValue
     *            The attribute's default value.
     */
    public void setDefault(final String aDefaultValue) {
        defaultValue = aDefaultValue;
    }

    /**
     * Gets the attribute's default value.
     *
     * @return The attribute's default value.
     */
    public String getDefault() {
        return defaultValue;
    }

    /**
     * Denotes wether the attribute is required.
     *
     * @return The attribute's required state.
     */
    public boolean isRequired() {
        return required;
    }

    /**
     * Sets the attribute's required state.
     *
     * @param isRequired
     *            The attribute's required state.
     */
    public void setRequired(final boolean isRequired) {
        required = isRequired;
    }

    /**
     * Sets the setter method.
     *
     * If no setter method is specified, the standard name "setAttributename()" will be used instead.
     *
     * @param aSetter
     *            The name of the setter method.
     */
    public void setSetterMethod(final String aSetter) {
        setter = aSetter;
    }

    /**
     * Gets the name of the setter method to be used to set the attribute value in the parent container.
     *
     * @return The name of the setter method.
     */
    public String getSetterMethod() {
        if (this.setter == null) {
            return "set" + this.name.substring(0, 1).toUpperCase() + this.name.substring(1);
        }
        return this.setter;
    }

    /**
     * Gets the name of the attribute.
     *
     * @return The attribute's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the type of the attribute.
     *
     * @param tag
     *            The tag this attribute belongs to.
     * @param loader
     *            A Classloader to be passed to the ValueConverter.
     * @return Class object denoting the attributes type.
     */
    public Class<?> getValueType(final Tag tag, final ClassLoader loader) {
        try {
            return vConverter.getType(loader);
        } catch (Exception e) {
            throw new RuntimeException("Could not return type.", e);
        }
    }

    /**
     * Gets the type of the attribute.
     *
     * @return The attribute's type as String.
     */
    public String getType() {
        return type;
    }

    /**
     * Converts a value to the defined type.
     *
     * The value you pass in will be cast to a String before it is converted to the defined type.
     *
     * The type of the returned value can be specified in the constructor using the type argument.
     *
     * @param tag
     *            The tag this attribute belongs to.
     * @param loader
     *            A Classloader to be passed to the ValueConverter.
     * @return The converted value.
     * @throws ValueConversionException
     */
    public Object convertValue(final Tag tag, final ClassLoader loader) throws ValueConversionException {
        String value;

        if (tag.hasAttribute(name)) {
            value = tag.getAttribute(name);
        } else {
            value = getDefault();
        }

        if (value == null) {
            if (isRequired()) {
                throw new MissingAttributeException("The attribute '" + this.name + "' is required for the tag '"
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
     * Does nothing, since attributes cannot have any children.
     */
    public void addChildDefinition(Definition def) throws Exception {
    }

}
