/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.repository;

import com.educacion.plataforma.entity.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Willi
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByCorreo(String correo);

    List<Usuario> findByRolNombre(String nombreRol);
}