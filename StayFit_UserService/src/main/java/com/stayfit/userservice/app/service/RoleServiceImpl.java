/**
 * 
 */
package com.stayfit.userservice.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stayfit.userservice.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.app.model.Role;
import com.stayfit.userservice.app.repository.RoleRepository;

/**
 * @author lorenzo
 *
 */
@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
    private RoleRepository roleRepository;
	
	/* (non-Javadoc)
	 * @see com.stayfit.userservice.app.service.RoleService#getRoleByName(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = true)
    public Role getRoleByName(String name) throws ResourceNotFoundException {
        Role role = roleRepository.findByName(name);

        if (role != null) {
	        return role;
        }
        
        throw new ResourceNotFoundException("Role", "name", name);
    }
    
    /* (non-Javadoc)
	 * @see com.stayfit.userservice.app.service.RoleService#getRoleById(java.lang.Long)
	 */
    @Override
	@Transactional(readOnly = true)
    public Role getRoleById(Long id) throws ResourceNotFoundException {
        return roleRepository.findById(id)
        		.orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
    }
}
