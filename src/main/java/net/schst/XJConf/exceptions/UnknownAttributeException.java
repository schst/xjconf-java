package net.schst.XJConf.exceptions;

import net.schst.XJConf.exceptions.XJConfException;

/**
 * Attribute has not been defined.
 * 
 * @author Stephan Schmidt <stephan.schmidt@schlund.de> */
public class UnknownAttributeException extends XJConfException {
    public UnknownAttributeException(String message) {
        super(message);
    }

    public UnknownAttributeException(String message, Exception cause) {
        super(message, cause);
    }
}
