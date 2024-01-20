package com.naja.springblog.repository;

import java.util.Optional;

import com.naja.springblog.model.Role;
import com.naja.springblog.model.RoleName;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(@Param("name") RoleName name);
}
