package org.estudio.Models.EstrucParametrosPaciente;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.estudio.Models.Dolencia;

import java.util.List;

@Data
@AllArgsConstructor
public class DolenciaPaciente {

    private List<Dolencia> dolenciasRegistradas;

    public void registrarDolencia(Dolencia dolencia)throws Exception{
        try {
            dolenciasRegistradas.add(dolencia);
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    public int evaluarPrioridad() throws Exception{
        try {
            int prioridadDolencia = 0;

            for (Dolencia dolencia : dolenciasRegistradas){
                prioridadDolencia = Math.max(prioridadDolencia, dolencia.getNivelPrioridad());
            }

            return prioridadDolencia;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
