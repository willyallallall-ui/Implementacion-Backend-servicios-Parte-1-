/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.service;

import com.educacion.plataforma.entity.Oferta;
import com.educacion.plataforma.repository.OfertaRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
/**
 *
 * @author Willi
 */
@Service
public class OfertaService {

    private final OfertaRepository ofertaRepository;

    public OfertaService(OfertaRepository ofertaRepository) {
        this.ofertaRepository = ofertaRepository;
    }

    public List<Oferta> listar() {
        return ofertaRepository.findAll();
    }

    public List<Oferta> listarPorEstado(String estado) {
        return ofertaRepository.findByEstado(estado.toUpperCase());
    }

    public Oferta buscarPorId(Long id) {
        return ofertaRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Oferta no encontrada con id " + id));
    }

    public Oferta crear(Oferta oferta) {
        validarReglas(oferta);
        return ofertaRepository.save(oferta);
    }

    public Oferta actualizar(Long id, Oferta datos) {
        Oferta oferta = buscarPorId(id);
        validarReglas(datos);
        oferta.setNombre(datos.getNombre());
        oferta.setDescripcion(datos.getDescripcion());
        oferta.setFechaInicio(datos.getFechaInicio());
        oferta.setFechaFin(datos.getFechaFin());
        oferta.setEstado(datos.getEstado());
        return ofertaRepository.save(oferta);
    }

    public void eliminar(Long id) {
        Oferta oferta = buscarPorId(id);
        ofertaRepository.delete(oferta);
    }

    // Reglas de negocio compartidas por crear y actualizar
    private void validarReglas(Oferta oferta) {
        if (oferta.getFechaFin().isBefore(oferta.getFechaInicio())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La fecha de fin no puede ser anterior a la fecha de inicio");
        }
        if (oferta.getEstado() == null || oferta.getEstado().trim().isEmpty()) {
            oferta.setEstado("ACTIVA");
        }
        oferta.setEstado(oferta.getEstado().toUpperCase());
        if (!oferta.getEstado().equals("ACTIVA") && !oferta.getEstado().equals("INACTIVA")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El estado debe ser ACTIVA o INACTIVA");
        }
    }
}