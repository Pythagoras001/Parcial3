package org.estudio.Services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.estudio.Models.Dolencia;
import org.estudio.Models.Dto.PacienteDTO;
import org.estudio.Models.Enfermedad;
import org.estudio.Models.EstrucPaciente.Paciente;
import org.estudio.Models.EstrucParametrosPaciente.*;
import org.estudio.Models.Reglas.EstadoSintimas;

import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CargaInfoDataService {

    private List<Enfermedad> dataEnfermedades;
    private List<Dolencia> dataDolencia;

    public CargaInfoDataService() throws Exception{
        try {
            dataEnfermedades = cargarEnfermedadesRegistradas();
            dataDolencia = cargarDolenciasRegistradas();
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private <T> List<T> cargarInfo(String ruta, Class<T> clase) throws Exception {
        try (FileReader reader = new FileReader(ruta)) {
            Gson gson = new Gson();
            Type tipo = TypeToken.getParameterized(List.class, clase).getType();
            return gson.fromJson(reader, tipo);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public List<Paciente> cargarPacientes() throws Exception{
        try {
            List<Paciente> pacientesIngresados = new ArrayList<>();

            List<PacienteDTO> pacientesRegistradosDto = cargarPacientesDto();
            Antecedente antecedente;
            DolenciaPaciente dolenciaPaciente;
            SignoVital signoVital;
            NivelDolor nivelDolor;
            InfoPersonal infoPersonal;

            for (PacienteDTO pacienteDTO : pacientesRegistradosDto){
                antecedente = cargarAntecedentePaciente(pacienteDTO.getAntecedentes());
                dolenciaPaciente = cargarDolenciasPaciente(pacienteDTO.getDolencias());
                signoVital = SignoVital.builder()
                        .presionArterial(pacienteDTO.getPresionArterial())
                        .saturacionOxigeno(pacienteDTO.getSaturacionOxigeno())
                        .tempertaturaCorporal(pacienteDTO.getTemperatura())
                        .fecuenciaRespiratoria(pacienteDTO.getFrecuenciaRespiratoria())
                        .frecuenciaCardiaca(pacienteDTO.getFrecuenciaCardiaca())
                        .build();
                nivelDolor = new NivelDolor(pacienteDTO.getNivelDolor());
                infoPersonal = InfoPersonal.builder()
                        .edad(pacienteDTO.getEdad())
                        .estaAcompanado(pacienteDTO.estaAcompanado)
                        .estadoSintomas(EstadoSintimas.valueOf(pacienteDTO.estadoSintomas))
                        .nombre(pacienteDTO.getNombre())
                        .enEmbarazo(pacienteDTO.enEmbarazo)
                        .build();

                Paciente paciente = Paciente.builder()
                        .antecedente(antecedente)
                        .dolenciaPaciente(dolenciaPaciente)
                        .signoVital(signoVital)
                        .nivelDolor(nivelDolor)
                        .infoPersonal(infoPersonal)
                        .build();

                pacientesIngresados.add(paciente);
            }

            return pacientesIngresados;

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private Antecedente cargarAntecedentePaciente(List<String> stringEnfermedades) throws Exception{
        try {
            List<Enfermedad> enfermedadesReportadas = dataEnfermedades.stream()
                    .filter(p -> stringEnfermedades.contains(p.getNombreEnfermedad()))
                    .toList();

            return new Antecedente(enfermedadesReportadas);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private DolenciaPaciente cargarDolenciasPaciente(List<String> stringDolencias) throws Exception{
        try {
            List<Dolencia> dolenciasReportadas = dataDolencia.stream()
                    .filter(p -> stringDolencias.contains(p.getNombreDolencia()))
                    .toList();

            return new DolenciaPaciente(dolenciasReportadas);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private List<PacienteDTO> cargarPacientesDto() throws Exception{
        try {
            String rutaPacientes = FileRoutes.rutaPacientes;
            return cargarInfo(rutaPacientes, PacienteDTO.class);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private List<Enfermedad> cargarEnfermedadesRegistradas()throws Exception{
        try {
            String rutaEnfermedades = FileRoutes.rutaEnfermedades;
            return cargarInfo(rutaEnfermedades, Enfermedad.class);

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private List<Dolencia> cargarDolenciasRegistradas()throws Exception{
        try {
            String rutaDolencia = FileRoutes.rutaDolencias;
            return cargarInfo(rutaDolencia, Dolencia.class);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
