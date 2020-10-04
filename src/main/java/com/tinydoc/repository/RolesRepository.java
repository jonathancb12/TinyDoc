package com.tinydoc.repository;

import com.tinydoc.model.Roles;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public interface RolesRepository extends CrudRepository<Roles, Integer> {

}
