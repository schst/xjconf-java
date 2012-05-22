package net.schst.XJConf.Converter;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Class to convert Enums and set value
 * 
 * @author Daniel Jahnke <daniel.jahnke@1und1.de>
 */
public class EnumConverter implements TypeConverter {

    private Class<? extends Enum> enumClass;
    private Object instance;

    public void doInstantiate(String className, ClassLoader loader) throws ClassNotFoundException {
        enumClass = Class.forName(className).asSubclass(Enum.class);
    }

    public void setValues(Object[] values, Class<?>[] types) throws ValueConversionException {
        instance = Enum.valueOf(enumClass, (String) values[0]);
    }

    public Object getInstance() {
        return instance;
    }

}
