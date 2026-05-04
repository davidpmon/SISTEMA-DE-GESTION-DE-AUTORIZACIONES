package com.example.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "medicamentos")
public class Medicamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medicamento")
    private Long idMedicamento;

    @Column(name = "nombre_generico", nullable = false)
    private String nombreGenerico;

    private String presentacion;
    private String concentracion;

    // Constructores
    public Medicamento() {}

    public Medicamento(String nombreGenerico, String presentacion, String concentracion) {
        this.nombreGenerico = nombreGenerico;
        this.presentacion = presentacion;
        this.concentracion = concentracion;
    }

    // Getters y Setters
    public Long getIdMedicamento() { return idMedicamento; }
    public void setIdMedicamento(Long idMedicamento) { this.idMedicamento = idMedicamento; }

    public String getNombreGenerico() { return nombreGenerico; }
    public void setNombreGenerico(String nombreGenerico) { this.nombreGenerico = nombreGenerico; }

    public String getPresentacion() { return presentacion; }
    public void setPresentacion(String presentacion) { this.presentacion = presentacion; }

    public String getConcentracion() { return concentracion; }
    public void setConcentracion(String concentracion) { this.concentracion = concentracion; }
}