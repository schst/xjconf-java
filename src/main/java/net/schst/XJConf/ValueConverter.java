package net.schst.XJConf;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public interface ValueConverter {
    public Object convertValue(Object[] values, Class[] types, ClassLoader loader) throws ValueConversionException;
    public Class getType(ClassLoader loader) throws Exception;
}