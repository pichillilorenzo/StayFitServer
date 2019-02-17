
package com.stayfit.userservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.stayfit.userservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetUserByIdRequest_QNAME = new QName("http://stayfit.com/userservice", "GetUserByIdRequest");
    private final static QName _GetUserByIdResponse_QNAME = new QName("http://stayfit.com/userservice", "GetUserByIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.stayfit.userservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetUserByIdRequest }
     * 
     */
    public GetUserByIdRequest createGetUserByIdRequest() {
        return new GetUserByIdRequest();
    }

    /**
     * Create an instance of {@link GetUserByIdResponse }
     * 
     */
    public GetUserByIdResponse createGetUserByIdResponse() {
        return new GetUserByIdResponse();
    }

    /**
     * Create an instance of {@link User }
     * 
     */
    public User createUser() {
        return new User();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserByIdRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stayfit.com/userservice", name = "GetUserByIdRequest")
    public JAXBElement<GetUserByIdRequest> createGetUserByIdRequest(GetUserByIdRequest value) {
        return new JAXBElement<GetUserByIdRequest>(_GetUserByIdRequest_QNAME, GetUserByIdRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://stayfit.com/userservice", name = "GetUserByIdResponse")
    public JAXBElement<GetUserByIdResponse> createGetUserByIdResponse(GetUserByIdResponse value) {
        return new JAXBElement<GetUserByIdResponse>(_GetUserByIdResponse_QNAME, GetUserByIdResponse.class, null, value);
    }

}
