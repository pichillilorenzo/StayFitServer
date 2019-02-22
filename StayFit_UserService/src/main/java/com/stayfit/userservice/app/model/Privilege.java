/**
 * 
 */
package com.stayfit.userservice.app.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.*;

/**
 * 
 * 
 * @Entity declares the class as an entity.
 * @Id declares the identifier property of this entity.
 * @Table defines the table for this entity.
 * 
 * This class represents a system privilege related to a particular role.
 */
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "privileges")
public class Privilege {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String name;

}
