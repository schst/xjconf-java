package net.schst.XJConf.Converter;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Class to convert Enums and set value.
 *
 * @author Daniel Jahnke <daniel.jahnke@1und1.de>
 */
public class EnumConverter<T extends Enum<T>> implements TypeConverter {

    private Class<T> enumClass;
    private T instance;

    @SuppressWarnings("unchecked")
    public void doInstantiate(String className, ClassLoader loader) throws ClassNotFoundException {
        enumClass = (Class<T>) Class.forName(className).asSubclass(Enum.class);
    }

    public void setValues(Object[] values, Class<?>[] types) throws ValueConversionException {
        instance = Enum.valueOf(enumClass, (String) values[0]);
    }

    public Object getInstance() {
        return instance;
    }

}
