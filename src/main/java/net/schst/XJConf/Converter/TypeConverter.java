package net.schst.XJConf.Converter;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Interface for typconverter
 * 
 * @author Daniel Jahnke <daniel.jahnke@1und1.de>
 */
public interface TypeConverter {
    
    /**
     * interface method
     * trying to create an instance
     * 
     * @param className     Class name of new instance
     * @param loader        Classloader to use
     * @throws ValueConversionException
     */
    public void doInstantiate(String className, ClassLoader loader) throws Exception;
    
    
    /**
     * interface method
     * to set values at created instance
     * 
     * @param values        Values for the constructor
     * @param types         Types of the values
     * @throws ValueConversionException
     */
    public void setValues(Object[] values, Class<?>[] types) throws ValueConversionException;
    
    /**
     * interface method
     * get the instanciated object
     * @return object
     */
    public Object getInstance();
}
