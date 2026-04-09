package com.example.demo.dto;

import java.time.LocalDate;

public class HistoriaClinicaDTO {

    private Integer idHistoria;
    private String nombrePaciente;
    private String apellidoPaciente;
    private String documento;
    private LocalDate fechaNacimiento;

    // Constructor
    public HistoriaClinicaDTO(Integer idHistoria, String nombrePaciente, String apellidoPaciente, String documento, LocalDate fechaNacimiento) {
        this.idHistoria = idHistoria;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.documento = documento;
        this.fechaNacimiento = fechaNacimiento;
    }

    // Getters y setters
    public Integer getIdHistoria() { return idHistoria; }
    public void setIdHistoria(Integer idHistoria) { this.idHistoria = idHistoria; }

    public String getNombrePaciente() { return nombrePaciente; }
    public void setNombrePaciente(String nombrePaciente) { this.nombrePaciente = nombrePaciente; }

    public String getApellidoPaciente() { return apellidoPaciente; }
    public void setApellidoPaciente(String apellidoPaciente) { this.apellidoPaciente = apellidoPaciente; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}