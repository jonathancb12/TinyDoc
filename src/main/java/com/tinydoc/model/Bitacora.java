package com.tinydoc.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "BITACORA")
@SequenceGenerator(
        name = "secuenciaBitacora",
        sequenceName = "SQ_BITACORA",
        initialValue = 1,
        allocationSize = 1
)
public class Bitacora implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "secuenciaBitacora")
    @Column(name = "IDBITACORA")
    private Integer idRegistro;

    @Column(name = "ACTION")
    private String accion;

    @Column(name = "date")
    private Date date;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "USERS_USERID")
    private Usuario usuario;

    public Bitacora() {
        this.date = new Date();
    }

    public Bitacora(Usuario usuario, String accion, String descripcion) {
        this.usuario = usuario;
        this.accion = accion;
        this.date = new Date();
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Number getIdRegistro() {
        return idRegistro;
    }

    public void setIdRegistro(Integer idRegistro) {
        this.idRegistro = idRegistro;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

}
