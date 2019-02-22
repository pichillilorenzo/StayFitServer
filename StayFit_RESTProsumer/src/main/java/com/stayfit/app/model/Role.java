/**
 * 
 */
package com.stayfit.app.model;

import java.util.Collection;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.JoinColumn;

import lombok.*;

/**
 * 
 * @Entity declares the class as an entity.
 * @Id declares the identifier property of this entity.
 * @Table defines the table for this entity.
 * 
 * This class represents a system role (authority) related to different users and system privileges.
 */
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;
    @ManyToMany(mappedBy = "roles")
    @JsonIgnoreProperties("roles")
    private Collection<User> users;
 
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "roles_privileges", 
        joinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "privilege_id", referencedColumnName = "id"))
    @OrderBy
    @JsonManagedReference 
    private Collection<Privilege> privileges;

	@Override
	public String getAuthority() {
		return name;
	} 

}
