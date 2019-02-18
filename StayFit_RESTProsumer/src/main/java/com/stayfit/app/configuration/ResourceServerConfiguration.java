/**
 * 
 */
package com.stayfit.app.configuration;

import java.util.Arrays;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import com.stayfit.app.model.User;
import com.stayfit.app.repository.UserRepository;

/**
 * @author lorenzo
 *
 */

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	@Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
	
	@Autowired
    private UserRepository userRepository;
	
    private static final String RESOURCE_ID = "resource-server-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN = "/api/v1/**";
    
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
        resources.tokenStore(tokenStore());
    }
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.requestMatchers()
                .antMatchers(SECURED_PATTERN).and().authorizeRequests()
                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
                .anyRequest().access(SECURED_READ_SCOPE);
    }
    
    @Bean
    public AccessTokenConverter accessTokenConverter() {
    	DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
    	tokenConverter.setUserTokenConverter(new DefaultUserAuthenticationConverter() {

    	    @Override
    	    public Authentication extractAuthentication(Map<String, ?> map) {
    	        Authentication authentication = super.extractAuthentication(map);

    	        User user = userRepository.findByUsername(map.get("user_name").toString());
    	        
    	        return new UsernamePasswordAuthenticationToken(user,
    	                authentication.getCredentials(), user.getAuthorities());
    	    }

    	});

    	return tokenConverter;
    }
    
    @Bean
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId("spring-security-oauth2-read-write-client");
        tokenServices.setClientSecret("spring-security-oauth2-read-write-client-password1234");
        tokenServices.setCheckTokenEndpointUrl("http://localhost:8081/oauth/check_token");
        tokenServices.setAccessTokenConverter(accessTokenConverter());
        return tokenServices;
    }
}