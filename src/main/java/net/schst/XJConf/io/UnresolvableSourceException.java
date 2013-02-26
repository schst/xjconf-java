package net.schst.XJConf.io;

import net.schst.XJConf.exceptions.XJConfException;

public class UnresolvableSourceException extends XJConfException {

    private static final long serialVersionUID = -4555029146826295511L;

    private Source parent;
    private String location;

    public UnresolvableSourceException(Source parent, String location, String message) {
        super(message);
        this.parent = parent;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public Source getParent() {
        return parent;
    }

}
