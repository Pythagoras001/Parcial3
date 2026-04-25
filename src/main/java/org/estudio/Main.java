package org.estudio;

import lombok.extern.log4j.Log4j2;
import org.estudio.Models.EstrucPaciente.Paciente;
import org.estudio.Services.CargaInfoDataService;
import org.estudio.Services.TriajeService;

import java.util.List;

@Log4j2
public class Main {
    public static void main(String[] args) throws Exception {

        TriajeService triajeService = new TriajeService();
        CargaInfoDataService cargaInfoDataService  = new CargaInfoDataService();

        log.info("========SISTEMA DE TRIAJE HOSPITALARIO========");

        List<Paciente> pacientes = cargaInfoDataService.cargarPacientes();

        log.info(String.format("Cantidad de pacientes a anteder #%d %n", pacientes.size()));

        for (Paciente p : pacientes) {
            triajeService.encolarPaciente(p);
        }

        triajeService.procesarPacientes();

    }
}
