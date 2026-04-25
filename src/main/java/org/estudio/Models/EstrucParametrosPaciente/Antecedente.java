package org.estudio.Models.EstrucParametrosPaciente;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.estudio.Models.Enfermedad;

import java.util.List;

@Data
@AllArgsConstructor
public class Antecedente {

    private List<Enfermedad> enfermedadesRegistradas;

    public void registrarNuevaEnfermedad(Enfermedad nuevaEnfermedad) throws Exception{
        try {
            this.enfermedadesRegistradas.add(nuevaEnfermedad);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public int evaluarPrioridad() throws Exception{
        try {
            int prioridadAntecedente = 0;
            for (Enfermedad enfermedad : enfermedadesRegistradas){
                prioridadAntecedente = Math.max(prioridadAntecedente, enfermedad.getNivelPrioridad());
            }
            return prioridadAntecedente;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
