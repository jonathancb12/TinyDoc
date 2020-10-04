package com.tinydoc.repository;

import com.tinydoc.model.Bitacora;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BitacoraRepository extends CrudRepository<Bitacora, Integer> {

}
