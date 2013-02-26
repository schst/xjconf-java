package net.schst.XJConf.exceptions;

import org.xml.sax.SAXException;

/**
 * @author Stephan Schmidt <stephan.schmidt@schlund.de>
 */
public class XJConfException extends SAXException {

    private static final long serialVersionUID = 7630162513203322987L;

    /**
     * @param message
     */
    public XJConfException(String message) {
        super(message);
    }
    /**
     * @param e
     */
    public XJConfException(Exception e) {
        super(e);
    }
    /**
     * @param message
     * @param e
     */
    public XJConfException(String message, Exception e) {
        super(message, e);
    }

}
