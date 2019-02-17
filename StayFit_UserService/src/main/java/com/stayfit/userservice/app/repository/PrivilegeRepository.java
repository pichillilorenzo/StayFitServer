package com.stayfit.userservice.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stayfit.userservice.app.model.Privilege;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

}
