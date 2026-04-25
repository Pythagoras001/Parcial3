package org.estudio.Models.Dto;

import lombok.Data;

import java.util.List;

@Data
public class PacienteDTO {

    // Info Personal
    public String nombre;
    public int edad;
    public boolean estaAcompanado;
    public boolean enEmbarazo;
    public String estadoSintomas;

    // Antecedentes y dolencias
    public List<String> antecedentes;  
    public List<String> dolencias;

    // Signos Vitales
    public int frecuenciaCardiaca;
    public int frecuenciaRespiratoria;
    public int presionArterial;
    public int saturacionOxigeno;
    public int temperatura;
    public int nivelDolor;

}
