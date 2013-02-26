package net.schst.XJConf;

import java.lang.reflect.Modifier;

import net.schst.XJConf.Converter.ClassConverter;
import net.schst.XJConf.Converter.EnumConverter;
import net.schst.XJConf.Converter.TypeConverter;
import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Class to convert a value to an object.
 *
 * @author Stephan Schmidt
 * @author Daniel Jahnke
 */
public class ObjectValueConverter implements ValueConverter {

    /**
     * Field stores the name of the target class.
     */
    private String className;

    /**
     * Create a new converter.
     *
     * @param className
     */
    public ObjectValueConverter(String className) {
        this.className = className;
    }

    /**
     * Convert the value.
     *
     * @param values    Values for the constructor
     * @param types     Types of the values
     * @param loader    classloader to use
     */
    @SuppressWarnings("unchecked")
    public Object convertValue(Object[] values, Class<?>[] types, ClassLoader loader) throws ValueConversionException {
        Class<?> instanceClass;
        TypeConverter typeConverter;

        // try to get the class object.
        try {
            instanceClass = Class.forName(this.className, true, loader);
        } catch (Exception e) {
            throw new ValueConversionException("Class " + this.className + " does not exist", e);
        }

        if (instanceClass.isInterface()) {
            throw new ValueConversionException("Class " + this.className + " is an interface.");
        } else if (Modifier.toString(instanceClass.getModifiers()).contains("abstract")) {
            throw new ValueConversionException("Class " + this.className + " is abstract.");
        } else if (instanceClass.isAnnotation()) {
            throw new ValueConversionException("Class " + this.className + " is an annotation.");
        } else if (instanceClass.isEnum()) {
            typeConverter = new EnumConverter();
        } else {
            typeConverter = new ClassConverter();
        }

        /*
         * after recognizing type, make
         * the object alive and set values.
         */
        try {
            typeConverter.doInstantiate(this.className, loader);
            typeConverter.setValues(values, types);
        } catch (Exception e) {
            throw new ValueConversionException("Trying to create an Object of Class " + this.className + " faild!", e);
        }


        return typeConverter.getInstance();
    }

    /**
     * Get the type of the returned value.
     *
     * @return    Class object
     */
    public Class<?> getType(ClassLoader loader) throws Exception {
        return Class.forName(this.className, true, loader);
    }
}
