package com.tinydoc.service;

import com.tinydoc.model.Roles;

import java.util.List;

public interface IRoles {

    void newRole(Roles rol);

    List<Roles> listAll();

}
