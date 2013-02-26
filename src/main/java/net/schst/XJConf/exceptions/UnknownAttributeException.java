package net.schst.XJConf.exceptions;

/**
 * Attribute has not been defined.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class UnknownAttributeException extends XJConfException {

    private static final long serialVersionUID = -2244163522763557321L;

    public UnknownAttributeException(String message) {
        super(message);
    }

    public UnknownAttributeException(String message, Exception cause) {
        super(message, cause);
    }

}
