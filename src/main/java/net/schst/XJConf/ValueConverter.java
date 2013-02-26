package net.schst.XJConf;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de/>
 */
public interface ValueConverter {
    Object convertValue(Object[] values, Class<?>[] types, ClassLoader loader) throws ValueConversionException;
    Class<?> getType(ClassLoader loader) throws Exception;
}
