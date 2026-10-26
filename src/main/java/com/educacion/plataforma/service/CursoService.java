/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.service;

import com.educacion.plataforma.entity.Curso;
import com.educacion.plataforma.entity.Oferta;
import com.educacion.plataforma.entity.Usuario;
import com.educacion.plataforma.repository.CursoRepository;
import com.educacion.plataforma.repository.OfertaRepository;
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
public class CursoService {

    private final CursoRepository cursoRepository;
    private final OfertaRepository ofertaRepository;
    private final UsuarioRepository usuarioRepository;

    public CursoService(CursoRepository cursoRepository,
                        OfertaRepository ofertaRepository,
                        UsuarioRepository usuarioRepository) {
        this.cursoRepository = cursoRepository;
        this.ofertaRepository = ofertaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    public List<Curso> listarPorOferta(Long ofertaId) {
        return cursoRepository.findByOfertaId(ofertaId);
    }

    public List<Curso> listarPorDocente(Long docenteId) {
        return cursoRepository.findByDocenteId(docenteId);
    }

    public Curso buscarPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Curso no encontrado con id " + id));
    }

    public Curso crear(Curso curso) {
        curso.setOferta(buscarOferta(curso.getOferta()));
        curso.setDocente(buscarDocente(curso.getDocente()));
        normalizarEstado(curso);
        return cursoRepository.save(curso);
    }

    public Curso actualizar(Long id, Curso datos) {
        Curso curso = buscarPorId(id);
        curso.setNombre(datos.getNombre());
        curso.setDescripcion(datos.getDescripcion());
        curso.setEstado(datos.getEstado());
        curso.setOferta(buscarOferta(datos.getOferta()));
        curso.setDocente(buscarDocente(datos.getDocente()));
        normalizarEstado(curso);
        return cursoRepository.save(curso);
    }

    public void eliminar(Long id) {
        Curso curso = buscarPorId(id);
        cursoRepository.delete(curso);
    }

    // Verifica que la oferta enviada exista de verdad
    private Oferta buscarOferta(Oferta oferta) {
        if (oferta.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Debe indicar el id de la oferta");
        }
        return ofertaRepository.findById(oferta.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Oferta no encontrada con id " + oferta.getId()));
    }

    // Verifica que el docente exista Y que tenga el rol DOCENTE
    private Usuario buscarDocente(Usuario docente) {
        if (docente.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Debe indicar el id del docente");
        }
        Usuario usuario = usuarioRepository.findById(docente.getId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Usuario no encontrado con id " + docente.getId()));
        if (!"DOCENTE".equalsIgnoreCase(usuario.getRol().getNombre())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Solo un usuario con rol DOCENTE puede dictar cursos");
        }
        return usuario;
    }

    private void normalizarEstado(Curso curso) {
        if (curso.getEstado() == null || curso.getEstado().trim().isEmpty()) {
            curso.setEstado("ACTIVO");
        }
        curso.setEstado(curso.getEstado().toUpperCase());
        if (!curso.getEstado().equals("ACTIVO") && !curso.getEstado().equals("INACTIVO")) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "El estado debe ser ACTIVO o INACTIVO");
        }
    }
}  