package org.estudio.Services;

import lombok.extern.log4j.Log4j2;
import org.estudio.Models.EstrucPaciente.Paciente;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.PriorityQueue;

@Log4j2
public class TriajeService {

    private final EstadisticasService estadisticasService = new EstadisticasService();

    private final PriorityQueue<Paciente> colaAlta  = new PriorityQueue<>(Comparator.reverseOrder());
    private final PriorityQueue<Paciente> colaMedia = new PriorityQueue<>(Comparator.reverseOrder());
    private final PriorityQueue<Paciente> colaBaja  = new PriorityQueue<>(Comparator.reverseOrder());

    public void encolarPaciente(Paciente paciente) {
        String infoEncolado;
        String tipoCola;
        paciente.setHoraIngreso(LocalDateTime.now());

        switch (paciente.getNivelPrioridad()) {
            case 3 :
                colaAlta.add(paciente);
                tipoCola = "ALTA PRIORIDAD";
                break;
            case 2 :
                colaMedia.add(paciente);
                tipoCola = "MEDIA PRIORIDAD";
                break;
            default :
                colaBaja.add(paciente);
                tipoCola = "BAJA PRIORIDAD";
                break;
        }

        infoEncolado = String.format(
                "  [ENCOLADO] %-20s Cola %-5s (prioridad=%d, acumulada=%d)%n",
                paciente.getInfoPersonal().getNombre(),
                tipoCola,
                paciente.getNivelPrioridad(),
                paciente.getPrioridadAcumulada()
        );

        log.info(infoEncolado);

    }

    public boolean hayPacientes() {
        return !colaAlta.isEmpty() || !colaMedia.isEmpty() || !colaBaja.isEmpty();
    }

    public String procesarPacientes() {
        int ciclo = 1;

        while (hayPacientes()) {
            log.info(String.format("INICIO DEL CICLO #%d", ciclo++));
            atenderCola(colaAlta,  "ALTA",  3);
            atenderCola(colaMedia, "MEDIA", 2);
            atenderCola(colaBaja,  "BAJA",  1);
        }

        return estadisticasService.mostrarEstadisticas();
    }

    private void atenderCola(PriorityQueue<Paciente> cola, String nombre, int cantidad) {
        int atendidos = 0;
        Paciente paciente;

        while (atendidos < cantidad && !cola.isEmpty()) {
            paciente = cola.poll();
            estadisticasService.registrarAtencion(paciente, nombre);
            atendidos++;

            log.info(String.format("    [ATENDIDO] %-20s Cola %s%n",
                    paciente.getInfoPersonal().getNombre(), nombre));

        }
    }
}
