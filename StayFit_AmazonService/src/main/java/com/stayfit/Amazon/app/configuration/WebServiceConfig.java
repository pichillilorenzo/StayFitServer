/**
 * 
 */
package com.stayfit.Amazon.app.configuration;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;
import org.springframework.ws.wsdl.wsdl11.Wsdl11Definition;

/**
 * 
 *
 * @EnableWs Enables SOAP Web Service features in this Spring Boot application.
 * Using the ServletRegistrationBean we register the MessageDispatcherServlet with Spring Boot. 
 * During this registration, the servlet mapping URI pattern is set to the SOAP Service address. 
 * Using this address, the web container will map incoming HTTP requests to the MessageDispatcherServlet. 
 * The DefaultWsdl11Definition exposes a standard WSDL 1.1 using the specified WSDL file. 
 * MessageDispatcherServlet also automatically detects any WsdlDefinition defined in its application context.
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/amazon/services/*");
    }

    @Bean(name = "amazon")
    public Wsdl11Definition defaultWsdl11Definition() {
        SimpleWsdl11Definition wsdl11Definition = new SimpleWsdl11Definition();
        wsdl11Definition.setWsdl(new ClassPathResource("/wsdl/amazon.wsdl"));
        return wsdl11Definition;
    }
}
