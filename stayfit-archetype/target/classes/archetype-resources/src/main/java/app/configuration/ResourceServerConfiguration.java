/**
 * 
 */
package ${package}.app.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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

import ${package}.app.model.Role;
import ${package}.app.model.User;
import ${package}.app.repository.UserRepository;

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
     * To invoke any POST method request, the ‘write’ scope is needed.
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
    	    public Authentication extractAuthentication(Map<String, ?> map) {
    	       return null;
    	    }

    	});

    	return null;
    }
    
    /**
     * RemoteTokenServices queries the /check_token endpoint of OAuth2 Server to obtain the contents of an access token.
     */
    @Bean
    public RemoteTokenServices remoteTokenServices() {
      return null;
    }
}