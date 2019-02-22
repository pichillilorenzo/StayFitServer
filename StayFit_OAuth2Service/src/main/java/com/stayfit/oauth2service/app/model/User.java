/**
 * 
 */
package com.stayfit.oauth2service.app.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.JoinColumn;

import lombok.*;

/**
 * 
 * User model used by the OAuth2 server to get client's username and password.
 * UserDetails Spring Security interface provides core user information.
 */
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User implements UserDetails {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String username;
	@JsonIgnore
	private String password;
	private boolean enabled;
	
	/**
	 * We create here an empty Collection<GrantedAuthority>
	 * because user's authorities will be set in the Resource server (the REST API server).
	 */
	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		return grantedAuthorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return enabled;
	}

	@Override
	public boolean isAccountNonLocked() {
		return enabled;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return enabled;
	}
}
