package net.schst.XJConf;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Converter to convert a value to a primitive type.
 *
 * @author Stephan Schmidt
 */
public class PrimitiveValueConverter implements ValueConverter {

    /**
     * Field stores the type of the primitive.
     */
    private Class<?> type;

    /**
     * Create a new converter.
     * @param type
     */
    public PrimitiveValueConverter(String type) {
        this.type = findPrimitiveByName(type);
    }

    /**
     * Convert the value.
     */
    public Object convertValue(Object[] values, Class<?>[] types, ClassLoader loader) throws ValueConversionException {

        if (values.length > 1) {
            throw new ValueConversionException("Cannot convert value to primitive type.");
        }

        if (type == null) {
            return null;
        } else if (int.class.equals(type)) {
            return Integer.valueOf(values[0].toString());
        } else if (byte.class.equals(type)) {
            return Byte.valueOf(values[0].toString());
        } else if (char.class.equals(type)) {
            return Character.valueOf(values[0].toString().charAt(0));
        } else if (short.class.equals(type)) {
            return Short.valueOf(values[0].toString());
        } else if (long.class.equals(type)) {
            return new Long(values[0].toString());
        } else if (float.class.equals(type)) {
            return Float.valueOf(values[0].toString());
        } else if (double.class.equals(type)) {
            return Double.valueOf(values[0].toString());
        } else if (boolean.class.equals(type)) {
            return Boolean.valueOf(values[0].toString());
        } else if (short.class.equals(type)){
            return Short.valueOf(values[0].toString());
        } else {
            assert false : "Unhandled primitive: " + type;
            return null;
        }
    }

    /**
     * Get the type.
     */
    public Class<?> getType(ClassLoader loader) throws Exception {
        return type;
    }

    private static Class<?> findPrimitiveByName(String name) {
        if ("int".equals(name)) {
            return Integer.TYPE;
        } else if ("byte".equals(name)) {
            return Byte.TYPE;
        } else if ("char".equals(name)) {
            return Character.TYPE;
        } else if ("long".equals(name)) {
            return Long.TYPE;
        } else if ("float".equals(name)) {
            return Float.TYPE;
        } else if ("double".equals(name)) {
            return Double.TYPE;
        } else if ("boolean".equals(name)) {
            return Boolean.TYPE;
        } else if ("short".equals(name)) {
            return Short.TYPE;
        }
        return null;
    }
}
