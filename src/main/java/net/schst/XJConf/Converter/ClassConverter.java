package net.schst.XJConf.Converter;

import java.lang.reflect.Constructor;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Class to convert defined classes and set attributes.
 *
 * @author Daniel Jahnke <daniel.jahnke@1und1.de/>
 */
public class ClassConverter implements TypeConverter {

    private Class<?> instanceClass;
    private Object instance;
    private String className;

    public void doInstantiate(String clsName, ClassLoader loader) throws Exception {
        instanceClass = Class.forName(clsName, true, loader);
        className = clsName;
    }

    public Object getInstance() {
        return instance;
    }

    public void setValues(Object[] values, Class<?>[] types) throws ValueConversionException {
        Object objectInstance;
        try {
            Constructor<?> co;
            try {
                co = instanceClass.getConstructor(types);
            } catch (NoSuchMethodException e) {
                // try to convert the values to a string
                for (int i = 0; i < types.length; i++) {
                    types[i] = String.class;
                    values[i] = values[0].toString();
                }
                co = instanceClass.getConstructor(types);
            }
            objectInstance = co.newInstance(values);
        } catch (Exception e) {
            try {
                // no matching constructor has been found
                // try to instantiate the class without using
                // a constructor
                objectInstance = instanceClass.newInstance();
            } catch (Exception t) {
                throw new ValueConversionException("Could not create instance of " + className, t);
            }
        }
        instance = objectInstance;
    }

}
