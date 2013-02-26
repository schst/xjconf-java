package net.schst.XJConf;

import java.lang.reflect.Method;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Value converter, that uses a factory method.
 *
 * @author Stephan Schmidt <me@schst.net/>
 */
public class FactoryMethodValueConverter implements ValueConverter {

    private String className;
    private String method;

    public FactoryMethodValueConverter(String name, String method) {
        this.className = name;
        this.method = method;
    }

    /**
     * Get the object.
     */
    public Object convertValue(Object[] values, Class<?>[] types, ClassLoader loader) throws ValueConversionException {
        Class<?> instanceClass;
        Object instance;

        // try to get the class object
        try {
            instanceClass = Class.forName(this.className, true, loader);
        } catch (Exception e) {
            throw new ValueConversionException("Class " + this.className + " does not exist", e);
        }
        // try to create a new instance
        try {
            Method methode;
            try {
                methode = instanceClass.getMethod(this.method, types);
            } catch (NoSuchMethodException e) {
                // try to convert the values to a string
                for (int i = 0; i < types.length; i++) {
                    types[i] = String.class;
                    values[i] = values[0].toString();
                }
                methode = instanceClass.getMethod(this.method, types);
            }
            instance = methode.invoke(null, values);
        } catch (Exception e) {
            throw new ValueConversionException("Could not create instance of " + this.className
                    + " using the factory method " + this.method, e);
        }
        return instance;
    }

    /**
     * Get the type of the generated object.
     */
    public Class<?> getType(ClassLoader loader) throws Exception {
        return Class.forName(this.className, true, loader);
    }
}
