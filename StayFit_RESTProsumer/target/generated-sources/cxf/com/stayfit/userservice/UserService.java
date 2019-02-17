package com.stayfit.userservice;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * User Service
 *
 * This class was generated by Apache CXF 3.2.7
 * 2019-02-17T01:37:41.577+01:00
 * Generated source version: 3.2.7
 *
 */
@WebServiceClient(name = "UserService",
                  wsdlLocation = "classpath:wsdl/clients/user.wsdl",
                  targetNamespace = "http://stayfit.com/userservice")
public class UserService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://stayfit.com/userservice", "UserService");
    public final static QName UserPort = new QName("http://stayfit.com/userservice", "UserPort");
    static {
        URL url = UserService.class.getClassLoader().getResource("wsdl/clients/user.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(UserService.class.getName())
                .log(java.util.logging.Level.INFO,
                     "Can not initialize the default wsdl from {0}", "classpath:wsdl/clients/user.wsdl");
        }
        WSDL_LOCATION = url;
    }

    public UserService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public UserService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public UserService() {
        super(WSDL_LOCATION, SERVICE);
    }

    public UserService(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    public UserService(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    public UserService(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }




    /**
     *
     * @return
     *     returns UserServicePortType
     */
    @WebEndpoint(name = "UserPort")
    public UserServicePortType getUserPort() {
        return super.getPort(UserPort, UserServicePortType.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns UserServicePortType
     */
    @WebEndpoint(name = "UserPort")
    public UserServicePortType getUserPort(WebServiceFeature... features) {
        return super.getPort(UserPort, UserServicePortType.class, features);
    }

}
