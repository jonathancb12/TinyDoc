package com.tinydoc.repository;

import com.tinydoc.model.Usuario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends CrudRepository<Usuario, String> {

    Usuario findByEmail(String email);
}
