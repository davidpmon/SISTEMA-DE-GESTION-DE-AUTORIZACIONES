package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "historias_clinicas")
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_historia")
    private Integer idHistoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    // Relación con las órdenes médicas
    @OneToMany(mappedBy = "historia", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrdenMedica> ordenes;

    // CONSTRUCTORES //
    public HistoriaClinica() {}

    public HistoriaClinica(Paciente paciente, LocalDate fecha, String descripcion, String diagnostico) {
        this.paciente = paciente;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
    }

    // GETTERS Y SETTERS //
    public Integer getIdHistoria() {
        return idHistoria;
    }

    public void setIdHistoria(Integer idHistoria) {
        this.idHistoria = idHistoria;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public List<OrdenMedica> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<OrdenMedica> ordenes) {
        this.ordenes = ordenes;
    }
}



