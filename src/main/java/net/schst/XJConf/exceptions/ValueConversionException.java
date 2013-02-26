package net.schst.XJConf.exceptions;

/**
 * Value could not be converted to the selected type.
 *
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class ValueConversionException extends XJConfException {

    private static final long serialVersionUID = -8827878587015359067L;

    /**
     * @param e
     */
    public ValueConversionException(Exception e) {
        super(e);
    }

    /**
     * @param message
     */
    public ValueConversionException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param e
     */
    public ValueConversionException(String message, Exception e) {
        super(message, e);
    }

}
