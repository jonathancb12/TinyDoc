package com.tinydoc.service.impl;

import com.tinydoc.model.Roles;
import com.tinydoc.repository.RolesRepository;
import com.tinydoc.service.IRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesImpl implements IRoles {

    @Autowired
    private RolesRepository rolesRepository;

    @Override
    public void newRole(Roles rol) {
        rolesRepository.save(rol);
    }

    @Override
    public List<Roles> listAll() {
        return (List<Roles>) rolesRepository.findAll();
    }


}
