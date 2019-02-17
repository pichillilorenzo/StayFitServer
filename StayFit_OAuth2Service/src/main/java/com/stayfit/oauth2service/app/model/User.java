/**
 * 
 */
package com.stayfit.oauth2service.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

/**
 * @author lorenzo
 *
 */
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "users")
public class User {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	private String username;
	@JsonIgnore
	private String password;
	private String email;
	private boolean enabled;

}
