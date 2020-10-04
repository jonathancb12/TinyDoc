package com.tinydoc.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ROLE")
@SequenceGenerator(
        name = "secuenciaRole",
        sequenceName = "SQ_ROLE",
        initialValue = 1,
        allocationSize = 1
)
public class Roles implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuenciaRole")
    @Column(name = "ROLEID")
    private Integer idRol;

    @Column(name = "DESCRIPTION")
    private String descripcion;

    @Column(name = "USERS_USERID")
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Roles() {
    }

    public Roles(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
