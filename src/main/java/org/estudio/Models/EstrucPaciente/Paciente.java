package org.estudio.Models.EstrucPaciente;

import lombok.Builder;
import lombok.Data;
import org.estudio.Models.EstrucParametrosPaciente.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class Paciente implements Comparable<Paciente> {

    private Antecedente antecedente;
    private DolenciaPaciente dolenciaPaciente;
    private SignoVital signoVital;
    private NivelDolor nivelDolor;
    private InfoPersonal infoPersonal;
    private int nivelPrioridad;
    private int prioridadAcumulada;
    private LocalDateTime horaIngreso;

    @Builder
    public Paciente(Antecedente antecedente, InfoPersonal infoPersonal, NivelDolor nivelDolor, SignoVital signoVital,
                    DolenciaPaciente dolenciaPaciente) throws Exception {

        this.antecedente = antecedente;
        this.infoPersonal = infoPersonal;
        this.nivelDolor = nivelDolor;
        this.signoVital = signoVital;
        this.dolenciaPaciente = dolenciaPaciente;

        List<Integer> prioridades = List.of(this.antecedente.evaluarPrioridad(), this.infoPersonal.evaluarPrioridad(),
                this.nivelDolor.evaluarPrioridad(), this.signoVital.evaluarPrioridad(), this.dolenciaPaciente.evaluarPrioridad());

        this.nivelPrioridad = prioridades.stream().max(Integer::compare).get();
        this.prioridadAcumulada = prioridades.stream().mapToInt(Integer::intValue).sum();

    }

    @Override
    public int compareTo(Paciente o) {
        int prioridad = Integer.compare(this.nivelPrioridad, o.nivelPrioridad);
        if(prioridad != 0) return prioridad;
        return Integer.compare(this.prioridadAcumulada, o.getPrioridadAcumulada());

    }
}
