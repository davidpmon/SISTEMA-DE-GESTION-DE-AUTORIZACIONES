package com.example.demo.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ordenes_medicas")
public class OrdenMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_orden")
    private Integer idOrden;

    // Relación con historia clínica (cada orden pertenece a una historia)
    @ManyToOne
    @JoinColumn(name = "id_historia", nullable = false)
    private HistoriaClinica historia;

    // Relacion de orden con paciente
    @ManyToOne
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    // Relación con el médico que crea la orden (vinculado a usuarios)
    @ManyToOne
    @JoinColumn(name = "id_medico", nullable = false)
    private Usuario medico;

    //utilizable a largo plazo
    /*@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;*/

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha; // Guarda fecha y hora exacta

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoOrden estado = EstadoOrden.PENDIENTE;



    // Enumeración igual a la base de datos (sin tildes)
    public enum EstadoOrden {
        PENDIENTE,
        EN_REVISION,
        APROBADA,
        RECHAZADA,
        CORREGIDA
    }

    // CONSTRUCTORES
    public OrdenMedica() {}

    public OrdenMedica(HistoriaClinica historia, Usuario medico, Paciente paciente, String descripcion) {
        this.historia = historia;
        this.medico = medico;
        this.paciente = paciente;
        this.descripcion = descripcion;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoOrden.PENDIENTE;
    }

    // Getters y Setters
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Paciente getPaciente() {
        return paciente;
    }
    public Integer getIdOrden() {
        return idOrden;
    }
    public void setIdOrden(Integer idOrden) {
        this.idOrden = idOrden;
    }

    public HistoriaClinica getHistoria() {
        return historia;
    }

    public void setHistoria(HistoriaClinica historia) {
        this.historia = historia;
    }

    public Usuario getMedico() {
        return medico;
    }

    public void setMedico(Usuario medico) {
        this.medico = medico;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoOrden getEstado() {
        return estado;
    }

    public void setEstado(EstadoOrden estado) {
        this.estado = estado;
    }
}
