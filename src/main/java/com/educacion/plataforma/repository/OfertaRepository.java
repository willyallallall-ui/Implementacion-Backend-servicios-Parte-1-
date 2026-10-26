/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.repository;

import com.educacion.plataforma.entity.Oferta;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author Willi
 */
public interface OfertaRepository extends JpaRepository<Oferta, Long> {

    List<Oferta> findByEstado(String estado);
}
