package com.stayfit.userservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;

/**
 * This class was generated by Apache CXF 3.2.7
 * 2019-02-16T21:40:21.104+01:00
 * Generated source version: 3.2.7
 *
 */
@WebService(targetNamespace = "http://stayfit.com/userservice", name = "UserServicePortType")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface UserServicePortType {

    @WebMethod
    @Action(input = "http://stayfit.com/userservice/GetUserByIdRequest", output = "http://stayfit.com/userservice/GetUserByIdResponse")
    @WebResult(name = "GetUserByIdResponse", targetNamespace = "http://stayfit.com/userservice", partName = "parameters")
    public GetUserByIdResponse getUserById(
        @WebParam(partName = "parameters", name = "GetUserByIdRequest", targetNamespace = "http://stayfit.com/userservice")
        GetUserByIdRequest parameters
    );
}