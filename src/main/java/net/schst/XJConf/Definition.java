package net.schst.XJConf;

import net.schst.XJConf.exceptions.ValueConversionException;

/**
 * Interface for tag and attribute definitions.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public interface Definition {

    /**
     * Gets the name under which the information will be stored.
     *
     * @return name of the value
     */
    String getName();

    /**
     * Gets the converted value.
     *
     * XJConf will pass the complete tag to this method.
     *
     * @param tag
     * @param loader
     *            A Classloader to be passed to the ValueConverter.
     * @return Converted value object.
     * @throws ValueConversionException
     */
    Object convertValue(final Tag tag, final ClassLoader loader) throws ValueConversionException;

    /**
     * Gets the type of the converted value.
     *
     * @param tag
     * @param loader
     *            A Classloader to be passed to the ValueConverter.
     * @return Class object denoting the type.
     */
    Class<?> getValueType(final Tag tag, final ClassLoader loader);

    /**
     * Gets the name of the setter method.
     *
     * @return The name of the setter method.
     */
    String getSetterMethod();

    /**
     * Adds a child definition of any type.
     *
     * @param def
     */
    void addChildDefinition(final Definition def) throws Exception;

}
