package org.estudio.Models.EstrucParametrosPaciente;

import lombok.Builder;
import lombok.Data;
import org.estudio.Models.Reglas.ParametrosDeAlerta;

@Data
@Builder
public class SignoVital {

    private int frecuenciaCardiaca;
    private int fecuenciaRespiratoria;
    private int presionArterial;
    private int tempertaturaCorporal;
    private int saturacionOxigeno;

    public int evaluarPrioridad() throws Exception{
        try {
            int prioridadSignoVital = 0;
            prioridadSignoVital = Math.max(evaluarFrecuenciaCardiaca(), evaluarPresionArterial());
            prioridadSignoVital = Math.max(prioridadSignoVital, (Math.max(evaluarTemperaturaCorporal(), evaluarSaturacionOxigeno())));

            return prioridadSignoVital;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private int evaluarFrecuenciaCardiaca() throws Exception{
        try {
            if (this.frecuenciaCardiaca >= ParametrosDeAlerta.UMBRAL_DE_ARRITMIA) return 3;
            return 0;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private int evaluarPresionArterial() throws Exception{
        try {
            if (this.presionArterial >= ParametrosDeAlerta.UMBRAL_DE_PRESION_ALTA ||
                this.presionArterial <= ParametrosDeAlerta.UMBRAL_DE_PRESION_BAJA) return 3;

            else if(this.presionArterial >= ParametrosDeAlerta.UMBRAL_DE_PRESION_MEDIA) return 2;

            return 0;
        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private int evaluarTemperaturaCorporal() throws Exception{
        try {
            if (this.tempertaturaCorporal >= ParametrosDeAlerta.UMBRAL_DE_FIEBRE_CRITICA) return 3;
            else if(this.tempertaturaCorporal >= ParametrosDeAlerta.UMBRAL_DE_FIEBRE_GRAVE) return 2;
            return 0;

        }catch (Exception e){
            throw new Exception(e);
        }
    }

    private int evaluarSaturacionOxigeno() throws Exception{
        try {
            if (this.saturacionOxigeno <= ParametrosDeAlerta.UMBRAL_DE_SATURACION_OXG_CRTICA) return 3;
            else if(this.saturacionOxigeno <= ParametrosDeAlerta.UMBRAL_DE_SATURACION_OXG_GRAVE) return 2;
            return 0;

        }catch (Exception e){
            throw new Exception(e);
        }
    }

}
