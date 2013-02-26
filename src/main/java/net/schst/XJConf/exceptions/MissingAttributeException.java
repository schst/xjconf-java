package net.schst.XJConf.exceptions;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class MissingAttributeException extends ValueConversionException {

    private static final long serialVersionUID = 854801274215580679L;

    /**
     * @param e
     */
    public MissingAttributeException(Exception e) {
        super(e);
    }

    /**
     * @param message
     */
    public MissingAttributeException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param e
     */
    public MissingAttributeException(String message, Exception e) {
        super(message, e);
    }

}
