package net.schst.XJConf;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Converter to convert a value to a primitive type
 * 
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class PrimitiveValueConverter implements ValueConverter {

    /**
     * Type of the primitive
     */
    private String type;

    /**
     * Create a new converter
     * @param type
     */
    public PrimitiveValueConverter(String type) {
        this.type = type;
    }

    /**
     * Convert the value
     */
    public Object convertValue(Object[] values, Class[] types, ClassLoader loader) throws ValueConversionException {
        
        if (values.length > 1) {
            throw new ValueConversionException("Cannot convert value to primitive type.");
        }
        
        if (this.type.equals("int")) {
            return Integer.valueOf(values[0].toString());
        }
        if (this.type.equals("long")) {
            return new Long(values[0].toString());
        }
        if (this.type.equals("float")) {
            return Float.valueOf(values[0].toString());
        }
        if (this.type.equals("double")) {
            return Double.valueOf(values[0].toString());
        }
        if (this.type.equals("boolean")) {
            return Boolean.valueOf(values[0].toString());
        }
        if (this.type.equals("short")) {
            return Short.valueOf(values[0].toString());
        }
        return null;
    }

    /**
     * Get the type
     */
    public Class getType(ClassLoader loader) throws Exception {
        if (this.type.equals("int")) {
            return Integer.TYPE;
        }
        if (this.type.equals("long")) {
            return Long.TYPE;
        }
        if (this.type.equals("float")) {
            return Float.TYPE;
        }
        if (this.type.equals("double")) {
            return Double.TYPE;
        }
        if (this.type.equals("boolean")) {
            return Boolean.TYPE;
        }
        if (this.type.equals("short")) {
            return Short.TYPE;
        }
        return null;
    }
}