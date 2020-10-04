package com.tinydoc.service;

import com.tinydoc.model.Usuario;

import java.util.List;

public interface IUsuario {

    Boolean create(Usuario usuario);

    Boolean update(Usuario usuario);

    void delete(String rut);

    List<Usuario> listAll();

    List<Usuario> listAllActives();

    Usuario find(String rut);

    Boolean exists(String rut);

    Usuario findByEmail(String email);
}
