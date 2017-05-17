
package org.komparator.mediator.ws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.10
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "InvalidItemId", targetNamespace = "http://ws.mediator.komparator.org/")
public class InvalidItemId_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private InvalidItemId faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public InvalidItemId_Exception(String message, InvalidItemId faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public InvalidItemId_Exception(String message, InvalidItemId faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: org.komparator.mediator.ws.InvalidItemId
     */
    public InvalidItemId getFaultInfo() {
        return faultInfo;
    }

}