package net.schst.XJConf.tests.helpers;

public class PrimitivesContainer implements IPrimitivesContainer {

    private boolean booleanValue;
    private int intValue;
    private long longValue;
    private float floatValue;
    private double doubleValue;
    private short shortValue;

    /*
     * @see net.schst.XJConf.tests.helpers.IPrimitivesContainer#getBooleanValue()
     */
    public boolean getBooleanValue() {
        return booleanValue;
    }
    /**
     * @param booleanValue The booleanValue to set.
     */
    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }
    /*
     * @see net.schst.XJConf.tests.helpers.IPrimitivesContainer#getFloatValue()
     */
    public float getFloatValue() {
        return floatValue;
    }
    /**
     * @param floatValue The floatValue to set.
     */
    public void setFloatValue(float floatValue) {
        this.floatValue = floatValue;
    }
    /* (non-Javadoc)
     * @see net.schst.XJConf.tests.helpers.IPrimitivesContainer#getIntValue()
     */
    public int getIntValue() {
        return intValue;
    }
    /**
     * @param intValue The intValue to set.
     */
    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }
    /* (non-Javadoc)
     * @see net.schst.XJConf.tests.helpers.IPrimitivesContainer#getLongValue()
     */
    public long getLongValue() {
        return longValue;
    }
    /**
     * @param longValue The longValue to set.
     */
    public void setLongValue(long longValue) {
        this.longValue = longValue;
    }
    /* (non-Javadoc)
     * @see net.schst.XJConf.tests.helpers.IPrimitivesContainer#getDoubleValue()
     */
    public double getDoubleValue() {
        return doubleValue;
    }
    /**
     * @param doubleParam The doubleParam to set.
     */
    public void setDoubleValue(double doubleParam) {
        this.doubleValue = doubleParam;
    }
    /**
     * @return Returns the shortValue.
     */
    public short getShortValue() {
        return shortValue;
    }
    /**
     * @param shortValue The shortValue to set.
     */
    public void setShortValue(short shortValue) {
        this.shortValue = shortValue;
    }

}
