package org.estudio.Models.EstrucParametrosPaciente;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.estudio.Models.Reglas.ParametrosDeAlerta;

@Data
@AllArgsConstructor
public class NivelDolor {

    private int nivelDolor;

    public int evaluarPrioridad() throws Exception{
        try {
            if (nivelDolor >= ParametrosDeAlerta.UMBRAL_DE_DOLOR_INTENSO) return 3;
            else if(nivelDolor >= ParametrosDeAlerta.UMBRAL_DE_DOLOR_MODERADO) return 2;
            return 0;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
