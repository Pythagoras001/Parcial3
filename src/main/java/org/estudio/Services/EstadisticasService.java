package org.estudio.Services;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.estudio.Models.EstrucPaciente.Paciente;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class EstadisticasService {


    private static final String SEPARADOR     = "  +------------+-----------+----------+";
    private static final String FMT_FILA      = "  | %-10s | %-9d | %6.1f%%  |";

    private int atendidosAlta  = 0;
    private int atendidosMedia = 0;
    private int atendidosBaja  = 0;

    private record RegistroAtencion(Paciente paciente, String cola, LocalDateTime horaAtencion) {}

    private final List<RegistroAtencion> historial = new ArrayList<>();

    public void registrarAtencion(Paciente p, String cola) {
        historial.add(new RegistroAtencion(p, cola, LocalDateTime.now()));
        switch (p.getNivelPrioridad()) {
            case 3 -> atendidosAlta++;
            case 2 -> atendidosMedia++;
            default -> atendidosBaja++;
        }
    }


    public String mostrarEstadisticas() {
        int total = atendidosAlta + atendidosMedia + atendidosBaja;

        StringBuilder sb = new StringBuilder();
        sb.append("\n==========================================\n");
        sb.append("         ESTADISTICAS FINALES            \n");
        sb.append("==========================================\n");

        sb.append("\n  Distribucion por prioridad:\n");
        sb.append(SEPARADOR).append("\n");
        sb.append("  | Prioridad  | Atendidos | % Total  |\n");
        sb.append(SEPARADOR).append("\n");
        sb.append(String.format(FMT_FILA, "ALTA",  atendidosAlta,  porcentaje(atendidosAlta,  total))).append("\n");
        sb.append(String.format(FMT_FILA, "MEDIA", atendidosMedia, porcentaje(atendidosMedia, total))).append("\n");
        sb.append(String.format(FMT_FILA, "BAJA",  atendidosBaja,  porcentaje(atendidosBaja,  total))).append("\n");
        sb.append(SEPARADOR).append("\n");
        sb.append(String.format("  | %-10s | %-9d | 100.0%%   |", "TOTAL", total)).append("\n");
        sb.append(SEPARADOR);

        if (!historial.isEmpty()) {
            RegistroAtencion maxEspera = historial.stream()
                    .max((a, b) -> {
                        long espA = Duration.between(a.paciente().getHoraIngreso(), a.horaAtencion()).toMillis();
                        long espB = Duration.between(b.paciente().getHoraIngreso(), b.horaAtencion()).toMillis();
                        return Long.compare(espA, espB);
                    })
                    .get();

            long ms = Duration.between(maxEspera.paciente().getHoraIngreso(), maxEspera.horaAtencion()).toMillis();
            sb.append("\n\n  Paciente con mayor tiempo de espera:\n");
            sb.append(String.format("    Nombre : %s\n", maxEspera.paciente().getInfoPersonal().getNombre()));
            sb.append(String.format("    Cola   : %s\n", maxEspera.cola()));
            sb.append(String.format("    Espera : %d ms", ms));
        }

        sb.append("\n==========================================");
        log.info(sb.toString());

        return sb.toString();
    }

    private double porcentaje(int valor, int total) {
        if (total == 0) return 0;
        return (valor * 100.0) / total;
    }
}
