package ${package}.app.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import ${package}.app.exception.ResourceNotFoundException;
import ${package}.app.model.Role;
import ${package}.app.model.User;
import ${package}.app.repository.UserRepository;

/**
 * 
 * The Resource Server is responsible for protecting resources, 
 * capable of accepting responding to protected resource requests using access tokens.
 * 
 * Spring OAuth2 provides an authentication filter that handles protection. 
 * The @EnableResourceServer annotation enables a Spring Security filter 
 * that authenticates requests via an incoming OAuth2 token.
 * 
 * To secure access to the protected resources, we will use the @PreAuthorize annotation.
 */

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	@Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;
	
	@Value("${oauth2service.url}")
	private String oauth2ServiceUrl;
	
	@Autowired
    private UserRepository userRepository;
	
    private static final String RESOURCE_ID = "resource-server-rest-api";
    private static final String SECURED_READ_SCOPE = "#oauth2.hasScope('read')";
    private static final String SECURED_WRITE_SCOPE = "#oauth2.hasScope('write')";
    private static final String SECURED_PATTERN = "/api/v1/**";
    
    List<String> allowedOrigins = new ArrayList<String>() {{
		add("*");
	}};
	
	List<String> allowedMethods = new ArrayList<String>() {{
		add("HEAD");
		add("GET");
		add("POST");
		add("PUT");
		add("DELETE");
		add("PATCH");
		add("OPTIONS");
	}};
	
	List<String> allowedHeaders = new ArrayList<String>() {{
		add("Authorization");
		add("Cache-Control");
		add("Content-Type");
	}};
    
	/**
     * Here we define the TokenStore bean to let Spring know to use the database for token operations.
     */
    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }
    
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID);
        resources.tokenStore(tokenStore());
    }
    
    /**
     * The configure(HttpSecurity http) method configures the access rules and request matchers (path) 
     * for protected resources using the HttpSecurity class. 
     * We secure the URL paths SECURED_PATTERN. 
     * To invoke any POST method request, the �write� scope is needed.
     * Also, we enable Cross-Origin Resource Sharing (CORS support).
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and().requestMatchers()
                .antMatchers(SECURED_PATTERN).and().authorizeRequests()
                .antMatchers(HttpMethod.POST, SECURED_PATTERN).access(SECURED_WRITE_SCOPE)
                .anyRequest().access(SECURED_READ_SCOPE);
    }
    
    /**
     * This method provides CORS configuration.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(allowedMethods);
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(allowedHeaders);
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    /**
     * With this methods we are updating the authentication principal at resource server 
     * with custom user's authorities by getting the authentication information from the user's access token.
     */
    @Bean
    public AccessTokenConverter accessTokenConverter() {
    	DefaultAccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();
    	tokenConverter.setUserTokenConverter(new DefaultUserAuthenticationConverter() {

    	    @Override
    	    public Authentication extractAuthentication(Map<String, ?> map) throws ResourceNotFoundException {
    	        Authentication authentication = super.extractAuthentication(map);
    	        
    	        String username = map.get("user_name").toString();
    	        
    	        User user = userRepository.findByUsername(username)
    	        				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
    	        
    	        // here we set the user's authorities
    	        // for granting access to protected resources
    	        Collection<GrantedAuthority> userAuthorities = new ArrayList<GrantedAuthority>();
    	        Collection<Role> roles = user.getAuthorities();
    	        userAuthorities.addAll(roles);
    	        roles.forEach(role -> {
    	        	userAuthorities.addAll(role.getPrivileges());
    	        });
    	        
    	        return new UsernamePasswordAuthenticationToken(user,
    	                authentication.getCredentials(), userAuthorities);
    	    }

    	});

    	return tokenConverter;
    }
    
    /**
     * RemoteTokenServices queries the /check_token endpoint of OAuth2 Server to obtain the contents of an access token.
     */
    @Bean
    public RemoteTokenServices remoteTokenServices() {
        RemoteTokenServices tokenServices = new RemoteTokenServices();
        tokenServices.setClientId("spring-security-oauth2-read-write-client");
        tokenServices.setClientSecret("spring-security-oauth2-read-write-client-password1234");
        tokenServices.setCheckTokenEndpointUrl(oauth2ServiceUrl+"/oauth/check_token");
        tokenServices.setAccessTokenConverter(accessTokenConverter());
        return tokenServices;
    }
}