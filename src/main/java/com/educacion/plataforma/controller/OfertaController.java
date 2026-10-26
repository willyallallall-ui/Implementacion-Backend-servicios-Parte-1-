/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.controller;

import com.educacion.plataforma.entity.Oferta;
import com.educacion.plataforma.service.OfertaService;
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
@RequestMapping("/api/ofertas")
public class OfertaController {

    private final OfertaService ofertaService;

    public OfertaController(OfertaService ofertaService) {
        this.ofertaService = ofertaService;
    }

    @GetMapping
    public List<Oferta> listar(@RequestParam(required = false) String estado) {
        if (estado != null) {
            return ofertaService.listarPorEstado(estado);
        }
        return ofertaService.listar();
    }

    @GetMapping("/{id}")
    public Oferta buscar(@PathVariable Long id) {
        return ofertaService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Oferta crear(@Valid @RequestBody Oferta oferta) {
        return ofertaService.crear(oferta);
    }

    @PutMapping("/{id}")
    public Oferta actualizar(@PathVariable Long id, @Valid @RequestBody Oferta oferta) {
        return ofertaService.actualizar(id, oferta);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        ofertaService.eliminar(id);
    }
}