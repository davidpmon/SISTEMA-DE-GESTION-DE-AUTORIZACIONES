package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "procedimientos")
public class Procedimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_procedimiento")
    private Long idProcedimiento;

    @Column(name = "nombre_procedimiento", nullable = false)
    private String nombreProcedimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoProcedimiento tipo;

    @Column(name = "categoria")
    private String categoria;  // ← IMPORTANTE: Nueva categoría

    @Column(name = "descripcion")
    private String descripcion;

    public enum TipoProcedimiento {
        EXAMEN,
        TERAPIA
    }

    // Constructores, Getters y Setters...
    public Procedimiento() {}
    public Procedimiento(String nombreProcedimiento, TipoProcedimiento tipo, String categoria) {
        this.nombreProcedimiento = nombreProcedimiento;
        this.tipo = tipo;
        this.categoria = categoria;
    }

    // Getters y Setters
    public Long getIdProcedimiento() { return idProcedimiento; }
    public void setIdProcedimiento(Long idProcedimiento) { this.idProcedimiento = idProcedimiento; }

    public String getNombreProcedimiento() { return nombreProcedimiento; }
    public void setNombreProcedimiento(String nombreProcedimiento) { this.nombreProcedimiento = nombreProcedimiento; }

    public TipoProcedimiento getTipo() { return tipo; }
    public void setTipo(TipoProcedimiento tipo) { this.tipo = tipo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
}
