/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.service;

import com.educacion.plataforma.entity.Rol;
import com.educacion.plataforma.repository.RolRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author Willi
 */
@Service
public class RolService {

    private final RolRepository rolRepository;

    public RolService(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    public List<Rol> listar() {
        return rolRepository.findAll();
    }

    public Rol buscarPorId(Long id) {
        return rolRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Rol no encontrado con id " + id));
    }

    public Rol crear(Rol rol) {
        if (rolRepository.existsByNombre(rol.getNombre())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Ya existe un rol con ese nombre");
        }
        return rolRepository.save(rol);
    }

    public Rol actualizar(Long id, Rol datos) {
        Rol rol = buscarPorId(id);
        rol.setNombre(datos.getNombre());
        rol.setDescripcion(datos.getDescripcion());
        return rolRepository.save(rol);
    }

    public void eliminar(Long id) {
        Rol rol = buscarPorId(id);
        rolRepository.delete(rol);
    }
}