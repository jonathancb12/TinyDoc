package com.tinydoc.service.impl;

import com.tinydoc.model.Bitacora;
import com.tinydoc.model.Roles;
import com.tinydoc.model.Usuario;
import com.tinydoc.repository.BitacoraRepository;
import com.tinydoc.repository.UsuarioRepository;
import com.tinydoc.service.IUsuario;
import com.tinydoc.utils.MailTemplates;
import com.tinydoc.utils.Password;
import com.tinydoc.utils.SendMail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Service
@Resource(mappedName = "classpath*:META-INF/persistence.xml")
public class UsuarioImpl implements IUsuario, UserDetailsService {

    private Logger LOGGER = LoggerFactory.getLogger(UsuarioImpl.class);

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BitacoraRepository bitacoraRepository;

    private Usuario usuarioActivo;

    @Transactional
    @Override
    public Boolean create(Usuario usuario) {
        if (!(usuario == null && usuarioRepository.existsById(usuario.getUserId()))) {
            usuario.setPass(Password.generatePassword());
            usuarioRepository.save(usuario);
            SendMail.senMail(usuario.getEmail(), "Tu nueva cuenta TinyDoc",
                    MailTemplates.envioClave(usuario.getName(), usuario.getPass()));
            bitacora("CREAR", "Crea usuario - " + usuario.getUserId());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional
    @Override
    public Boolean update(Usuario usuario) {
        Usuario tmp = find(usuario.getUserId());
        tmp.setName(usuario.getName());
        tmp.setLastName(usuario.getLastName());
        tmp.setBirth(usuario.getBirth());
        tmp.setActive(usuario.getActive());
        tmp.setEmail(usuario.getEmail());
        usuarioRepository.save(tmp);
        //bitacora("MODIFICA", "Modifica datos del usuario - " + usuario.getUserId());
        return true;
    }

    @Override
    public void delete(String rut) {
        usuarioRepository.delete(find(rut));
        bitacora("ELIMINA", "Eliminó el registro del usuario - " + rut);
    }

    @Transactional
    @Override
    public List<Usuario> listAllActives() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA_Factoria");
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("select u from Usuario u where u.active = true");
        return query.getResultList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listAll() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario find(String rut) {
        return usuarioRepository.findById(rut).orElse(null);
    }

    @Override
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean exists(String rut) {
        return usuarioRepository.existsById(rut);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = findByEmail(email);

        if (usuario == null || !usuario.getActive()) {
            LOGGER.error("El usuario " + email + " no existe, o no se encuentra activo");
            throw new UsernameNotFoundException("El usuario no existe, o no se encuentra activo");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Roles rol : usuario.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(rol.getDescripcion()));
        }

        usuarioActivo = usuario;
        return new User(usuario.getUserId(), usuario.getPass(), usuario.getActive(), true, true, true, authorities);
    }


    private void bitacora(String accion, String desc) {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();
        UserDetails userDetail = (UserDetails) auth.getPrincipal();
        bitacoraRepository.save(new Bitacora(usuarioActivo, accion, desc));
    }
}
