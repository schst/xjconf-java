package net.schst.XJConf;

import net.schst.XJConf.exceptions.ValueConversionException;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class PrimitiveValueConverterTest {

    @DataPoints
    public static final Class<?>[] TYPES = {
        Boolean.TYPE, Byte.TYPE, Character.TYPE, Short.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE,
        Object.class, String.class};

    @Theory
    public void testConversion(Class<?> type) throws ClassNotFoundException, ValueConversionException {
        if (type.isPrimitive()) {
            Object value = new PrimitiveValueConverter(type.getName()).convertValue(new Object[] {1}, null, null);
            Assert.assertNotNull("value for type " + type, value);
        } else {
            Object value = new PrimitiveValueConverter(type.getName()).convertValue(new Object[] {1}, null, null);
            Assert.assertNull("value for type " + type, value);
        }
    }
}
