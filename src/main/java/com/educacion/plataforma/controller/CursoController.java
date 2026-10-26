/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.controller;

import com.educacion.plataforma.entity.Curso;
import com.educacion.plataforma.service.CursoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
/**
 *
 * @author Willi
 */
@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoService cursoService;

    public CursoController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    @GetMapping
    public List<Curso> listar(@RequestParam(required = false) Long ofertaId,
                              @RequestParam(required = false) Long docenteId) {
        if (ofertaId != null) {
            return cursoService.listarPorOferta(ofertaId);
        }
        if (docenteId != null) {
            return cursoService.listarPorDocente(docenteId);
        }
        return cursoService.listar();
    }

    @GetMapping("/{id}")
    public Curso buscar(@PathVariable Long id) {
        return cursoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Curso crear(@Valid @RequestBody Curso curso) {
        return cursoService.crear(curso);
    }

    @PutMapping("/{id}")
    public Curso actualizar(@PathVariable Long id, @Valid @RequestBody Curso curso) {
        return cursoService.actualizar(id, curso);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        cursoService.eliminar(id);
    }
}