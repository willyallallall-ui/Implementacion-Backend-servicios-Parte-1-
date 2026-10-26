/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.educacion.plataforma.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
/**
 *
 * @author Willi
 */
@Entity
@Table(name = "unidades")
public class Unidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título de la unidad es obligatorio")
    private String titulo;

    private String descripcion;

    @NotNull(message = "El orden es obligatorio")
    @Min(value = 1, message = "El orden debe ser 1 o mayor")
    private Integer orden;

    @NotNull(message = "El curso es obligatorio")
    @ManyToOne
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Unidad() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Integer getOrden() { return orden; }
    public void setOrden(Integer orden) { this.orden = orden; }

    public Curso getCurso() { return curso; }
    public void setCurso(Curso curso) { this.curso = curso; }
}