package com.stayfit.userservice.app.service;

import com.stayfit.userservice.app.exception.ResourceNotFoundException;
import com.stayfit.userservice.app.model.Role;

public interface RoleService {

	Role getRoleByName(String name) throws ResourceNotFoundException;

	Role getRoleById(Long id) throws ResourceNotFoundException;

}