package net.schst.XJConf.tests.helpers;

/**
 * Simple class that must be created using a factory method.
 *
 * @author Stephan Schmidt <me@schst.net>
 */
public class FactoryMethodClass {

    private String param;

    protected FactoryMethodClass() {
    }

    public String getParam() {
        return this.param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public static FactoryMethodClass createInstance() {
        return new FactoryMethodClass();
    }

    public static FactoryMethodClass createInstanceWithParam(String param) {
        FactoryMethodClass instance = new FactoryMethodClass();
        instance.setParam(param);
        return instance;
    }

}
