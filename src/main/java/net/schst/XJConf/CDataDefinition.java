package net.schst.XJConf;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Definition for the character data inside a tag.
 *
 * This is used to pass the character data to the constructor
 * of the tag, while casting it to the desired class.
 *
 * @author Stephan Schmidt
 */
public class CDataDefinition implements Definition {

    /**
     * Type, that is used.
     */
    private String type = null;

    /**
     * Field stores the setter method for the character data.
     */
    private String setter = "setData";

    /**
     * Field stores the converter used to convert the character data.
     */
    private ValueConverter vConverter;

    /**
     * Create a new CDataDefinition for a String.
     */
    public CDataDefinition() {
        type = "java.lang.String";
        vConverter = new ObjectValueConverter(type);
    }

    /**
     * Create a new CDataDefinition for any other type.
     *
     * @param    type of the content
     */
    public CDataDefinition(String type) {
        this.type = type;

        if (!type.contains(".")) {
            vConverter = new PrimitiveValueConverter(type);
        } else {
            vConverter = new ObjectValueConverter(type);
        }
    }

    /**
     * Set the setter method.
     *
     * If no setter method is specified, the standard
     * name "setAttributename()" will be used instead.
     *
     * @param    set    name of the setter method
     * @see      getSetterMethod()
     */
    public void setSetterMethod(String set) {
        setter = set;
    }

    /**
     * Character data cannot have any child definitions.
     */
    public void addChildDefinition(Definition def) throws Exception {
    }

    /**
     * Convert the character data to any type.
     */
    public Object convertValue(Tag tag, ClassLoader loader) throws ValueConversionException {

        Object[] params = {tag.getContent()};
        Class<?>[] paramTypes = new Class[1];
        if (tag.getContent() == null) {
            paramTypes[0] = String.class;
        } else {
            paramTypes[0] = tag.getContent().getClass();
        }

        Object instance = vConverter.convertValue(params, paramTypes, loader);
        return instance;
    }

    /**
     * Get the name under which the data will be stored.
     */
    public String getName() {
        return "data";
    }

    /**
     * Get the type of the cdata.
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
     * Get the setter method, which is setData() by default.
     */
    public String getSetterMethod() {
        return setter;
    }
}
