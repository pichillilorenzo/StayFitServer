/**
 * 
 */
package com.stayfit.userservice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stayfit.userservice.app.model.Role;

/**
 * @author lorenzo
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
