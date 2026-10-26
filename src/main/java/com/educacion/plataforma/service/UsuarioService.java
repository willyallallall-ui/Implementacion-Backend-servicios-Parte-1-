/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.service;

import com.educacion.plataforma.entity.Rol;
import com.educacion.plataforma.entity.Usuario;
import com.educacion.plataforma.repository.RolRepository;
import com.educacion.plataforma.repository.UsuarioRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author Willi
 */
@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;

    public UsuarioService(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public List<Usuario> listarPorRol(String nombreRol) {
        return usuarioRepository.findByRolNombre(nombreRol);
    }

    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado con id " + id));
    }

    public Usuario crear(Usuario usuario) {
        if (usuarioRepository.existsByCorreo(usuario.getCorreo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe un usuario con ese correo");
        }
        usuario.setRol(buscarRol(usuario.getRol()));
        return usuarioRepository.save(usuario);
    }

    public Usuario actualizar(Long id, Usuario datos) {
        Usuario usuario = buscarPorId(id);
        if (!usuario.getCorreo().equals(datos.getCorreo())
                && usuarioRepository.existsByCorreo(datos.getCorreo())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe un usuario con ese correo");
        }
        usuario.setNombre(datos.getNombre());
        usuario.setCorreo(datos.getCorreo());
        usuario.setRol(buscarRol(datos.getRol()));
        return usuarioRepository.save(usuario);
    }

    public void eliminar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuarioRepository.delete(usuario);
    }

    // Verifica que el rol enviado exista de verdad en la base de datos
    private Rol buscarRol(Rol rol) {
        if (rol.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Debe indicar el id del rol");
        }
        return rolRepository.findById(rol.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rol no encontrado con id " + rol.getId()));
    }
}