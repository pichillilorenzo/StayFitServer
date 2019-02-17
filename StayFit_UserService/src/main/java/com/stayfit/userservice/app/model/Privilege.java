/**
 * 
 */
package com.stayfit.userservice.app.model;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.*;

/**
 * @author lorenzo
 *
 */
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "privileges")
public class Privilege {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;
 
    @ManyToMany(mappedBy = "privileges")
    @JsonIgnoreProperties("users")
    @JsonBackReference
    private Collection<Role> roles;

}
