/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.controller;

import com.educacion.plataforma.entity.Rol;
import com.educacion.plataforma.service.RolService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Willi
 */
@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    public List<Rol> listar() {
        return rolService.listar();
    }

    @GetMapping("/{id}")
    public Rol buscar(@PathVariable Long id) {
        return rolService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rol crear(@Valid @RequestBody Rol rol) {
        return rolService.crear(rol);
    }

    @PutMapping("/{id}")
    public Rol actualizar(@PathVariable Long id, @Valid @RequestBody Rol rol) {
        return rolService.actualizar(id, rol);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        rolService.eliminar(id);
    }
}